package com.duzo.superhero.util;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EquipmentSlot;

import static com.duzo.superhero.entities.IronManEntity.fileNameToUsable;
import static com.duzo.superhero.entities.IronManEntity.nameFromSlot;

public enum IronManMark implements StringRepresentable {
    MARK_20() {
        @Override
        public IronManCapabilities getCapabilities() {
            return new IronManCapabilities().add(IronManCapability.SEAMLESS,IronManCapability.JARVIS);
        }

        @Override
        public double getVerticalFlightSpeed() {
            return 0.03;
        }

        @Override
        public double getHorizontalFlightSpeed(boolean isSprinting) {
            if (isSprinting) {
                return 4d;
            }
            return 1.25d;
        }
    },
    MARK_14() {
        @Override
        public IronManCapabilities getCapabilities() {
            return new IronManCapabilities().add(IronManCapability.SEAMLESS,IronManCapability.JARVIS);
        }

        @Override
        public double getVerticalFlightSpeed() {
            return 0.025;
        }

        @Override
        public double getHorizontalFlightSpeed(boolean isSprinting) {
            if (isSprinting) {
                return 1.5d;
            }
            return 2d;
        }
    },
    MARK_9() {
        @Override
        public IronManCapabilities getCapabilities() {
            return new IronManCapabilities().add(IronManCapability.SEAMLESS,IronManCapability.JARVIS);
        }


        @Override
        public double getVerticalFlightSpeed() {
            return 0.02;
        }

        @Override
        public double getHorizontalFlightSpeed(boolean isSprinting) {
            if (isSprinting) {
                return 1.25;
            }
            return 4d;
        }
    },
    MARK_7() {
        @Override
        public IronManCapabilities getCapabilities() {
            return new IronManCapabilities().add(IronManCapability.BRACELET_LOCATING,IronManCapability.JARVIS);
        }

        @Override
        public double getVerticalFlightSpeed() {
            return 0.0175;
        }

        @Override
        public double getHorizontalFlightSpeed(boolean isSprinting) {
            if (isSprinting) {
                return 1d;
            }
            return 4d;
        }
    },
    MARK_5() {
        @Override
        public IronManCapabilities getCapabilities() {
            return new IronManCapabilities().add(IronManCapability.SUITCASE,IronManCapability.JARVIS);
        }

        @Override
        public double getVerticalFlightSpeed() {
            return 0.015;
        }

        @Override
        public double getHorizontalFlightSpeed(boolean isSprinting) {
            if (isSprinting) {
                return 0.8d;
            }
            return 4d;
        }
    },
    MARK_2() {
        @Override
        public IronManCapabilities getCapabilities() {
            return new IronManCapabilities().add(IronManCapability.ICES_OVER,IronManCapability.JARVIS);
        }

        @Override
        public double getHorizontalFlightSpeed(boolean isSprinting) {
            if (isSprinting) {
                return 0.5d;
            }
            return 4d;
        }

    },
    MARK_1() {
        @Override
        public IronManCapabilities getCapabilities() {
            return new IronManCapabilities().add(IronManCapability.ICES_OVER);
        }

        @Override
        public double getVerticalFlightSpeed() {
            return 0.005D;
        }

        @Override
        public double getHorizontalFlightSpeed(boolean isSprinting) {
            if (isSprinting) {
                return 0.25d;
            }
            return 6d;
        }
    };
    @Override
    public String getSerializedName() {
        return this.name().toLowerCase();
    }
    public String getLangFileName(EquipmentSlot slot) {
        String base = fileNameToUsable(this.getSerializedName());
        String slotName = fileNameToUsable(nameFromSlot(slot));

        return "Iron Man" + " " + slotName;
    }
    public String getLangFileName() {
        return "Iron Man";
    }
    public int getMarkNumber() {
        return this.name().charAt(-1);
    }

    public abstract IronManCapabilities getCapabilities();

    public double getVerticalFlightSpeed() {
        return 0.01D;
    }
    public double getVerticalFlightSpeedForBoots() {
        return this.getVerticalFlightSpeed();
    }

    public abstract double getHorizontalFlightSpeed(boolean isSprinting);
    public boolean autoAdd() {
        return true;
    }
}
