package com.duzo.superhero.entities.spiderman;

import com.duzo.superhero.entities.SuperheroEntities;
import com.duzo.superhero.network.Network;
import com.duzo.superhero.network.packets.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class WebRopeEntity extends Entity {
    @Deprecated
    public Vec3 origin,point;
    public Player player;

    private float alpha = 1f;

    public WebRopeEntity(Level level) {
        super(SuperheroEntities.WEB_ROPE_ENTITY.get(),level);
    }

    @Deprecated
    public WebRopeEntity(Level level, Vec3 point, Player player) {
        super(SuperheroEntities.WEB_ROPE_ENTITY.get(), level);
        this.origin = this.position();
        this.point = point;
        this.player = player;
    }

    public WebRopeEntity(EntityType<?> type, Level level) {
        super(type, level);
    }
    @Deprecated
    public void setPointsChanged() {
        Network.sendToAll(new UpdateWebRopePointsS2CPacket(this.getId(),this.origin,this.point));
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        if(this.player == null) {
            Network.sendToAll(new UpdatePlayerS2CPacket(this.getId(), this.player.getId()));
        }
        return this.player;
    }

    @Deprecated
    public void clientRequestUpdatedPoints() {
        Network.sendToServer(new RequestWebRopePointsC2SPacket(this.getId()));
    }

    public void syncAlphaToClient() {
        Network.sendToAll(new UpdateWebRopeAlphaS2CPacket(this.getId(),this.alpha));
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.origin = new Vec3(tag.getDouble("originX"),tag.getDouble("originY"),tag.getDouble("originZ"));
        this.point = new Vec3(tag.getDouble("pointX"),tag.getDouble("pointY"),tag.getDouble("pointZ"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        if (this.origin == null || this.point == null) return;

        tag.putDouble("originX", this.origin.x);
        tag.putDouble("originY", this.origin.y);
        tag.putDouble("originZ", this.origin.z);
//
        tag.putDouble("pointX", this.point.x);
        tag.putDouble("pointY", this.point.y);
        tag.putDouble("pointZ", this.point.z);
    }

    public float getAlpha() {
        return this.alpha;
    }
    public void setAlpha(float alpha) {
        this.alpha = Mth.clamp(alpha,0,1);
    }

    private void fadeOut() {
        this.setAlpha(this.alpha - (0.0095f));
    }

    @Override
    public void tick() {
        super.tick();
        fadeOut();

        if (this.alpha == 0) {
            this.remove(RemovalReason.DISCARDED);
        }

        if (!this.level.isClientSide) {
            this.syncAlphaToClient();
        }

        if (!this.level.isClientSide) {
            if (this.point == null) {
                this.remove(RemovalReason.DISCARDED);
            }
        } else {
            if (this.point == null) {
                this.clientRequestUpdatedPoints();
            }
        }
        /*if (this.player != null && this.player.level == this.level) {
            //this.restrictTo(this.player.blockPosition(), 5);
            this.player.setPosRaw(this.blockPosition().getX(),this.blockPosition().getY() - 5, this.blockPosition().getZ());
            float f = this.player.distanceTo(this);

            if (f > 10.0F) {
            } else if (f > 6.0F) {
                double d0 = (this.getX() - this.player.getX()) / (double)f;
                double d1 = (this.getY() - this.player.getY()) / (double)f;
                double d2 = (this.getZ() - this.player.getZ()) / (double)f;
                this.player.setDeltaMovement(this.player.getDeltaMovement().add(Math.copySign(d0 * d0 * 0.4D, d0), Math.copySign(d1 * d1 * 0.4D, d1), Math.copySign(d2 * d2 * 0.4D, d2)));
                this.player.checkSlowFallDistance();
            }

        }*/
    }
}
