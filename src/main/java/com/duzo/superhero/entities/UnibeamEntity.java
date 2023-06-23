package com.duzo.superhero.entities;

import com.duzo.superhero.items.ironman.IronManArmourItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class UnibeamEntity extends Entity {
    private int life;
    public UnibeamEntity(EntityType<?> type, Level level, int life) {
        super(type, level);
        this.life = life;
    }
    public UnibeamEntity(EntityType<?> type, Level level) {
        this(type,level,60);
    }

    public UnibeamEntity(Level level) {
        this(SuperheroEntities.UNIBEAM_ENTITY.get(),level);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.life = tag.getInt("life");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("life",this.life);
    }

    protected boolean rayPredicate(Entity entity) {
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.getLevel().isClientSide) {
            if (this.life <= 0) {
                this.remove(RemovalReason.DISCARDED);
            }

            // @TODO damaging
//            HitResult ray = ProjectileUtil.getEntityHitResult(this,this::rayPredicate);
//
//            System.out.println(ray.getLocation());
//
//            if (ray.getType() == HitResult.Type.ENTITY) {
//                EntityHitResult eRay = (EntityHitResult) ray;
//                if(eRay.getEntity() instanceof LivingEntity entity) {
//                    entity.hurt(this.level.damageSources().inFire(), 10f);
//                }
//            }

            Player player = this.level.getNearestPlayer(this,10d);

            if (player == null) return;

            if (!(player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof IronManArmourItem)) return;

            // @TODO move this code somewhere thats constantly ran bc the delay is very noticeable.
            int i = Mth.clamp(0, 0, 64);
            float f2 = Mth.cos(player.yBodyRot * ((float) Math.PI / 180F)) * (0F + 1.21F * (float) i);
            float f3 = Mth.sin(player.yBodyRot * ((float) Math.PI / 180F)) * (0F + 1.21F * (float) i);
            float f6 = (0.3F * 0.45F) * ((float) i * 0.2F + 0.0F);
            this.moveTo(player.getX() + (double) f2, player.getY() + (double) f6, player.getZ() + (double) f3, player.getYRot(), player.getXRot());
        }

        this.life--;
    }
}
