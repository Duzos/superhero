package com.duzo.superhero.blocks.entities;

import com.duzo.superhero.client.gui.menu.SuitMakerMenu;
import com.duzo.superhero.network.Network;
import com.duzo.superhero.network.packets.OpenSelectSuitMakerS2CPacket;
import com.duzo.superhero.network.packets.SyncSuitMakerHandlerS2CPacket;
import com.duzo.superhero.recipes.SuperheroSuitRecipe;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public class SuitMakerBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(64) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    public SuperheroSuitRecipe selectedSuitRecipe;
    public EquipmentSlot selectedSuitSlot;
    public SuitMakerBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(SuperheroBlockEntities.SUIT_MAKER.get(), p_155229_, p_155230_);
    }


    @Override
    public Component getDisplayName() {
        return Component.translatable("screen.superhero.suit_maker");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
        return new SuitMakerMenu(p_39954_,p_39955_,this);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();

        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();

        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("inventory",itemHandler.serializeNBT());

        if (selectedSuitRecipe != null) {
            tag.put("selectedRecipe",selectedSuitRecipe.serializeNBT());
        }
        if (selectedSuitSlot != null) {
            tag.putInt("suitSlot",selectedSuitSlot.ordinal());
        }

        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("inventory"));

        if (tag.getCompound("selectedRecipe") != null) {
            this.selectedSuitRecipe = SuperheroSuitRecipe.fromNBT(tag.getCompound("selectedRecipe"));
        }
        this.selectedSuitSlot = EquipmentSlot.values()[tag.getInt("suitSlot")];
    }

    public void use(Player player,BlockPos pos) {
        if (this.selectedSuitSlot != null && this.selectedSuitRecipe != null) {
            NetworkHooks.openScreen((ServerPlayer) player, this,pos);
        } else {
            openSelectScreen(player);
        }
    }

    public void openSelectScreen(Player player) {
        if (!(player instanceof ServerPlayer)) return;

        Network.sendToPlayer(new OpenSelectSuitMakerS2CPacket(this.worldPosition), (ServerPlayer) player);
    }

    public void dropContents() {
        Containers.dropContents(this.level,this.worldPosition,createContainerFromHandler(this.itemHandler));
    }

    public static SimpleContainer createContainerFromHandler(ItemStackHandler itemHandler) {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());

        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i,itemHandler.getStackInSlot(i));
        }

        return inventory;
    }

    public static SimpleContainer createContainerFromList(List<ItemStack> list) {
        SimpleContainer inventory = new SimpleContainer(list.size());

        for (int i = 0; i < list.size(); i++) {
            inventory.setItem(i,list.get(i));
        }

        return inventory;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, SuitMakerBlockEntity entity) {
        if (level.isClientSide) return;

        if (hasIngredientInInput(entity)) {
            moveIngredientIntoInventory(entity);
            setChanged(level,pos,state);
            entity.syncItemHandlerToClient();
        }

        if (hasCompletedRecipe(entity)) {
            craftItem(entity);
            setChanged(level,pos,state);
            entity.syncItemHandlerToClient();
        }
    }

    private static void craftItem(SuitMakerBlockEntity entity) {
        if (hasCompletedRecipe(entity)) {
            for (int i = 0; i < entity.itemHandler.getSlots();i++) {
                entity.itemHandler.extractItem(i,64,false);
            }
            entity.itemHandler.setStackInSlot(0,entity.selectedSuitRecipe.getResult(entity.selectedSuitSlot).get());
        }
    }

    private static boolean hasIngredientInInput(SuitMakerBlockEntity entity) {
        ItemStack input = entity.itemHandler.getStackInSlot(0);

        if (hasItemInInventory(entity,input)) {
            return false;
        }

        HashMap<Supplier<ItemStack>, List<ItemStack>> map = entity.selectedSuitRecipe.get(entity.selectedSuitSlot);
        if (map == null) return false;
        if (map.keySet().stream().findAny().isEmpty()) return false;

        Supplier<ItemStack> result = map.keySet().stream().findAny().get();

        for (ItemStack stack : map.get(result)) {
            if (stack.equals(input,false)) return true;
        }
        return false;
    }

    private static void moveIngredientIntoInventory(SuitMakerBlockEntity entity) {
        if (!hasIngredientInInput(entity)) return;

        int slot = getNextFreeSlot(entity);
        entity.itemHandler.insertItem(slot,entity.itemHandler.extractItem(0,64,false),false);
    }

    private static int getNextFreeSlot(SuitMakerBlockEntity entity) {
        ItemStack stack;
        for (int i = 2; i < entity.itemHandler.getSlots(); i++) {
            stack = entity.itemHandler.getStackInSlot(i);
            if (stack.isEmpty()) return i;
        }
        return -1;
    }

    public static boolean hasItemInInventory(SuitMakerBlockEntity entity,ItemStack stack) {
        for (int i = 1; i < entity.itemHandler.getSlots(); i++) {
            if (entity.itemHandler.getStackInSlot(i).equals(stack,true)) return true;
        }
        return false;
    }

    private static boolean hasCompletedRecipe(SuitMakerBlockEntity entity) {
        SimpleContainer inventory = createContainerFromHandler(entity.itemHandler);

        if (entity.selectedSuitRecipe == null) return false;

        return isCompletedRecipe(entity.selectedSuitSlot,entity.selectedSuitRecipe,inventory) && canInsertAmountIntoOutputSlot(inventory) && canInsertItemIntoOutputSlot(inventory,entity.selectedSuitRecipe.getResult(entity.selectedSuitSlot).get());
    }

    public static boolean isCompletedRecipe(EquipmentSlot slot, SuperheroSuitRecipe recipe, SimpleContainer container) {
        HashMap<Supplier<ItemStack>, List<ItemStack>> map = recipe.get(slot);
        if (map == null) return false;
        if (map.keySet().stream().findAny().isEmpty()) return false;

        Supplier<ItemStack> result = map.keySet().stream().findAny().get();

        for (ItemStack stack : map.get(result)) {
            boolean doesContain = container.hasAnyMatching(stack1 -> stack.equals(stack1,false));

            if (doesContain) continue;
            return false;
        }
        return true;
    }

    public static boolean isCompletedRecipe(SuperheroSuitRecipe recipe, SimpleContainer container) {
        for (EquipmentSlot slot : recipe.keySet()) {
            if (isCompletedRecipe(slot,recipe,container)) continue;
            return false;
        }
        return true;
    }
    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inv, ItemStack stack) {
        return inv.getItem(0).getItem() == stack.getItem() || inv.getItem(0).isEmpty();
    }
    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inv) {
        return inv.getItem(0).getMaxStackSize() > inv.getItem(0).getCount();
    }

    /**
     * Only use when syncing server to client
     * @param tag
     */
    public void setItemHandlerData(CompoundTag tag) {
        this.itemHandler.deserializeNBT(tag);
    }
    public void syncItemHandlerToClient() {
        Network.sendToAll(new SyncSuitMakerHandlerS2CPacket(this.worldPosition,this.itemHandler.serializeNBT()));
    }

    public void verifyRecipe() {
        if (!this.selectedSuitRecipe.verifyRecipeExistsInIdentifiers()) {
            LogUtils.getLogger().error("Recipe didn't exist in identifiers recipes, is someone being cheeky??");
            this.selectedSuitRecipe = null;
            this.selectedSuitSlot = null;
        }
    }
}
