package com.duzo.superhero.blocks;

import com.duzo.superhero.items.SuperheroItems;
import com.duzo.superhero.items.ironman.IronManNanotechItem;
import com.duzo.superhero.sounds.SuperheroSounds;
import com.duzo.superhero.util.SuperheroIdentifier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class IronManSuitCaseBlock extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    // Shapes for each direction so the hitbox is the right size
    public static final VoxelShape NORTH_AABB = Block.box(2, 0, 5, 14, 3, 13); // I don't think AABB is the right name for these, but thats how that idiot Loqor does it.
    public static final VoxelShape EAST_AABB = Block.box(3, 0, 2, 11, 3, 14);
    public static final VoxelShape SOUTH_AABB = Block.box(2, 0, 3, 14, 3, 11);
    public static final VoxelShape WEST_AABB = Block.box(5, 0, 2, 13, 3, 14);
    public IronManSuitCaseBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return switch (state.getValue(FACING)) {
            case NORTH -> NORTH_AABB;
            case SOUTH -> SOUTH_AABB;
            case EAST -> EAST_AABB;
            case WEST -> WEST_AABB;
            default -> throw new RuntimeException("Invalid facing direction in getShape() for RadioBlock");
        };
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public static boolean equipArmourForMark(SuperheroIdentifier mark, Player player) {
        return equipArmourForMark(mark,player,false);
    }

    public static boolean equipArmourForMark(SuperheroIdentifier mark, Player player, boolean excludeNanotech) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (!slot.isArmor()) continue;

            if (!player.getItemBySlot(slot).isEmpty()) {
                if (excludeNanotech && slot == EquipmentSlot.CHEST) {
                    if (player.getItemBySlot(slot).getItem() instanceof IronManNanotechItem) {
                        continue;
                    }
                } else {
                    return false;
                }
            }
        }

        List<ArmorItem> armour = new ArrayList<>();
        for (RegistryObject<Item> item : SuperheroItems.ITEMS.getEntries()) {
            if (item.getId().toString().contains(mark.getSerializedName())) {
                armour.add((ArmorItem) item.get());
            }
        }

        if (armour.isEmpty()) return false;

        for (ArmorItem item : armour) {
            player.setItemSlot(item.getEquipmentSlot(), new ItemStack(item));
        }

        return true;
    }

    public static void convertArmourToSuitcase(Player player) {
        player.getInventory().placeItemBackInInventory(new ItemStack(SuperheroBlocks.IRONMAN_SUITCASE.get().asItem()));

        player.setItemSlot(EquipmentSlot.HEAD,ItemStack.EMPTY);
        player.setItemSlot(EquipmentSlot.CHEST,ItemStack.EMPTY);
        player.setItemSlot(EquipmentSlot.LEGS,ItemStack.EMPTY);
        player.setItemSlot(EquipmentSlot.FEET,ItemStack.EMPTY);
    }
    @Override
    public InteractionResult use(BlockState p_60503_, Level level, BlockPos p_60505_, Player player, InteractionHand hand, BlockHitResult p_60508_) {
        if (!level.isClientSide && hand == InteractionHand.MAIN_HAND) {
            if (equipArmourForMark(SuperheroIdentifier.IRONMAN_MARK_5,player)) {
                level.removeBlock(p_60505_,false);
                level.playSound(null,player, SuperheroSounds.IRONMAN_POWERUP.get(), SoundSource.PLAYERS,1f,1f);
            }
        }
        return InteractionResult.SUCCESS;
    }
}
