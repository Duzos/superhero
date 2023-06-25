package com.duzo.superhero.util.spiderman;

import net.minecraft.util.StringRepresentable;

public enum SpiderManCapability implements StringRepresentable {
    WEB_SHOOTING,
    INVISIBILITY,
    NANOTECH,
    SUPER_STRENGTH,
    FAST_MOBILITY,
    WALL_CLIMBING;

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase();
    }
}
