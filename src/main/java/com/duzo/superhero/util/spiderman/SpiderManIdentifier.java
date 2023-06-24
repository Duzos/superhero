package com.duzo.superhero.util.spiderman;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EquipmentSlot;

import static com.duzo.superhero.entities.IronManEntity.fileNameToUsable;
import static com.duzo.superhero.entities.IronManEntity.nameFromSlot;

public enum SpiderManIdentifier implements StringRepresentable {
    MILES {
        @Override
        public boolean isSlim() {
            return false;
        }

        @Override
        public String getLangFileName(EquipmentSlot slot) {
            String slotName = fileNameToUsable(nameFromSlot(slot));

            return "Miles" + " " + slotName;
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

        return "SpiderMan" + " " + slotName;
    }
}
