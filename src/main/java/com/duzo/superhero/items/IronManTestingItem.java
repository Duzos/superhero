package com.duzo.superhero.items;

import com.duzo.superhero.entities.IronManEntity;
import com.duzo.superhero.entities.SuperheroEntities;
import com.duzo.superhero.util.IronManMark;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public abstract class IronManTestingItem extends Item {
    public IronManTestingItem(Properties properties) {
        super(properties);
    }

    protected IronManMark getMark() {
        return null;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        Player player = context.getPlayer();
        InteractionHand hand = context.getHand();

        if (!level.isClientSide && hand == InteractionHand.MAIN_HAND) {
            IronManEntity ironMan = new IronManEntity(SuperheroEntities.IRON_MAN_ENTITY.get(), level);
            ironMan.setMark(this.getMark());
            ironMan.takeArmourOffPlayer(player);
            ironMan.setPos(pos.above().getCenter());
            ironMan.setYRot(player.getYRot());
            level.addFreshEntity(ironMan);
            context.getItemInHand().shrink(1);
        }
        return InteractionResult.SUCCESS;
    }
}
