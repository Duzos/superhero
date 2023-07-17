package com.duzo.superhero.util.batman;

import com.duzo.superhero.capabilities.SuperheroCapability;
import net.minecraft.util.StringRepresentable;

@Deprecated
/**
 * Replace with {@link SuperheroCapability}
 */
public enum BatManCapability implements StringRepresentable {
;

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase();
    }
}
