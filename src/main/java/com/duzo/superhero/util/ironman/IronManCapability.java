package com.duzo.superhero.util.ironman;

import net.minecraft.util.StringRepresentable;

@Deprecated
/**
 * Replace with {@link com.duzo.superhero.util.SuperheroCapability}
 */
public enum IronManCapability implements StringRepresentable {
    SUITCASE,
    SEAMLESS,
    BRACELET_LOCATING,
    BINDING,
    ICES_OVER,
    JARVIS,
    NANOTECH,
    BLAST_OFF;

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase();
    }
}
