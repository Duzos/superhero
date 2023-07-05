package com.duzo.superhero.util;

import com.duzo.superhero.entities.SuperheroEntities;
import com.duzo.superhero.entities.ironman.UnibeamEntity;
import com.duzo.superhero.items.SuperheroArmourItem;
import com.duzo.superhero.items.SuperheroNanotechItem;
import com.duzo.superhero.sounds.SuperheroSounds;
import com.duzo.superhero.util.flash.FlashUtil;
import com.duzo.superhero.util.ironman.IronManUtil;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import static com.duzo.superhero.blocks.IronManSuitCaseBlock.convertArmourToSuitcase;
import static com.duzo.superhero.entities.ironman.IronManEntity.fileNameToUsable;
import static com.duzo.superhero.entities.ironman.IronManEntity.spawnNew;
import static com.duzo.superhero.items.SuperheroNanotechItem.convertArmourToNanotech;
import static com.duzo.superhero.items.SuperheroNanotechItem.convertNanotechToArmour;
import static com.duzo.superhero.util.SuperheroUtil.getIDFromStack;
import static com.duzo.superhero.util.ironman.IronManUtil.FlightUtil.bootsOnlyFlight;
import static com.duzo.superhero.util.ironman.IronManUtil.FlightUtil.runFlight;
import static com.duzo.superhero.util.spiderman.SpiderManUtil.*;

