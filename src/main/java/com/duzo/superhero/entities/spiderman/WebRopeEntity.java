package com.duzo.superhero.entities.spiderman;

import com.duzo.superhero.entities.SuperheroEntities;
import com.duzo.superhero.network.Network;
import com.duzo.superhero.network.packets.*;
import com.duzo.superhero.util.KeyBinds;
import net.minecraft.core.Direction;
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

    private double initialDistance;

    public WebRopeEntity(Level level) {
        super(SuperheroEntities.WEB_ROPE_ENTITY.get(),level);
    }

    @Deprecated
    public WebRopeEntity(Level level, Vec3 point, Player player) {
        super(SuperheroEntities.WEB_ROPE_ENTITY.get(), level);
        this.origin = this.position();
        this.point = point;
        this.player = player;
        if(this.player != null) {
            Vec3 playerPos = this.player.position();
            this.initialDistance = Math.abs(Math.sqrt(((playerPos.x() - this.position().x()) * (playerPos.x() - this.position().x())) + ((playerPos.y() - this.position().y()) * (playerPos.y() - this.position().y())) + ((playerPos.z() - this.position().z()) * (playerPos.z() - this.position().z()))));
        }
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

    //public void setRestraintDistance() {
    //    if(this.getPlayer() != null) {
    //        Vec3 playerPos = this.getPlayer().position();
    //        this.initialDistance = Math.abs(Math.sqrt(((playerPos.x() - this.position().x()) * (playerPos.x() - this.position().x())) + ((playerPos.y() - this.position().y()) * (playerPos.y() - this.position().y())) + ((playerPos.z() - this.position().z()) * (playerPos.z() - this.position().z()))));
    //    }
    //}

    public double getInitialDistance() {
        return this.initialDistance;
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
        this.initialDistance = tag.getDouble("initialDistance");
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

        tag.putDouble("initialDistance", this.initialDistance);
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

    /*public static Vec3 rotatePoint(Vec3 point, Vec3 anchor, double xRotation, double yRotation, double zRotation) {
        // Translate the point relative to the anchor
        double deltaX = point.x - anchor.x;
        double deltaY = point.y - anchor.y;
        double deltaZ = point.z - anchor.z;

        // Convert rotation angles to radians
        double xRad = Math.toRadians(xRotation);
        double yRad = Math.toRadians(yRotation);
        double zRad = Math.toRadians(zRotation);

        // Apply rotation matrices
        double cosX = Math.cos(xRad);
        double sinX = Math.sin(xRad);
        double cosY = Math.cos(yRad);
        double sinY = Math.sin(yRad);
        double cosZ = Math.cos(zRad);
        double sinZ = Math.sin(zRad);

        double rotatedX = deltaX;
        double rotatedY = deltaY * cosX - deltaZ * sinX;
        double rotatedZ = deltaY * sinX + deltaZ * cosX;

        double finalX = rotatedX * cosY + rotatedZ * sinY;
        double finalY = rotatedY;
        double finalZ = -rotatedX * sinY + rotatedZ * cosY;

        rotatedX = finalX * cosZ - finalY * sinZ;
        rotatedY = finalX * sinZ + finalY * cosZ;
        rotatedZ = finalZ;

        // Translate the point back to the original position
        double newX = rotatedX + anchor.x;
        double newY = rotatedY + anchor.y;
        double newZ = rotatedZ + anchor.z;

        // Create and return the new point
        return new Vec3(newX, newY, newZ);
    }

    public static Vec3 getRotationAngles(Vec3 point1, Vec3 point2) {
        double deltaX = point2.x - point1.x;
        double deltaY = point2.y - point1.y;
        double deltaZ = point2.z - point1.z;

        double distanceXY = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        double distanceXZ = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

        double xRotation = Math.atan2(deltaY, distanceXZ);
        double yRotation = Math.atan2(-deltaX, distanceXY);
        double zRotation = Math.atan2(deltaZ, distanceXY);

        return new Vec3(Math.toDegrees(xRotation), Math.toDegrees(yRotation), Math.toDegrees(zRotation));
    }

    private void runSwingPhysics() {
        if (KeyBinds.ABILITY_ONE.isDown()) {
            if (getPlayer() == null)
                return;

            double radius = 6;
            double distanceToPlayer = getPlayer().distanceTo(this);

            if (distanceToPlayer > radius) {
                // Calculate the rotation angles for the swing arc
                Vec3 rotationAngles = getRotationAngles(getPlayer().position(), this.position());


                // Calculate the velocity components for the swing motion
                double xVel = (rotationAngles.x * radius) / distanceToPlayer;
                double yVel = (rotationAngles.y * radius) / distanceToPlayer;
                double zVel = (rotationAngles.z * radius) / distanceToPlayer;

                // Calculate the new position for the player using the rotated point
                Vec3 newPlayerPosition = rotatePoint(getPlayer().position(), this.position(), xVel, yVel, zVel);

                // Calculate the delta movement
                Vec3 deltaMovement = newPlayerPosition.subtract(getPlayer().position()).scale(0.5D);

                // Move the player relative to its current position
                getPlayer().setDeltaMovement(deltaMovement);

                System.out.println("Web Entity Position: " + this.position());
                System.out.println("Original Player Position: " + getPlayer().position());
                System.out.println("X Velocity: " + xVel + " Y Velocity: " + yVel + " Z Velocity: " + zVel);
                System.out.println("New Player Position: " + newPlayerPosition);

                getPlayer().checkSlowFallDistance();
            } else {
                // Perform some action when the player is within the radius of the swing
            }
        }
    }*/
    //@TODO this is the original better code. use the before this right below for better effect

    public double getCurrentDistance() {
        if(this.getPlayer() == null) return 0;
        Vec3 playerPos = this.getPlayer().position();
        return Math.abs(Math.sqrt(((playerPos.x() - this.position().x()) * (playerPos.x() - this.position().x())) + ((playerPos.y() - this.position().y()) * (playerPos.y() - this.position().y())) + ((playerPos.z() - this.position().z()) * (playerPos.z() - this.position().z()))));

    }

    private void runSwingPhysics() {
        if(KeyBinds.ABILITY_ONE.isDown()) {
            if (this.getPlayer() == null) return;
            float distanceToPlayer = this.getPlayer().distanceTo(this);
            Vec3 playerPos = this.getPlayer().position();
            double length = Math.abs(Math.sqrt(((playerPos.x() - this.position().x()) * (playerPos.x() - this.position().x())) + ((playerPos.y() - this.position().y()) * (playerPos.y() - this.position().y())) + ((playerPos.z() - this.position().z()) * (playerPos.z() - this.position().z()))));
            double blend = Mth.clamp(length - this.getInitialDistance(), 1.0, 0.0);

            double d0 = (this.getX() - this.getPlayer().getX()) * blend;
            double d1 = (this.getY() - this.getPlayer().getY()) * blend;
            double d2 = (this.getZ() - this.getPlayer().getZ()) * blend;

            if (d1 < 0) {
                d1 = 0;
            }

            this.getPlayer().setDeltaMovement(this.getPlayer().getDeltaMovement().add(Math.copySign(d0 * d0 * 0.4D, d0), Math.copySign(d1 * d1 * 0.2D, d1), Math.copySign(d2 * d2 * 0.4D, d2)));
            this.getPlayer().checkSlowFallDistance();
            System.out.println( "Length: " + length + " | Initial Distance: " + this.initialDistance);
        }
    }

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
