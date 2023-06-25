package com.duzo.superhero.util.batman;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EquipmentSlot;

import static com.duzo.superhero.entities.ironman.IronManEntity.fileNameToUsable;
import static com.duzo.superhero.entities.ironman.IronManEntity.nameFromSlot;

public enum BatManIdentifier implements StringRepresentable {
    BATMAN_VS_SUPERMAN {
        @Override
        public BatManCapabilities getCapabilities() {
            return new BatManCapabilities();
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
    public String getLangFileName(EquipmentSlot slot) {
        String base = fileNameToUsable(this.getSerializedName());
        String slotName = fileNameToUsable(nameFromSlot(slot));

        return "Batman" + " " + slotName;
    }
    public abstract BatManCapabilities getCapabilities();
}