public enum SuperheroCapability implements StringRepresentable {
    // GENERIC
    INVISIBILITY {
        @Override
        public void runAbility(int num, Player player) {
            if (num == 2) {
                runMilesInvisibility(player);
            }
        }

        @Override
        public void tick(ItemStack stack, Level level, Player player) {

        }

        @Override
        public void unequippedTick(ItemStack stack, Level level, Player player) {

        }
    },
    NANOTECH {
        @Override
        public void runAbility(int num, Player player) {
            ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
            if (num == 2) {
                convertArmourToNanotech(player);
                if (IronManUtil.isIronManSuit(getIDFromStack(chest))) {
                    player.getLevel().playSound(null, player, SuperheroSounds.IRONMAN_POWERDOWN.get(), SoundSource.PLAYERS, 1f, 1f);
                }
            }
            if (num == 1) {
                if (chest.getItem() instanceof SuperheroNanotechItem) {
                    convertNanotechToArmour(chest,player);
                }
            }
        }

        @Override
        public void tick(ItemStack stack, Level level, Player player) {

        }

        @Override
        public void unequippedTick(ItemStack stack, Level level, Player player) {

        }
    },
    SUPER_STRENGTH {
        @Override
        public void runAbility(int num, Player player) {

        }

        @Override
        public void tick(ItemStack stack, Level level, Player player) {
            if (getIDFromStack(stack).isValidArmour(player)) {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 2 * 20, 1, false, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2 * 20, 1, false, false, false));
            }
        }

        @Override
        public void unequippedTick(ItemStack stack, Level level, Player player) {

        }
    },
    FAST_MOBILITY {
        @Override
        public void runAbility(int num, Player player) {

        }

        @Override
        public void tick(ItemStack stack, Level level, Player player) {
            if (getIDFromStack(stack).isValidArmour(player)) {
                player.addEffect(new MobEffectInstance(MobEffects.JUMP, 2 * 20, 1, false, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2 * 20, 1, false, false, false));
            }
        }

        @Override
        public void unequippedTick(ItemStack stack, Level level, Player player) {

        }
    },
    NIGHT_VISION {
        @Override
        public void runAbility(int num, Player player) {

        }

        @Override
        public void tick(ItemStack stack, Level level, Player player) {
            if (getIDFromStack(stack).isValidArmour(player)) {
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 11 * 20, 1, false, false, false));
            }
        }

        @Override
        public void unequippedTick(ItemStack stack, Level level, Player player) {

        }
    },

    // FLASH
    SPEEDSTER {
        @Override
        public void runAbility(int num, Player player) {
            if (num == 1) {
                double speed = FlashUtil.changeSpeed(player, Screen.hasControlDown());
                player.displayClientMessage(Component.literal("Speed: " + speed),true);
            }
        }

        @Override
        public void tick(ItemStack stack, Level level, Player player) {
            if (getIDFromStack(stack).isValidArmour(player)) {
                FlashUtil.modifyPlayerSpeed(player);
            }
        }

        @Override
        public void unequippedTick(ItemStack stack, Level level, Player player) {

        }
    },

    // BATMAN
    GRAPPLING_HOOK {
        @Override
        public void runAbility(int num, Player player) {

        }

        @Override
        public void tick(ItemStack stack, Level level, Player player) {

        }

        @Override
        public void unequippedTick(ItemStack stack, Level level, Player player) {

        }
    },

    // IRONMAN
    NIGHT_VISION_HELMET_ONLY {
        @Override
        public void runAbility(int num, Player player) {

        }

        @Override
        public void tick(ItemStack stack, Level level, Player player) {
            if (stack.getEquipmentSlot() == EquipmentSlot.HEAD && player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof SuperheroArmourItem) {
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 11 * 20, 1, false, false, false));
            }
        }

        @Override
        public void unequippedTick(ItemStack stack, Level level, Player player) {

        }

        @Override
        public String getNameForText() {
            return NIGHT_VISION.getNameForText();
        }
    },
    SUITCASE {
        @Override
        public void runAbility(int num, Player player) {
            if (num == 1) {
                convertArmourToSuitcase(player);
                player.getLevel().playSound(null,player, SuperheroSounds.IRONMAN_POWERDOWN.get(), SoundSource.PLAYERS,1f,1f);
            }
        }

        @Override
        public void tick(ItemStack stack, Level level, Player player) {

        }

        @Override
        public void unequippedTick(ItemStack stack, Level level, Player player) {

        }
    },
    SEAMLESS {
        @Override
        public void runAbility(int num, Player player) {
            if (num == 1) {
                ItemStack head = player.getItemBySlot(EquipmentSlot.HEAD);
                SuperheroArmourItem item = (SuperheroArmourItem) head.getItem();

                spawnNew(item.getIdentifier(),player.getLevel(),player.getOnPos(),player);
                player.getLevel().playSound(null,player, SuperheroSounds.IRONMAN_POWERDOWN.get(), SoundSource.PLAYERS,1f,1f);
            }
        }

        @Override
        public void tick(ItemStack stack, Level level, Player player) {

        }

        @Override
        public void unequippedTick(ItemStack stack, Level level, Player player) {

        }
    },
    BRACELET_LOCATING {
        @Override
        public void runAbility(int num, Player player) {
            SEAMLESS.runAbility(num,player);
        }

        @Override
        public void tick(ItemStack stack, Level level, Player player) {

        }

        @Override
        public void unequippedTick(ItemStack stack, Level level, Player player) {

        }
    },
    BINDING {
        @Override
        public void runAbility(int num, Player player) {

        }

        @Override
        public void tick(ItemStack stack, Level level, Player player) {

        }

        @Override
        public void unequippedTick(ItemStack stack, Level level, Player player) {

        }
    },
    ICES_OVER {
        @Override
        public void runAbility(int num, Player player) {

        }

        @Override
        public void tick(ItemStack stack, Level level, Player player) {
            if (player.getY() > 185) {
                player.addEffect(new MobEffectInstance(MobEffects.WITHER, 2 * 20, 0, false, false, false));
            }
        }

        @Override
        public void unequippedTick(ItemStack stack, Level level, Player player) {

        }
    },
    JARVIS {
        @Override
        public void runAbility(int num, Player player) {

        }

        @Override
        public void tick(ItemStack stack, Level level, Player player) {

        }

        @Override
        public void unequippedTick(ItemStack stack, Level level, Player player) {

        }
    },
    BLAST_OFF {
        @Override
        public void runAbility(int num, Player player) {

        }

        @Override
        public void tick(ItemStack stack, Level level, Player player) {

        }

        @Override
        public void unequippedTick(ItemStack stack, Level level, Player player) {

        }
    },

    /**
     * Requires the identifier to have "vertical" and "blastoff" values set in its custom values
     */
    IRON_MAN_FLIGHT {
        @Override
        public void runAbility(int num, Player player) {

        }

        @Override
        public void tick(ItemStack stack, Level level, Player player) {
            if (getIDFromStack(stack).isValidArmour(player)) {
                runFlight(player,getIDFromStack(stack));
            }

            if (stack.getEquipmentSlot() == EquipmentSlot.FEET && player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof SuperheroArmourItem) {
                if (!getIDFromStack(stack).isValidArmour(player)) {
                    bootsOnlyFlight(player,getIDFromStack(stack));
                }
            }
        }

        @Override
        public void unequippedTick(ItemStack stack, Level level, Player player) {

        }
    },
    IRON_MAN_WEAPONS {
        @Override
        public void runAbility(int num, Player player) {
            if (num == 2) {
                // Unibeam

                int i = Mth.clamp(0, 0, 64);
                float f2 = Mth.cos(player.yBodyRot * ((float) Math.PI / 180F)) * (0F + 1.21F * (float) i);
                float f3 = Mth.sin(player.yBodyRot * ((float) Math.PI / 180F)) * (0F + 1.21F * (float) i);
                float f6 = (0.3F * 0.45F) * ((float) i * 0.2F + 0.0F);
                UnibeamEntity unibeam = new UnibeamEntity(SuperheroEntities.UNIBEAM_ENTITY.get(), player.getLevel());
                unibeam.moveTo(player.getX() + (double) f2, player.getY() + (double) f6, player.getZ() + (double) f3, player.getYRot(), player.getXRot());
                player.getLevel().addFreshEntity(unibeam);
            }
        }

        @Override
        public void tick(ItemStack stack, Level level, Player player) {

        }

        @Override
        public void unequippedTick(ItemStack stack, Level level, Player player) {

        }
    },

    // SPIDERMAN
    WEB_SHOOTING {
        @Override
        public void runAbility(int num, Player player) {
            if (num == 1) {
                shootWebAndSwingToIt(player);
            }
        }

        @Override
        public void tick(ItemStack stack, Level level, Player player) {

        }

        @Override
        public void unequippedTick(ItemStack stack, Level level, Player player) {

        }
    },
    WALL_CLIMBING {
        @Override
        public void runAbility(int num, Player player) {

        }

        @Override
        public void tick(ItemStack stack, Level level, Player player) {
            if (getIDFromStack(stack).isValidArmour(player)) {
                runWallClimbs(player);
            }
        }

        @Override
        public void unequippedTick(ItemStack stack, Level level, Player player) {

        }
    };
    @Override
    public String getSerializedName() {
        return this.name().toLowerCase();
    }
    public abstract void runAbility(int num,Player player);

    /**
     * Tick thats ran when this suit is equipped
     * @param stack
     * @param level
     * @param player
     */
    public abstract void tick(ItemStack stack, Level level, Player player);

    /**
     * Tick thats ran when this suit is not equipped and is sat in the inventory.
     * @param stack
     * @param level
     * @param player
     */
    public abstract void unequippedTick(ItemStack stack, Level level, Player player);

    public String getNameForText() {
        return fileNameToUsable(this.getSerializedName());
    }
}
