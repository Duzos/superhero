package com.duzo.superhero.entities;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.items.IronManArmourItem;
import com.duzo.superhero.sounds.SuperheroSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class IronManEntity extends HumanoidEntity {
    private static final EntityDataAccessor<String> MARK = SynchedEntityData.defineId(IronManEntity.class, EntityDataSerializers.STRING);
    public static final String DEFAULT_MARK = "mark_7";

    public IronManEntity(EntityType<? extends HumanoidEntity> entityType, Level level) {
        super(entityType, level);
        this.setInvulnerable(true);
        this.setCustomName(Component.translatable("Iron Man " + fileNameToUsable(this.getMark())));
        this.refreshSkin();
    }

    public IronManEntity(EntityType<? extends HumanoidEntity> entityType, Level level, String customName, ResourceLocation skin) {
        super(entityType, level, customName, skin);
        this.setInvulnerable(true);
        this.setCustomName(Component.translatable("Iron Man " + fileNameToUsable(this.getMark())));
        this.refreshSkin();
    }

    public IronManEntity(EntityType<? extends HumanoidEntity> entityType, Level level, String customName) {
        super(entityType, level, customName);
        this.setInvulnerable(true);
        this.setCustomName(Component.translatable("Iron Man " + fileNameToUsable(this.getMark())));
        this.refreshSkin();
    }

    public IronManEntity(EntityType<? extends HumanoidEntity> entityType, Level level, ResourceLocation skin) {
        super(entityType, level, skin);
        this.setInvulnerable(true);
        this.setCustomName(Component.translatable("Iron Man " + fileNameToUsable(this.getMark())));
        this.refreshSkin();
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!player.level.isClientSide && hand == InteractionHand.MAIN_HAND) {
            if (armourSlotsEmpty(player)) {
                this.putSelfOntoPlayer(player);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void registerGoals() {
        // no goals because no AI
    }

    public ResourceLocation getSkin() {
        return new ResourceLocation(Superhero.MODID, "textures/entities/iron_man/" + this.getMark() + ".png");
    }
    private void refreshSkin() {
        this.skin = new ResourceLocation(Superhero.MODID, "textures/entities/iron_man/" + this.getMark() + ".png");
    }

    public void setMark(String mark) {
        this.entityData.set(MARK,mark);
        this.skin = new ResourceLocation(Superhero.MODID, "textures/entities/iron_man/" + this.getMark() + ".png");
        this.setCustomName(Component.translatable("Iron Man " + fileNameToUsable(this.getMark())));
    }
    public String getMark() {
        return this.entityData.get(MARK);
    }
    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setMark(nbt.getString("mark"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putString("mark",this.getMark());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MARK, DEFAULT_MARK);
    }

    // @TODO iron man entity despawns
    @Override
    public void checkDespawn() {
        super.checkDespawn();
    }

    /**
     * Removes Underscores from the string
     * Capitalises the first letter of every word
     * Returns the new, more understandable, string.
     */
    public static String fileNameToUsable(String name) {
        String spaced = name.replace("_", " ");
        String[] words = spaced.split(" ");
        StringBuilder output = new StringBuilder();
        for (String word : words) {
            output.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1))
                    .append(" ");
        }
        return output.toString();
    }

/*    public static boolean isValidArmourSet(ItemStack head,ItemStack chest,ItemStack legs,ItemStack feet) {
        boolean flag1 = head.getItem() instanceof IronManArmourItem && chest.getItem() instanceof IronManArmourItem && legs.getItem() instanceof IronManArmourItem && feet.getItem() instanceof IronManArmourItem;

        IronManArmourItem ironhead = (IronManArmourItem) head.getItem();
        IronManArmourItem ironchest = (IronManArmourItem) chest.getItem();
        IronManArmourItem ironlegs = (IronManArmourItem) legs.getItem();
        IronManArmourItem ironfeet = (IronManArmourItem) feet.getItem();

        boolean flag2 = (ironhead.getMark() == ironchest.getMark()) && (ironhead.getMark() == ironlegs.getMark()) && (ironhead.getMark() == ironfeet.getMark());

        return flag1 && flag2;
    }*/


    public static boolean isValidArmorButCooler(LivingEntity player) {
        String currentMark = "";

        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            if (!equipmentSlot.isArmor()) continue;
            ItemStack currentSlot = player.getItemBySlot(equipmentSlot);
            if (currentSlot.getItem() instanceof IronManArmourItem item) {
                if (currentMark.isEmpty()) {
                    currentMark = item.getMark();
                } else if (!currentMark.equals(item.getMark())) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }


    public void takeArmourOffPlayer(Player player) {
        if (!isValidArmorButCooler(player)) return;

        this.setItemSlot(EquipmentSlot.HEAD, player.getItemBySlot(EquipmentSlot.HEAD));
        this.setItemSlot(EquipmentSlot.CHEST, player.getItemBySlot(EquipmentSlot.CHEST));
        this.setItemSlot(EquipmentSlot.LEGS, player.getItemBySlot(EquipmentSlot.LEGS));
        this.setItemSlot(EquipmentSlot.FEET, player.getItemBySlot(EquipmentSlot.FEET));

        player.setItemSlot(EquipmentSlot.HEAD,ItemStack.EMPTY);
        player.setItemSlot(EquipmentSlot.CHEST,ItemStack.EMPTY);
        player.setItemSlot(EquipmentSlot.LEGS,ItemStack.EMPTY);
        player.setItemSlot(EquipmentSlot.FEET,ItemStack.EMPTY);
    }
    private void putSelfOntoPlayer(Player player) {
        player.setItemSlot(EquipmentSlot.HEAD,this.getItemBySlot(EquipmentSlot.HEAD));
        player.setItemSlot(EquipmentSlot.CHEST,this.getItemBySlot(EquipmentSlot.CHEST));
        player.setItemSlot(EquipmentSlot.LEGS,this.getItemBySlot(EquipmentSlot.LEGS));
        player.setItemSlot(EquipmentSlot.FEET,this.getItemBySlot(EquipmentSlot.FEET));

        this.level.playSound(null,player,SuperheroSounds.IRONMAN_POWERUP.get(),SoundSource.PLAYERS,1f,1f);

        this.remove(RemovalReason.DISCARDED);
    }
//    private boolean putSelfOntoPlayer(Player player) {
//        if (!armourSlotsEmpty(player)) return false; // If the slots arent empty return
//
//        NonNullList<Item> armorItems = IronManArmourMarks.get(this.getMark());
//
//        int i = 0;
//        for (Item item : armorItems) {
//            player.getInventory().armor.set(i, new ItemStack(item));
//        }
//    }
    private static boolean armourSlotsEmpty(Player player) {
        for (ItemStack item : player.getInventory().armor) {
            if (!item.isEmpty()) return false;
        }
        return true;
    }

    public static void spawnNew(String mark,Level level, BlockPos pos, Player player) {
        IronManEntity ironMan = new IronManEntity(SuperheroEntities.IRON_MAN_ENTITY.get(), level);
        ironMan.setMark(mark);
        ironMan.takeArmourOffPlayer(player);
        ironMan.setPos(pos.above().getCenter());
        ironMan.setYRot(player.getYRot());
        level.addFreshEntity(ironMan);
        ironMan.refreshSkin();
    }
}