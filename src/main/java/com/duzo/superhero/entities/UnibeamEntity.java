package com.duzo.superhero.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

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
        }

//        BlockHitResult result = getPlayerPOVHitResult(this.level,this);

//        this.level.setBlock(result.getBlockPos().above(), Blocks.RED_WOOL.defaultBlockState(),1);

        this.life--;
    }

    public static BlockHitResult getPlayerPOVHitResult(Level p_41436_, UnibeamEntity p_41437_) {
        float f = p_41437_.getXRot();
        float f1 = p_41437_.getYRot();
        Vec3 vec3 = p_41437_.getEyePosition();
        float f2 = Mth.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = Mth.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -Mth.cos(-f * ((float)Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d0 = 1d;
        Vec3 vec31 = vec3.add((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);
        return p_41436_.clip(new ClipContext(vec3, vec31, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, p_41437_));
    }
}
