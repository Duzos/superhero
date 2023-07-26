package com.duzo.superhero.entities.spiderman;

import com.duzo.superhero.entities.SuperheroEntities;
import com.duzo.superhero.network.Network;
import com.duzo.superhero.network.packets.*;
import com.duzo.superhero.util.KeyBinds;
import com.duzo.superhero.util.spiderman.SpiderManUtil;
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

    private double initialDistance = 0d;
    private Vec3 initialDistanceVec = new Vec3(0,0,0);
    //PendulumCalculations pendulum = new PendulumCalculations();

    public WebRopeEntity(Level level) {
        super(SuperheroEntities.WEB_ROPE_ENTITY.get(),level);
    }

    @Deprecated
    public WebRopeEntity(Level level, Vec3 point, Player player) {
        super(SuperheroEntities.WEB_ROPE_ENTITY.get(), level);
        this.origin = this.position();
        this.point = point;
        this.player = player;
        this.setInitialDistance(point);
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

    public void syncInitialDistanceToClient() {
        Network.sendToAll(new UpdateInitialDistanceS2CPacket(this.getId(),this.initialDistance, this.initialDistanceVec));
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

    public double getInitialDistance() {
        return this.initialDistance;
    }

    public Vec3 getInitialDistanceVec() {
        return this.initialDistanceVec;
    }

    public Vec3 initialDistanceVec() {
        if (this.getPlayer() != null) {
            Vec3 playerPos = this.getPlayer().position();
            Vec3 newVec = new Vec3((playerPos.x() - this.position().x()) * (playerPos.x() - this.position().x()), (playerPos.y() - this.position().y()) * (playerPos.y() - this.position().y()), (playerPos.z() - this.position().z()) * (playerPos.z() - this.position().z()));
            return newVec;
        }
        return new Vec3(0,0,0);
    }

    public void setInitialDistance(Vec3 position) {
        if (this.getPlayer() != null) {
            Vec3 playerPos = this.getPlayer().position();
            double distance = Math.abs(Math.sqrt(((playerPos.x() - position.x()) * (playerPos.x() - position.x())) + ((playerPos.y() - position.y()) * (playerPos.y() - position.y())) + ((playerPos.z() - position.z()) * (playerPos.z() - position.z()))));
            this.initialDistanceVec = initialDistanceVec();
            this.initialDistance = distance;
            //this.pendulum.init(this.initialDistance, Math.atan(divideByDouble(this.initialDistanceVec.multiply(this.position()), this.initialDistanceVec.dot(this.position()))));
        }
    }

    public void setInitialDistance(double initialDistance) {
        this.initialDistance = initialDistance;
    }

    public void setInitialDistanceVec(Vec3 initialDistanceVec) {
        this.initialDistanceVec = initialDistanceVec;
    }

    /*public static Vec3 rotatePoint(Vec3 point, Vec3 anchor, double xRotation, double yRotation, double zRotation) {
        // Translate the point relative to the anchor
        double deltaX = point.x - anchor.x;
        double deltaY = point.y - anchor.y;
        double deltaZ = point.z - anchor.z;
        System.out.println("deltaX: " + deltaX + " point.x: " + point.x + " anchor.x: " + anchor.x);
        System.out.println("xRot: " + xRotation + " yRot: " + yRotation + " zRot: " + zRotation);

        // Convert rotation angles to radians
        double xRad = Math.abs(Math.toRadians(xRotation));
        double yRad = Math.abs(Math.toRadians(yRotation));
        double zRad = Math.abs(Math.toRadians(zRotation));

        System.out.println("xRot after rads: " + xRad + " yRot after rads: " + yRad + " zRot after rads: " + zRad);

        // Apply rotation matrices
        double cosX = Math.cos(xRad);
        double sinX = Math.sin(xRad);
        double cosY = Math.cos(yRad);
        double sinY = Math.sin(yRad);
        double cosZ = Math.cos(zRad);
        double sinZ = Math.sin(zRad);

        double rotatedX = deltaX * cosY * cosZ - deltaY * cosY * sinZ + deltaZ * sinY;
        double rotatedY = deltaX * (cosX * sinZ + sinX * sinY * cosZ) + deltaY * (cosX * cosZ - sinX * sinY * sinZ) - deltaZ * sinX * cosY;
        double rotatedZ = deltaX * (sinX * sinZ - cosX * sinY * cosZ) + deltaY * (sinX * cosZ + cosX * sinY * sinZ) + deltaZ * cosX * cosY;

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

            double radius = this.initialDistance;
            double distanceToPlayer = this.getPlayer().distanceTo(this);

            if (distanceToPlayer > radius) {
                // Calculate the rotation angles for the swing arc
                Vec3 rotationAngles = getRotationAngles(this.getPlayer().position(), this.position());

                double blend = Mth.clamp((distanceToPlayer-this.initialDistance)*0.2+0.6,0.0,1.0);

                // Calculate the velocity components for the swing motion
                double xVel = ((rotationAngles.x * radius) / distanceToPlayer) * blend;
                double yVel = ((rotationAngles.y * radius) / distanceToPlayer) * blend;
                double zVel = ((rotationAngles.z * radius) / distanceToPlayer) * blend;

                // Calculate the new position for the player using the rotated point
                Vec3 newPlayerPosition = rotatePoint(this.getPlayer().position(), this.position(), xVel, yVel, zVel);

                // Calculate the delta movement
                //Vec3 deltaMovement = newPlayerPosition.add(this.getPlayer().position()).scale(0.5D).normalize();
                Vec3 deltaMovement = newPlayerPosition.normalize();

                // Move the player relative to its current position
                this.getPlayer().setDeltaMovement(deltaMovement.normalize());

                System.out.println("Web Entity Position: " + this.position());
                System.out.println("Original Player Position: " + this.getPlayer().position());
                System.out.println("X Velocity: " + xVel + " Y Velocity: " + yVel + " Z Velocity: " + zVel);
                System.out.println("New Player Position: " + newPlayerPosition);

                getPlayer().checkSlowFallDistance();
            } else {
                // Perform some action when the player is within the radius of the swing
            }
        }
    }*/
    //@TODO this is the original better code. use the before this right below for better effect

    private double yawVector(Player player, double radius) {
        return ((player.getDeltaMovement().x() / radius) + (player.getDeltaMovement().z() / radius));
    }

    private double pitchVector(Player player, double radius) {
        return (Math.sqrt((player.getDeltaMovement().x() * player.getDeltaMovement().x()) + (player.getDeltaMovement().z() * player.getDeltaMovement().z())) / radius) + (player.getDeltaMovement().y() / radius);
    }

    /*private Vec3 angleVectorBetweenPlayerPositionAndDesiredPoint(Vec3 point) {
        Vec3 playerPos = this.getPlayer().position();
        double distance = Math.abs((point.y() - playerPos.y()));
        double length = Math.abs(Math.sqrt(((playerPos.x() - point.x()) * (playerPos.x() - point.x())) +
                ((playerPos.y() - 0) * (playerPos.y() - 0)) +
                ((playerPos.z() - point.z()) * (playerPos.z() - point.z()))));
        double a = Math.asin(distance / length);
        return
    }*/

    /*private Vec3 rotate(Vec3 playerPos, Vec3 center, Player player) {
        double ox = center.x();
        double oy = center.y();
        double oz = center.z();
        double px = playerPos.x();
        double py = playerPos.y();
        double pz = playerPos.z();
        //Vec3 dx = deltaMovement.xRot(1);
        //Vec3 dy = deltaMovement.yRot(1);
        //Vec3 dz = deltaMovement.zRot(1);
        Direction dx = player.getMotionDirection();
        if(dx == Direction.DOWN) {
            py = py - 2;
        }

        //double xnew = Math.cos(angle) * (px - ox) - Math.sin(angle) * (pz - oz) + ox;
        //double znew = Math.sin(angle) * (px - ox) + Math.cos(angle) * (pz - oz) + oz;
        //double ynew = Math.sin(xnew)/Math.cos(znew);
        double f = angle;
        double ϕ = angle2;
        double xnew = center.x() + (px - ox) * Math.cos(f) - (py - oy) * Math.sin(f) * Math.cos(pz) + (pz - oz) * Math.sin(f) * Math.sin(pz) * Math.cos(ϕ);
        double znew = center.z() + (px - ox) * Math.sin(f) + (py - oy) * Math.cos(f) * Math.cos(pz) + (pz - oz) * Math.cos(f) * Math.sin(pz) * Math.cos(ϕ);
        double ynew = center.y() + (py - oy) * Math.sin(pz) * Math.sin(ϕ) - (pz - oz) * Math.cos(pz) * Math.sin(ϕ);
        return new Vec3(xnew, ynew, znew);
    }*/

    /*private Vec3 runAngleCalculations(Vec3 rotationalPoints) {
        double xRot = rotationalPoints.x();
        double zRot = rotationalPoints.z();
        double yaw = yawAngle(xRot, this.getPlayer().position().x(), zRot, this.getPlayer().position().z());
        System.out.println("yaw: " + yaw);
        return new Vec3(yaw, yaw, yaw);
    }

    private double yawAngle(double xnew, double px, double znew, double pz) {
        double yaw = Math.toDegrees(Math.atan((xnew - px)/(znew - pz)));
        if(yaw < 0) {
            yaw += 360;
        }
        return yaw;
    }

    private void runSwingPhysics() {
        if(KeyBinds.ABILITY_ONE.isDown()) {
            if (this.getPlayer() != null) {
                Vec3 center = this.position();
                Vec3 playerPos = this.getPlayer().position();
                double length = this.getPlayer().distanceTo(this);
                double radius = this.initialDistance;
                double yaw = yawVector(this.getPlayer(), radius);
                double pitch = pitchVector(this.getPlayer(), radius);
                Vec3 transposedPoint = rotate(playerPos, center, yaw, pitch);
                double blend = Mth.clamp((length-radius)*0.2+0.6,1.0,0.0);
                Vec3 propellantAngle = runAngleCalculations(transposedPoint).normalize().scale(blend);
                System.out.print("propellantAngle: " + propellantAngle);

                if(Double.isNaN(propellantAngle.x()) && Double.isNaN(propellantAngle.y()) && Double.isNaN(propellantAngle.z())) {
                    propellantAngle = new Vec3(0,0,0);
                    System.out.print("Error: propellant is NaN");
                }

                //this.getPlayer().setDeltaMovement(propellantAngle.subtract(this.getDeltaMovement().x(), 0, this.getDeltaMovement().z()));
                this.getPlayer().setDeltaMovement(propellantAngle);
                System.out.println(transposedPoint);
            }
        } else {
            this.getPlayer().setDeltaMovement(0, 0,0);
        }
    }*/
    //@TODO THIS IS THE CONTINUATION CODE, USE THIS.

    /*private void ArunSwingPhysics() {
        if(KeyBinds.ABILITY_ONE.isDown()) {
            if(this.getPlayer() != null) {
                double distanceToPlayer = this.getPlayer().distanceTo(this);
                double radius = this.initialDistance;
                double blend = Mth.clamp((distanceToPlayer-this.initialDistance)*0.2+0.6,0,1.0);
                Vec3 playerPos = this.getPlayer().position();
                double valX = this.position().x() - playerPos.x();
                double valY = this.position().y() - playerPos.y();
                double valZ = this.position().z() - playerPos.z();
                if(valX < 0) {
                    valX = Math.abs(valX);
                }
                if(valY < 0) {
                    valY = Math.abs(valY);
                }
                if(valZ < 0) {
                    valZ = Math.abs(valZ);
                }
                double yaw = Math.toDegrees(Math.atan(Math.abs(valX)/Math.abs(valZ)));
                double hyp = Math.sqrt((valX*valX)+(valZ*valZ));
                double pitch = -Math.toDegrees(Math.atan(valY/hyp));
                Vec3 newPoint = rotate(playerPos, this.position(), this.getPlayer());
                Vec3 direction = calculatedVector(yaw, pitch).normalize();
                this.getPlayer().setDeltaMovement();
            }
        }
        //@TODO again.. another fucking runSwingPhysics()..
    }

    private Vec3 divideByDouble(Vec3 vec, double i) {
        double val1 = vec.x() / i;
        double val2 = vec.y() / i;
        double val3 = vec.z() / i;
        return new Vec3(val1, val2, val3);
    }

    private Vec3 divide(Vec3 vec, Vec3 vec1) {
        double val1 = vec.x() / vec1.x();
        double val2 = vec.y() / vec1.y();
        double val3 = vec.z() / vec1.z();
        return new Vec3(val1, val2, val3);
    }

    public final Vec3 calculatedVector(double p_20172_, double p_20173_) {
        double f = p_20172_ * (Math.PI / 180F);
        double f1 = -p_20173_ * (Math.PI / 180F);
        double f2 = Math.cos(f1);
        double f3 = Math.sin(f1);
        double f4 = Math.cos(f);
        double f5 = Math.sin(f);
        return new Vec3((f3 * f4), (-f5), (f2 * f4));
    }*/

    private void runSwingPhysics() {//@TODO THIS IS THE BEST ONE DON'T DELETE
        if(KeyBinds.ABILITY_ONE.isDown()) {
            if (this.getPlayer() == null) return;
            float distanceToPlayer = this.getPlayer().distanceTo(this);
            Vec3 playerPos = this.getPlayer().position();
            double length = Math.abs(Math.sqrt(((playerPos.x() - this.position().x()) * (playerPos.x() - this.position().x())) + ((playerPos.y() - this.position().y()) * (playerPos.y() - this.position().y())) + ((playerPos.z() - this.position().z()) * (playerPos.z() - this.position().z()))));
            double blend = Mth.clamp((length-this.initialDistance)*0.2+0.6,0.0,1.0);

            //double d0 = ((this.getX() - this.getPlayer().getX()) / distanceToPlayer) * blend;
            //double d1 = ((this.getY() - this.getPlayer().getY()) / distanceToPlayer) * blend;
            //double d2 = ((this.getZ() - this.getPlayer().getZ()) / distanceToPlayer) * blend;
            double d0 = ((this.position().x() - playerPos.x()) / distanceToPlayer) * blend;
            double d1 = ((this.position().y() - playerPos.y()) / distanceToPlayer) * blend;
            double d2 = ((this.position().z() - playerPos.z()) / distanceToPlayer) * blend;

            //System.out.println("X: " + xVel + " Y: " + yVel + " Z: " + zVel);

            //double clampX = Mth.clamp(this.position().x() - playerPos.x(), 2.0D, 1.0D);
            //double clampY = Mth.clamp(this.position().y() - playerPos.y(), 2.0D, 1.0D);
            //double clampZ = Mth.clamp(this.position().z() - playerPos.z(), 2.0D, 1.0D);

            //double clampedPosition = clampX + clampY + clampZ;

            double speedxz = 4.0D;// * (clampedPosition / 3);
            double speedy = 2.0D;// * (clampedPosition / 3);

            if (d1 < 0) {
                d1 = 0;
            }

            this.getPlayer().setDeltaMovement(this.getPlayer().getDeltaMovement().add(Math.copySign(d0 * d0 * speedxz, d0), Math.copySign(d1 * d1 * speedy * blend, d1), Math.copySign(d2 * d2 * speedxz, d2)));
            this.getPlayer().checkSlowFallDistance();
            //System.out.println(this.getPlayer().getDeltaMovement());
        }
    }


    //@TODO Pendulum equations
    //private void runSwingPhysics() {
    //    if(KeyBinds.ABILITY_ONE.isDown()) {
    //        if(this.getPlayer() == null) return;
//
    //        System.out.println(this.pendulum.pendulumCalc());
    //
    //        Vec3 newPosition = vectorFromAngle(this.pendulum.pendulumCalc(this.initialDistance, this.initialDistance / this.position().dot(new Vec3(this.initialDistance, this.initialDistance, this.initialDistance))) - Math.PI / 2, this.initialDistance, this.position().get(Direction.Axis.Z));
    //        this.getPlayer().setDeltaMovement(this.getPlayer().position().subtract(newPosition).normalize());
    //    }
    //}
//
    //public Vec3 vectorFromAngle(double angle, double length, double rotateY) {
    //    double cos = Math.cos(angle) * length;
    //    double sin = Math.sin(angle) * length;
    //    Vec3 vec = new Vec3(cos, sin, 0.0).yRot((float) rotateY);
    //    return vec;
    //}
//
    //private double divideByDouble(Vec3 vec, double d) {
    //    double val1 = vec.x() / d;
    //    double val2 = vec.y() / d;
    //    double val3 = vec.z() / d;
    //    double vecToDouble = Math.abs(Math.sqrt(((val1 - d) * (val1 - d)) + ((val2 - d) * (val2 - d)) + ((val3 - d) * (val3 - d))));
    //    return vecToDouble;
    //}

    @Override
    public void tick() {
        super.tick();

        if (this.alpha > 0.85 && KeyBinds.ABILITY_ONE.isDown()) {
            this.setAlpha(1f);
//            this.runSwingPhysics();
            if (this.getPlayer() != null) {
                SpiderManUtil.swingPlayerAlongPoint(this.getPlayer(), this.position());
            }
        } else {
            fadeOut();
        }

        if (this.alpha == 0) {
            this.remove(RemovalReason.DISCARDED);
        }

        if (!this.level.isClientSide) {
            this.syncAlphaToClient();
            this.syncInitialDistanceToClient();
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
