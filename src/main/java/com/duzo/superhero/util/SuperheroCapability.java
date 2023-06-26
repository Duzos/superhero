package com.duzo.superhero.util;

import net.minecraft.util.StringRepresentable;

public enum SuperheroCapability implements StringRepresentable {
    // GENERIC
    INVISIBILITY,
    NANOTECH,
    SUPER_STRENGTH,
    FAST_MOBILITY,

    // IRONMAN
    SUITCASE,
    SEAMLESS,
    BRACELET_LOCATING,
    BINDING,
    ICES_OVER,
    JARVIS,
    BLAST_OFF,

    // SPIDERMAN
    WEB_SHOOTING,
    WALL_CLIMBING;
    @Override
    public String getSerializedName() {
        return this.name().toLowerCase();
    }
}
