package com.duzo.superhero.util;

import com.duzo.superhero.capabilities.SuperheroCapabilities;
import com.duzo.superhero.capabilities.SuperheroCapability;
import com.duzo.superhero.items.SuperheroArmourItem;
import com.duzo.superhero.util.ironman.IronManUtil;
import com.duzo.superhero.util.spiderman.SpiderManUtil;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;

import static com.duzo.superhero.entities.ironman.IronManEntity.fileNameToUsable;
import static com.duzo.superhero.entities.ironman.IronManEntity.nameFromSlot;

/**
 * replace with {@link com.duzo.superhero.ids.AbstractIdentifier}
 */
@Deprecated
public enum SuperheroIdentifier implements StringRepresentable {
    // @TODO replace a list of enums with a registry that is editable at runtime for datapack capabilities.
    // FLASH
    FLASH { // Done
        @Override
        public SuperheroCapabilities getCapabilities() {
            return new SuperheroCapabilities(SuperheroCapability.SPEEDSTER,SuperheroCapability.FLASH_HUD);
        }

        @Override
        public String getLangFileName(EquipmentSlot slot) {
            String slotName = fileNameToUsable(nameFromSlot(slot));

            return "Flash" + " " + slotName;
        }
    },

    // BATMAN
    BATMAN_VS_SUPERMAN {
        @Override
        public SuperheroCapabilities getCapabilities() {
            return new SuperheroCapabilities(SuperheroCapability.GRAPPLING_HOOK,SuperheroCapability.SUPER_STRENGTH,SuperheroCapability.NIGHT_VISION_HELMET_ONLY,SuperheroCapability.INVISIBILITY);
        }

        @Override
        public String getLangFileName(EquipmentSlot slot) {
            String slotName = fileNameToUsable(nameFromSlot(slot));

            return "Batman" + " " + slotName;
        }
    },

    // SPIDER-MAN
    GWEN { // Done
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
        public boolean isValidArmour(LivingEntity player) {
            return SpiderManUtil.isValidArmor(player);
        }
        @Override
        public SuperheroCapabilities getCapabilities() {
            return new SuperheroCapabilities().add(SuperheroCapability.WEB_SHOOTING,SuperheroCapability.WALL_CLIMBING,SuperheroCapability.SUPER_STRENGTH,SuperheroCapability.FAST_MOBILITY,SuperheroCapability.SPIDERMAN_HUD);
        }
    },
    IRON_SPIDER { // Done
        @Override
        public boolean isSlim() {
            return true;
        }

        @Override
        public SuperheroCapabilities getCapabilities() {
            return new SuperheroCapabilities().add(SuperheroCapability.NANOTECH,SuperheroCapability.WEB_SHOOTING,SuperheroCapability.WALL_CLIMBING,SuperheroCapability.SUPER_STRENGTH,SuperheroCapability.FAST_MOBILITY,SuperheroCapability.SPIDERMAN_HUD);
        }

        @Override
        public boolean isValidArmour(LivingEntity player) {
            return SpiderManUtil.isValidArmor(player);
        }
        @Override
        public String getLangFileName(EquipmentSlot slot) {
            String slotName = fileNameToUsable(nameFromSlot(slot));

            return "Spider-Man" + " " + slotName;
        }
    },
    /**
     * The default identifier.
     */
    AMAZING_SPIDER_MAN { // Done
        @Override
        public boolean isSlim() {
            return true;
        }

        @Override
        public SuperheroCapabilities getCapabilities() {
            return new SuperheroCapabilities().add(SuperheroCapability.WEB_SHOOTING,SuperheroCapability.WALL_CLIMBING,SuperheroCapability.SUPER_STRENGTH,SuperheroCapability.FAST_MOBILITY,SuperheroCapability.SPIDERMAN_HUD);
        }

        @Override
        public boolean isValidArmour(LivingEntity player) {
            return SpiderManUtil.isValidArmor(player);
        }
        @Override
        public String getLangFileName(EquipmentSlot slot) {
            String slotName = fileNameToUsable(nameFromSlot(slot));

            return "Spider-Man" + " " + slotName;
        }
    },
    MILES_CLOTHED { // Done
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
        public boolean isValidArmour(LivingEntity player) {
            return SpiderManUtil.isValidArmor(player);
        }
        @Override
        public SuperheroCapabilities getCapabilities() {
            return new SuperheroCapabilities().add(SuperheroCapability.INVISIBILITY,SuperheroCapability.WEB_SHOOTING,SuperheroCapability.WALL_CLIMBING,SuperheroCapability.SUPER_STRENGTH,SuperheroCapability.FAST_MOBILITY,SuperheroCapability.SPIDERMAN_HUD);
        }
    },
    MILES { // Done
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
        public boolean isValidArmour(LivingEntity player) {
            return SpiderManUtil.isValidArmor(player);
        }

        @Override
        public SuperheroCapabilities getCapabilities() {
            return new SuperheroCapabilities().add(SuperheroCapability.INVISIBILITY,SuperheroCapability.WEB_SHOOTING,SuperheroCapability.WALL_CLIMBING,SuperheroCapability.SUPER_STRENGTH,SuperheroCapability.FAST_MOBILITY,SuperheroCapability.SPIDERMAN_HUD);
        }
    },

