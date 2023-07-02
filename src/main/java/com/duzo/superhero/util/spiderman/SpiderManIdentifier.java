package com.duzo.superhero.util.spiderman;

import com.duzo.superhero.util.SuperheroCapabilities;
import com.duzo.superhero.util.SuperheroCapability;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EquipmentSlot;

import static com.duzo.superhero.entities.ironman.IronManEntity.fileNameToUsable;
import static com.duzo.superhero.entities.ironman.IronManEntity.nameFromSlot;

@Deprecated
public enum SpiderManIdentifier implements StringRepresentable {
    GWEN {
        @Override
        public boolean isSlim() {
            return true;
        }

        @Override
        public String getLangFileName(EquipmentSlot slot) {
            String slotName = fileNameToUsable(nameFromSlot(slot));

            return "SpiderWoman" + " " + slotName;
        }

        @Override
        public SuperheroCapabilities getCapabilities() {
            return new SuperheroCapabilities().add(SuperheroCapability.WEB_SHOOTING,SuperheroCapability.WALL_CLIMBING,SuperheroCapability.SUPER_STRENGTH,SuperheroCapability.FAST_MOBILITY);
        }
    },
    IRON_SPIDER {
        @Override
        public SuperheroCapabilities getCapabilities() {
            return new SuperheroCapabilities().add(SuperheroCapability.NANOTECH,SuperheroCapability.WEB_SHOOTING,SuperheroCapability.WALL_CLIMBING,SuperheroCapability.SUPER_STRENGTH,SuperheroCapability.FAST_MOBILITY);
        }
    },
    AMAZING_SPIDER_MAN {
        @Override
        public SuperheroCapabilities getCapabilities() {
            return new SuperheroCapabilities().add(SuperheroCapability.WEB_SHOOTING,SuperheroCapability.WALL_CLIMBING,SuperheroCapability.SUPER_STRENGTH,SuperheroCapability.FAST_MOBILITY);
        }
    },
    MILES_CLOTHED {
        @Override
        public boolean isSlim() {
            return true;
        }

        @Override
        public String getLangFileName(EquipmentSlot slot) {
            String slotName = fileNameToUsable(nameFromSlot(slot));

            return "Miles" + " " + slotName;
        }

        @Override
        public SuperheroCapabilities getCapabilities() {
            return new SuperheroCapabilities().add(SuperheroCapability.INVISIBILITY,SuperheroCapability.WEB_SHOOTING,SuperheroCapability.WALL_CLIMBING,SuperheroCapability.SUPER_STRENGTH,SuperheroCapability.FAST_MOBILITY);
        }
    },
    MILES {
        @Override
        public boolean isSlim() {
            return true;
        }

        @Override
        public String getLangFileName(EquipmentSlot slot) {
            String slotName = fileNameToUsable(nameFromSlot(slot));

            return "Miles" + " " + slotName;
        }

        @Override
        public SuperheroCapabilities getCapabilities() {
            return new SuperheroCapabilities().add(SuperheroCapability.INVISIBILITY,SuperheroCapability.WEB_SHOOTING,SuperheroCapability.WALL_CLIMBING,SuperheroCapability.SUPER_STRENGTH,SuperheroCapability.FAST_MOBILITY);
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
    public abstract SuperheroCapabilities getCapabilities();
}
