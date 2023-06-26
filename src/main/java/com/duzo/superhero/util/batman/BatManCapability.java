package com.duzo.superhero.util.batman;

import net.minecraft.util.StringRepresentable;

@Deprecated
/**
 * Replace with {@link com.duzo.superhero.util.SuperheroCapability}
 */
public enum BatManCapability implements StringRepresentable {
;

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase();
    }
}
