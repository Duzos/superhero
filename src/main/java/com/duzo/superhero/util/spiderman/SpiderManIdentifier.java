package com.duzo.superhero.util.spiderman;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EquipmentSlot;

import static com.duzo.superhero.entities.ironman.IronManEntity.fileNameToUsable;
import static com.duzo.superhero.entities.ironman.IronManEntity.nameFromSlot;

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
        public SpiderManCapabilities getCapabilities() {
            return new SpiderManCapabilities().add(SpiderManCapability.WEB_SHOOTING,SpiderManCapability.WALL_CLIMBING,SpiderManCapability.SUPER_STRENGTH,SpiderManCapability.FAST_MOBILITY);
        }
    },
    IRON_SPIDER {
        @Override
        public SpiderManCapabilities getCapabilities() {
            return new SpiderManCapabilities().add(SpiderManCapability.NANOTECH,SpiderManCapability.WEB_SHOOTING,SpiderManCapability.WALL_CLIMBING,SpiderManCapability.SUPER_STRENGTH,SpiderManCapability.FAST_MOBILITY);
        }
    },
    AMAZING_SPIDER_MAN {
        @Override
        public SpiderManCapabilities getCapabilities() {
            return new SpiderManCapabilities().add(SpiderManCapability.WEB_SHOOTING,SpiderManCapability.WALL_CLIMBING,SpiderManCapability.SUPER_STRENGTH,SpiderManCapability.FAST_MOBILITY);
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
        public SpiderManCapabilities getCapabilities() {
            return new SpiderManCapabilities().add(SpiderManCapability.INVISIBILITY,SpiderManCapability.WEB_SHOOTING,SpiderManCapability.WALL_CLIMBING,SpiderManCapability.SUPER_STRENGTH,SpiderManCapability.FAST_MOBILITY);
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
        public SpiderManCapabilities getCapabilities() {
            return new SpiderManCapabilities().add(SpiderManCapability.INVISIBILITY,SpiderManCapability.WEB_SHOOTING,SpiderManCapability.WALL_CLIMBING,SpiderManCapability.SUPER_STRENGTH,SpiderManCapability.FAST_MOBILITY);
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
    public abstract SpiderManCapabilities getCapabilities();
}
