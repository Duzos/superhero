package com.duzo.superhero.util.spiderman;

import net.minecraft.util.StringRepresentable;

public enum SpiderManIdentifier implements StringRepresentable {
    MILES {
        @Override
        public boolean isSlim() {
            return false;
        }
    };

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase();
    }

    public boolean isSlim() {
        return false;
    }
    public boolean autoAdd() {
        return true;
    }
}
