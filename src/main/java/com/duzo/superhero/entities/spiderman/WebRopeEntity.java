package com.duzo.superhero.entities.spiderman;

import com.duzo.superhero.entities.SuperheroEntities;
import com.duzo.superhero.network.Network;
import com.duzo.superhero.network.packets.*;
import com.duzo.superhero.util.KeyBinds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
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
            if (this.level.isClientSide) {
                this.clientRequestUpdatePlayer();
            } else { // Panic if you get here.
                // Kill yourself, now.
                this.remove(RemovalReason.DISCARDED);
            }
        }
        return this.player;
    }

    public void clientRequestUpdatePlayer() {
        Network.sendToServer(new RequestWebRopePlayerC2SPacket(this.getId()));
    }
    public void syncPlayerToClient() {
        Network.sendToAll(new UpdateWebRopePlayerS2CPacket(this.getId(),this.getPlayer().getId()));
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
        this.setAlpha(this.alpha - (0.0295f));
    }

    private void runSwingPhysics() {
        if(KeyBinds.ABILITY_ONE.isDown()) {
        if (this.getPlayer() == null) return;
            float distanceToPlayer = this.getPlayer().distanceTo(this);
            if (distanceToPlayer > 6.0F) {
                double d0 = (this.getX() - this.getPlayer().getX()) / (double) distanceToPlayer;
                double d1 = (this.getY() - this.getPlayer().getY()) / (double) distanceToPlayer;
                double d2 = (this.getZ() - this.getPlayer().getZ()) / (double) distanceToPlayer;

                if (d1 < 0) {
                    d1 = 0;
                }

                this.getPlayer().setDeltaMovement(this.getPlayer().getDeltaMovement().add(Math.copySign(d0 * d0 * 0.4D, d0), Math.copySign(d1 * d1 * 0.2D, d1), Math.copySign(d2 * d2 * 0.4D, d2)));
                this.getPlayer().checkSlowFallDistance();
            }
        }
    }

    /*private void runSwingPhysics() {
        //@TODO by Loqor: IDK IF THIS IS THE FINAL PRODUCT :( IT'S NOT MY FAVORITE AND IT NEEDS A LOT OF WORK, BUT IT DOES WHAT IT DOES. PREVIOUS CODE IS ABOVE IF THIS ONE TURNS OUT TO BE MORE SHITE.
        if (this.getPlayer() == null) return;

        float distanceToPlayer = this.getPlayer().distanceTo(this);
        if (distanceToPlayer > 6.0F) {
            double d0 = (this.getX() - this.getPlayer().getX()) / (double) distanceToPlayer;
            double d1 = (this.getY() - this.getPlayer().getY()) / (double) distanceToPlayer;
            double d2 = (this.getZ() - this.getPlayer().getZ()) / (double) distanceToPlayer;

            if (d1 < 0) {
                d1 = 0;
            }

            double swingForce = 0.3D; // Adjust the swing force as needed

            // Calculate the swing direction perpendicular to the rope
            double dx = d0;
            double dy = d1;
            double dz = d2;

            // Calculate the swing force vector
            double swingX = dx * swingForce;
            double swingY = dy * swingForce;
            double swingZ = dz * swingForce;

            // Apply the swing force to the player's motion
            this.getPlayer().setDeltaMovement(this.getPlayer().getDeltaMovement().add(swingX, swingY, swingZ));
            this.getPlayer().checkSlowFallDistance();
        }
    }*/

    @Override
    public void tick() {
        super.tick();

        if (this.alpha > 0.85 && KeyBinds.ABILITY_ONE.isDown()) {
            this.setAlpha(1f);
            this.runSwingPhysics();
        } else {
            fadeOut();
        }

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
    }
}
