package com.duzo.superhero.util.ironman;

import com.duzo.superhero.capabilities.SuperheroCapability;
import net.minecraft.util.StringRepresentable;

@Deprecated
/**
 * Replace with {@link SuperheroCapability}
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
