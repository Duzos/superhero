package com.duzo.superhero.entities.batman;

import com.duzo.superhero.entities.SuperheroEntities;
import com.duzo.superhero.items.batman.GrapplingHookWeaponItem;
import com.duzo.superhero.network.Network;
import com.duzo.superhero.network.packets.RequestGrappleRopePointsC2SPacket;
import com.duzo.superhero.network.packets.UpdateGrappleRopePointsS2CPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class GrapplingHookRopeEntity extends Entity {
    @Deprecated
    public Vec3 point;
    public GrapplingHookRopeEntity(Level level) {
        super(SuperheroEntities.GRAPPLE_ROPE_ENTITY.get(),level);
    }

    @Deprecated
    public GrapplingHookRopeEntity(Level level, Vec3 point) {
        super(SuperheroEntities.GRAPPLE_ROPE_ENTITY.get(),level);
        this.point = point;
    }

    public GrapplingHookRopeEntity(EntityType<?> type, Level level) {
        super(type, level);
    }
    @Deprecated
    public void setPointsChanged() {
        Network.sendToAll(new UpdateGrappleRopePointsS2CPacket(this.getId(),this.point));
    }

    @Deprecated
    public void clientRequestUpdatedPoints() {
        Network.sendToServer(new RequestGrappleRopePointsC2SPacket(this.getId()));
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.point = new Vec3(tag.getDouble("pointX"),tag.getDouble("pointY"),tag.getDouble("pointZ"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        if (this.point == null) return;

        tag.putDouble("pointX", this.point.x);
        tag.putDouble("pointY", this.point.y);
        tag.putDouble("pointZ", this.point.z);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide) {
            if (this.point == null) {
                this.remove(RemovalReason.DISCARDED);
            }
            Player player = this.level.getNearestPlayer(this,10d);

            if (player == null) this.remove(RemovalReason.DISCARDED);
            assert player != null;
            if (!(player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof GrapplingHookWeaponItem)) this.remove(RemovalReason.DISCARDED);

            int i = Mth.clamp(0, 0, 64);
            float f2 = Mth.cos(player.yBodyRot * ((float) Math.PI / 180F)) * (0F + 1.21F * (float) i);
            float f3 = Mth.sin(player.yBodyRot * ((float) Math.PI / 180F)) * (0F + 1.21F * (float) i);
            float f6 = (0.3F * 0.45F) * ((float) i * 0.2F + 0.0F);
            this.moveTo(player.getX() + (double) f2, player.getY() + (double) f6, player.getZ() + (double) f3);

            System.out.println(player.position().distanceTo(this.point));

            if (player.position().distanceTo(this.point) <= 2.5) this.remove(RemovalReason.DISCARDED);
        } else {
            if (this.point == null) {
                this.clientRequestUpdatedPoints();
            }
        }
    }
}
