package com.duzo.superhero.util;

import net.minecraft.util.StringRepresentable;

public enum IronManMark implements StringRepresentable {
    MARK_7() {
        @Override
        public IronManCapabilities getCapabilities() {
            return new IronManCapabilities().add(IronManCapability.BRACELET_LOCATING);
        }

        @Override
        public double getVerticalFlightSpeed() {
            return 0.0175;
        }

        @Override
        public double getHorizontalFlightSpeed(boolean isSprinting) {
            if (isSprinting) {
                return 0.8d;
            }
            return 4d;
        }
    },
    MARK_5() {
        @Override
        public IronManCapabilities getCapabilities() {
            return new IronManCapabilities().add(IronManCapability.SUITCASE);
        }

        @Override
        public double getVerticalFlightSpeed() {
            return 0.015;
        }

        @Override
        public double getHorizontalFlightSpeed(boolean isSprinting) {
            if (isSprinting) {
                return 0.75d;
            }
            return 4d;
        }
    },
    MARK_9() {
        @Override
        public IronManCapabilities getCapabilities() {
            return new IronManCapabilities().add(IronManCapability.SEAMLESS);
        }


        @Override
        public double getVerticalFlightSpeed() {
            return 0.02;
        }

        @Override
        public double getHorizontalFlightSpeed(boolean isSprinting) {
            if (isSprinting) {
                return 1.75d;
            }
            return 4d;
        }
    },
    MARK_2() {
        @Override
        public IronManCapabilities getCapabilities() {
            return new IronManCapabilities().add(IronManCapability.ICES_OVER);
        }

        @Override
        public double getHorizontalFlightSpeed(boolean isSprinting) {
            if (isSprinting) {
                return 0.4d;
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
