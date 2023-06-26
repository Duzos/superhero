package com.duzo.superhero.util.ironman;

import com.duzo.superhero.util.SuperheroCapabilities;
import com.duzo.superhero.util.SuperheroCapability;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EquipmentSlot;

import static com.duzo.superhero.entities.ironman.IronManEntity.fileNameToUsable;
import static com.duzo.superhero.entities.ironman.IronManEntity.nameFromSlot;

public enum IronManMark implements StringRepresentable {
//    MARK_20() {
//        @Override
//        public SuperheroCapabilities getCapabilities() {
//            return new SuperheroCapabilities().add(SuperheroCapability.SEAMLESS,SuperheroCapability.JARVIS);
//        }
//
//        @Override
//        public double getVerticalFlightSpeed() {
//            return 0.03;
//        }
//
//
//    },
//    MARK_14() {
//        @Override
//        public SuperheroCapabilities getCapabilities() {
//            return new SuperheroCapabilities().add(SuperheroCapability.SEAMLESS,SuperheroCapability.JARVIS);
//        }
//
//        @Override
//        public double getVerticalFlightSpeed() {
//            return 0.025;
//        }
//
//
//    },
    MARK_9() {
        @Override
        public SuperheroCapabilities getCapabilities() {
            return new SuperheroCapabilities().add(SuperheroCapability.NANOTECH,SuperheroCapability.JARVIS,SuperheroCapability.BLAST_OFF);
        }


        @Override
        public double getVerticalFlightSpeed() {
            return 0.02;
        }

    @Override
    public double getBlastOffSpeed() {
        return 2.5d;
    }

    },
    MARK_7() {
        @Override
        public SuperheroCapabilities getCapabilities() {
            return new SuperheroCapabilities().add(SuperheroCapability.BRACELET_LOCATING,SuperheroCapability.JARVIS,SuperheroCapability.BLAST_OFF);
        }

        @Override
        public double getVerticalFlightSpeed() {
            return 0.0175;
        }

        @Override
        public double getBlastOffSpeed() {
            return 2d;
        }


    },
    MARK_5() {
        @Override
        public SuperheroCapabilities getCapabilities() {
            return new SuperheroCapabilities().add(SuperheroCapability.SUITCASE,SuperheroCapability.JARVIS,SuperheroCapability.BLAST_OFF);
        }

        @Override
        public double getVerticalFlightSpeed() {
            return 0.015;
        }

        @Override
        public double getBlastOffSpeed() {
            return 1.5d;
        }


    },
    MARK_2() {
        @Override
        public SuperheroCapabilities getCapabilities() {
            return new SuperheroCapabilities().add(SuperheroCapability.ICES_OVER,SuperheroCapability.JARVIS,SuperheroCapability.BLAST_OFF,SuperheroCapability.BINDING);
        }

        @Override
        public double getBlastOffSpeed() {
            return 1.25d;
        }


    },
    MARK_1() {
        @Override
        public SuperheroCapabilities getCapabilities() {
            return new SuperheroCapabilities().add(SuperheroCapability.ICES_OVER,SuperheroCapability.BINDING);
        }

        @Override
        public double getVerticalFlightSpeed() {
            return 0.005D;
        }

        @Override
        public double getBlastOffSpeed() {
            return 1d;
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
    @Deprecated
    public int getMarkNumber() {
        return this.name().charAt(-1);
    }

    public abstract SuperheroCapabilities getCapabilities();

    public double getVerticalFlightSpeed() {
        return 0.01D;
    }
    public double getVerticalFlightSpeedForBoots() {
        return this.getVerticalFlightSpeed();
    }

    public abstract double getBlastOffSpeed();
    public boolean autoAdd() {
        return true;
    }
}
