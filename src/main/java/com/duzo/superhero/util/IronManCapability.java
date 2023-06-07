package com.duzo.superhero.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.StringRepresentable;

public enum IronManCapability implements StringRepresentable {
    SUITCASE,
    SEAMLESS,
    BRACELET_LOCATING,
    ICES_OVER,
    JARVIS,
    NANOTECH;

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase();
    }
}