    // IRON MAN
    IRONMAN_MARK_9() {
        @Override
        public SuperheroCapabilities getCapabilities() {
            return new SuperheroCapabilities().add(SuperheroCapability.MASK_TOGGLE, SuperheroCapability.NANOTECH,SuperheroCapability.JARVIS,SuperheroCapability.NIGHT_VISION_HELMET_ONLY,SuperheroCapability.IRON_MAN_WEAPONS,SuperheroCapability.BLAST_OFF,SuperheroCapability.IRON_MAN_FLIGHT);
        }

        @Override
        public HashMap<String, ?> getEnumSpecificValues() {
            HashMap<String, Double> map = new HashMap<>();

            map.put("vertical",0.02);
            map.put("blastOff",2.5d);

            return map;
        }
        @Override
        public boolean usesDefaultRenderer() {
            return false;
        }
        @Override
        public String getLangFileName(EquipmentSlot slot) {
            String slotName = fileNameToUsable(nameFromSlot(slot));

            return "Iron-Man" + " " + slotName;
        }
        @Override
        public String getHoverTextName() {
            return IronManUtil.getHoverTextName(this);
        }
    },
    IRONMAN_MARK_7() {
        @Override
        public SuperheroCapabilities getCapabilities() {
            return new SuperheroCapabilities().add(SuperheroCapability.MASK_TOGGLE, SuperheroCapability.BRACELET_LOCATING,SuperheroCapability.JARVIS,SuperheroCapability.NIGHT_VISION_HELMET_ONLY,SuperheroCapability.IRON_MAN_WEAPONS,SuperheroCapability.BLAST_OFF,SuperheroCapability.IRON_MAN_FLIGHT);
        }

        @Override
        public HashMap<String, ?> getEnumSpecificValues() {
            HashMap<String, Double> map = new HashMap<>();

            map.put("vertical",0.0175);
            map.put("blastOff",2d);

            return map;
        }
        @Override
        public boolean usesDefaultRenderer() {
            return false;
        }
        @Override
        public String getLangFileName(EquipmentSlot slot) {
            String slotName = fileNameToUsable(nameFromSlot(slot));

            return "Iron-Man" + " " + slotName;
        }
        @Override
        public String getHoverTextName() {
            return IronManUtil.getHoverTextName(this);
        }
    },
    IRONMAN_MARK_5() {
        @Override
        public SuperheroCapabilities getCapabilities() {
            return new SuperheroCapabilities().add(SuperheroCapability.SUITCASE,SuperheroCapability.JARVIS,SuperheroCapability.NIGHT_VISION_HELMET_ONLY,SuperheroCapability.IRON_MAN_WEAPONS,SuperheroCapability.BLAST_OFF,SuperheroCapability.IRON_MAN_FLIGHT);
        }

        @Override
        public HashMap<String, ?> getEnumSpecificValues() {
            HashMap<String, Double> map = new HashMap<>();

            map.put("vertical",0.015);
            map.put("blastOff",1.5d);

            return map;
        }
        @Override
        public boolean usesDefaultRenderer() {
            return false;
        }
        @Override
        public String getLangFileName(EquipmentSlot slot) {
            String slotName = fileNameToUsable(nameFromSlot(slot));

            return "Iron-Man" + " " + slotName;
        }
        @Override
        public String getHoverTextName() {
            return IronManUtil.getHoverTextName(this);
        }
    },
    IRONMAN_MARK_2() {
        @Override
        public SuperheroCapabilities getCapabilities() {
            return new SuperheroCapabilities().add(SuperheroCapability.MASK_TOGGLE, SuperheroCapability.ICES_OVER,SuperheroCapability.JARVIS,SuperheroCapability.NIGHT_VISION_HELMET_ONLY,SuperheroCapability.BLAST_OFF,SuperheroCapability.BINDING,SuperheroCapability.IRON_MAN_FLIGHT);
        }

        @Override
        public HashMap<String, ?> getEnumSpecificValues() {
            HashMap<String, Double> map = new HashMap<>();

            map.put("vertical",0.01);
            map.put("blastOff",1.25d);

            return map;

        }
        @Override
        public boolean usesDefaultRenderer() {
            return false;
        }

        @Override
        public String getLangFileName(EquipmentSlot slot) {
            String slotName = fileNameToUsable(nameFromSlot(slot));

            return "Iron-Man" + " " + slotName;
        }
        @Override
        public String getHoverTextName() {
            return IronManUtil.getHoverTextName(this);
        }
    },
    IRONMAN_MARK_1() {
        @Override
        public SuperheroCapabilities getCapabilities() {
            return new SuperheroCapabilities().add(SuperheroCapability.MASK_TOGGLE, SuperheroCapability.ICES_OVER,SuperheroCapability.BINDING,SuperheroCapability.IRON_MAN_FLIGHT);
        }

        @Override
        public boolean usesDefaultRenderer() {
            return false;
        }

        @Override
        public HashMap<String, ?> getEnumSpecificValues() {
            HashMap<String, Double> map = new HashMap<>();

            map.put("vertical",0.005);
            map.put("blastOff",1d);

            return map;
        }
        @Override
        public String getLangFileName(EquipmentSlot slot) {
            String slotName = fileNameToUsable(nameFromSlot(slot));

            return "Iron-Man" + " " + slotName;
        }

        @Override
        public String getHoverTextName() {
            return IronManUtil.getHoverTextName(this);
        }
    };
    // GENERIC;

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

        return "Superhero" + " " + slotName;
    }
    public String getHoverTextName() {
        return fileNameToUsable(this.getSerializedName());
    }
    public abstract SuperheroCapabilities getCapabilities();
    public HashMap<String,?> getEnumSpecificValues() {
        return new HashMap<>();
    }

    public boolean isValidArmour(LivingEntity player) {
        SuperheroIdentifier currentID = null;

        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            if (!equipmentSlot.isArmor()) continue;
            ItemStack currentSlot = player.getItemBySlot(equipmentSlot);
            if (currentSlot.getItem() instanceof SuperheroArmourItem item) {
                if (currentID == null) {
                    currentID = item.getIdentifier();
                } else if (!currentID.equals(item.getIdentifier())) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }
    public boolean usesDefaultRenderer() {
        return true;
    }
}
