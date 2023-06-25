package com.duzo.superhero.util.batman;

import net.minecraft.util.StringRepresentable;

public enum BatManCapability implements StringRepresentable {
;

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase();
    }
}
