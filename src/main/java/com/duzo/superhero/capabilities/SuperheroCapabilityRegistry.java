package com.duzo.superhero.capabilities;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.capabilities.impl.CapabilityBuilder;
import com.duzo.superhero.entities.ironman.RocketEntity;
import com.duzo.superhero.ids.impls.IronManIdentifier;
import com.duzo.superhero.items.SuperheroArmourItem;
import com.duzo.superhero.items.SuperheroNanotechItem;
import com.duzo.superhero.sounds.SuperheroSounds;
import com.duzo.superhero.util.flash.FlashUtil;
import com.duzo.superhero.util.ironman.IronManUtil;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static com.duzo.superhero.blocks.IronManSuitCaseBlock.convertArmourToSuitcase;
import static com.duzo.superhero.entities.ironman.IronManEntity.fileNameToUsable;
import static com.duzo.superhero.entities.ironman.IronManEntity.spawnNew;
import static com.duzo.superhero.items.SuperheroNanotechItem.convertArmourToNanotech;
import static com.duzo.superhero.items.SuperheroNanotechItem.convertNanotechToArmour;
import static com.duzo.superhero.util.SuperheroUtil.getIDFromStack;
import static com.duzo.superhero.util.SuperheroUtil.moveMissingSlotOntoPlayer;
import static com.duzo.superhero.util.ironman.IronManUtil.FlightUtil.bootsOnlyFlight;
import static com.duzo.superhero.util.ironman.IronManUtil.FlightUtil.runFlight;
import static com.duzo.superhero.util.spiderman.SpiderManUtil.*;

public class SuperheroCapabilityRegistry {
    public static final DeferredRegister<AbstractCapability> CAPS = DeferredRegister.create(new ResourceLocation(Superhero.MODID,"capabilities"), Superhero.MODID);

    public static final RegistryObject<AbstractCapability> INVISIBILITY = CAPS.register("invisibility", () -> new CapabilityBuilder("invisibility")
            .runner(new AbstractCapability.abilityRunner() {
                @Override
                public void runAbility(int num, Player player) {
                    if (num == 3) {
                        runMilesInvisibility(player);
                    }
                }
            })
    );
    // @TODO fix, is brokey and using outdated pre-registry stuff
    public static final RegistryObject<AbstractCapability> NANOTECH = CAPS.register("nanotech", () -> new CapabilityBuilder("nanotech")
            .runner(new AbstractCapability.abilityRunner() {
                @Override
                public void runAbility(int num, Player player) {
                    ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
                    if (num == 3) {
                        convertArmourToNanotech(player);
                        if (IronManUtil.isIronManSuit(getIDFromStack(chest))) {
                            player.level().playSound(null, player, SuperheroSounds.IRONMAN_POWERDOWN.get(), SoundSource.PLAYERS, 1f, 1f);
                        }
                    }
                    if (num == 1) {
                        if (chest.getItem() instanceof SuperheroNanotechItem) {
                            convertNanotechToArmour(chest,player);
                        }
                    }
                }
            })
    );
    public static final RegistryObject<AbstractCapability> MASK_TOGGLE = CAPS.register("mask_toggle", () -> new CapabilityBuilder("mask_toggle")
            .runner(new AbstractCapability.abilityRunner() {
                @Override
                public void runAbility(int num, Player player) {
                    if (num == 2) {
                        // If theres a helmet put it in the inventory
                        if (!player.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
                            player.getInventory().placeItemBackInInventory(player.getItemBySlot(EquipmentSlot.HEAD));
                            player.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                        }
                        // Otherwise find a valid helmet and put it on the head
                        else {
                            moveMissingSlotOntoPlayer(player, EquipmentSlot.HEAD);
                        }
                    }
                }
            })
    );
    public static final RegistryObject<AbstractCapability> SUPER_STRENGTH = CAPS.register("super_strength", () -> new CapabilityBuilder("super_strength")
            .runner(new AbstractCapability.abilityRunner() {
                @Override
                public void tick(ItemStack stack, Level level, Player player) {
                    if (getIDFromStack(stack).isValidArmour(player)) {
                        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 2 * 20, 1, false, false, false));
                        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2 * 20, 1, false, false, false));
                    }
                }
            })
    );
    public static final RegistryObject<AbstractCapability> FAST_MOBILITY = CAPS.register("fast_mobility", () -> new CapabilityBuilder("fast_mobility")
            .runner(new AbstractCapability.abilityRunner() {
                @Override
                public void tick(ItemStack stack, Level level, Player player) {
                    if (getIDFromStack(stack).isValidArmour(player)) {
                        player.addEffect(new MobEffectInstance(MobEffects.JUMP, 2 * 20, 1, false, false, false));
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2 * 20, 1, false, false, false));
                    }
                }
            })
    );
    public static final RegistryObject<AbstractCapability> NIGHT_VISION = CAPS.register("night_vision", () -> new CapabilityBuilder("night_vision")
            .runner(new AbstractCapability.abilityRunner() {
                @Override
                public void tick(ItemStack stack, Level level, Player player) {
                    if (getIDFromStack(stack).isValidArmour(player)) {
                        player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 11 * 20, 1, false, false, false));
                    }
                }
            })
    );

    // Flash
    public static final RegistryObject<AbstractCapability> SPEEDSTER = CAPS.register("speedster", () -> new CapabilityBuilder("speedster")
            .runner(new AbstractCapability.abilityRunner() {
                @Override
                public void runAbility(int num, Player player) {
                    if (num == 1) {
                        double speed = FlashUtil.changeSpeed(player, Screen.hasControlDown());
//                player.displayClientMessage(Component.literal("Speed: " + speed),true);
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
                    ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
                    if (chest.getItem() instanceof SuperheroArmourItem) {
                        if (!getIDFromStack(chest).isValidArmour(player)) {
                            FlashUtil.setSpeed(player,1.0d);
                            FlashUtil.removeFlashSpeed(player);
                        }
                    } else {
                        FlashUtil.setSpeed(player,1.0d);
                        FlashUtil.removeFlashSpeed(player);
                    }
                }
            })
    );
    public static final RegistryObject<AbstractCapability> FLASH_HUD = CAPS.register("flash_hud", () -> new CapabilityBuilder("flash_hud"));

    // Batman
    public static final RegistryObject<AbstractCapability> GRAPPLING_HOOK = CAPS.register("grappling_hook", () -> new CapabilityBuilder("grappling_hook"));

    // IronMan
    public static final RegistryObject<AbstractCapability> MASK_TOGGLE_IRONMAN = CAPS.register("mask_toggle_ironman", () -> new CapabilityBuilder("mask_toggle_ironman")
            .runner(new AbstractCapability.abilityRunner() {
                @Override
                public void runAbility(int num, Player player) {
                    ItemStack head = player.getItemBySlot(EquipmentSlot.HEAD);
                    if (num == 2) {
                        if (IronManUtil.isIronManSuit(getIDFromStack(head))) {
                            //SuperheroData superheroData = new SuperheroData(player);
                            System.out.println("HELLO");
                        }
                    }
                }
            })
    );
    public static final RegistryObject<AbstractCapability> NIGHT_VISION_HELMET_ONLY = CAPS.register("night_vision_helmet_only", () -> new CapabilityBuilder("night_vision_helmet_only")
            .runner(new AbstractCapability.abilityRunner() {
                @Override
                public void tick(ItemStack stack, Level level, Player player) {
                    if (stack.getEquipmentSlot() == EquipmentSlot.HEAD && player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof SuperheroArmourItem) {
                        player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 11 * 20, 1, false, false, false));
                    }
                }
            })
            .nameForText(fileNameToUsable("night_vision"))
    );
    public static final RegistryObject<AbstractCapability> SUITCASE = CAPS.register("suitcase",() -> new CapabilityBuilder("suitcase")
            .runner(new AbstractCapability.abilityRunner() {
                @Override
                public void runAbility(int num, Player player) {
                    if (num == 1) {
                        convertArmourToSuitcase(player);
                        player.level().playSound(null,player, SuperheroSounds.IRONMAN_POWERDOWN.get(), SoundSource.PLAYERS,1f,1f);
                    }
                }
            })
    );
    public static final RegistryObject<AbstractCapability> SEAMLESS = CAPS.register("seamless",() -> new CapabilityBuilder("seamless")
            .runner(new AbstractCapability.abilityRunner() {
                @Override
                public void runAbility(int num, Player player) {
                    if (num == 1) {
                        ItemStack head = player.getItemBySlot(EquipmentSlot.HEAD);
                        SuperheroArmourItem item = (SuperheroArmourItem) head.getItem();

                        if (!IronManUtil.isIronManSuit(item.getIdentifier())) return;

                        spawnNew((IronManIdentifier) item.getIdentifier(),player.level(),player.getOnPos(),player);
                        player.level().playSound(null,player, SuperheroSounds.IRONMAN_POWERDOWN.get(), SoundSource.PLAYERS,1f,1f);
                    }
                }
            })
    );
    public static final RegistryObject<AbstractCapability> BRACELET_LOCATING = CAPS.register("bracelet_locating",() -> new CapabilityBuilder("bracelet_locating")
            .runner(new AbstractCapability.abilityRunner() {
                @Override
                public void runAbility(int num, Player player) {
                    if (num == 1) {
                        ItemStack head = player.getItemBySlot(EquipmentSlot.HEAD);
                        SuperheroArmourItem item = (SuperheroArmourItem) head.getItem();

                        if (!IronManUtil.isIronManSuit(item.getIdentifier())) return;

                        spawnNew((IronManIdentifier) item.getIdentifier(),player.level(),player.getOnPos(),player);
                        player.level().playSound(null,player, SuperheroSounds.IRONMAN_POWERDOWN.get(), SoundSource.PLAYERS,1f,1f);
                    }
                }
            })
    );
    public static final RegistryObject<AbstractCapability> BINDING = CAPS.register("binding",() -> new CapabilityBuilder("binding"));
    public static final RegistryObject<AbstractCapability> ICES_OVER = CAPS.register("ices_over",() -> new CapabilityBuilder("ices_over")
            .runner(new AbstractCapability.abilityRunner() {
                @Override
                public void tick(ItemStack stack, Level level, Player player) {
                    if (player.getY() > 185) {
                        player.addEffect(new MobEffectInstance(MobEffects.WITHER, 2 * 20, 0, false, false, false));
                    }
                }
            })
    );
    public static final RegistryObject<AbstractCapability> JARVIS = CAPS.register("jarvis",() -> new CapabilityBuilder("jarvis"));
    public static final RegistryObject<AbstractCapability> BLAST_OFF = CAPS.register("blast_off",() -> new CapabilityBuilder("blast_off"));
    public static final RegistryObject<AbstractCapability> IRON_MAN_FLIGHT = CAPS.register("iron_man_flight",() -> new CapabilityBuilder("iron_man_flight")
            .runner(new AbstractCapability.abilityRunner() {
                @Override
                public void tick(ItemStack stack, Level level, Player player) {
                    if (getIDFromStack(stack).isValidArmour(player)) {
                        if (IronManUtil.isIronManSuit(stack)) {
                            runFlight(player, (IronManIdentifier) getIDFromStack(stack));
                        }
                    }

                    if (stack.getEquipmentSlot() == EquipmentSlot.FEET && player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof SuperheroArmourItem) {
                        if (!getIDFromStack(stack).isValidArmour(player)) {
                            if (IronManUtil.isIronManSuit(stack)) {
                                bootsOnlyFlight(player, (IronManIdentifier) getIDFromStack(stack));
                            }
                        }
                    }
                }
            })
    );
    public static final RegistryObject<AbstractCapability> IRON_MAN_WEAPONS = CAPS.register("iron_man_weapons",() -> new CapabilityBuilder("iron_man_weapons")
            .runner(new AbstractCapability.abilityRunner() {
                @Override
                public void runAbility(int num, Player player) {
                    if (num == 2) {
                        // Unibeam

                        int i = Mth.clamp(0, 0, 64);
                        float f2 = Mth.cos(player.yBodyRot * ((float) Math.PI / 180F)) * (0F + 1.21F * (float) i);
                        float f3 = Mth.sin(player.yBodyRot * ((float) Math.PI / 180F)) * (0F + 1.21F * (float) i);
                        float f6 = (0.3F * 0.45F) * ((float) i * 0.2F + 0.0F);
//                UnibeamEntity unibeam = new UnibeamEntity(SuperheroEntities.UNIBEAM_ENTITY.get(), player.level());
//                unibeam.moveTo(player.getX() + (double) f2, player.getY() + (double) f6, player.getZ() + (double) f3, player.getYRot(), player.getXRot());
//                player.level().addFreshEntity(unibeam);

                        // Rocket
                        Vec3 look = player.getLookAngle().normalize();
                        RocketEntity rocket = new RocketEntity(player.level(),player,look.x,look.y,look.z, 5);
                        rocket.moveTo(player.getX() + (double) f2, player.getY() + (double) f6, player.getZ() + (double) f3,player.getXRot(),player.getYRot());
                        player.level().addFreshEntity(rocket);
                    }
                }
            })
    );

    // Spiderman
    public static final RegistryObject<AbstractCapability> WEB_SHOOTING = CAPS.register("web_shooting",() -> new CapabilityBuilder("web_shooting")
            .runner(new AbstractCapability.abilityRunner() {
                @Override
                public void runAbility(int num, Player player) {
                    if (num == 1) {
                        shootWebAndSwingToIt(player);
                    }
                    if (num == 2) {
                        shootWebAndZipToIt(player);
                    }
                }
            })
    );
    public static final RegistryObject<AbstractCapability> SPIDERMAN_HUD = CAPS.register("spiderman_hud",() -> new CapabilityBuilder("spiderman_hud"));
    public static final RegistryObject<AbstractCapability> WALL_CLIMBING = CAPS.register("wall_climbing",() -> new CapabilityBuilder("wall_climbing")
            .runner(new AbstractCapability.abilityRunner() {
                @Override
                public void tick(ItemStack stack, Level level, Player player) {
                    if (getIDFromStack(stack).isValidArmour(player)) {
                        runWallClimbs(player);
                    }
                }
            })
    );



    public static Supplier<IForgeRegistry<AbstractCapability>> CAPS_REGISTRY = CAPS.makeRegistry(() -> new RegistryBuilder<AbstractCapability>().setMaxID(Integer.MAX_VALUE - 1));
}
