package com.duzo.superhero.events;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.capabilities.SuperheroCapabilityRegistry;
import com.duzo.superhero.client.gui.FlashGUIOverlay;
import com.duzo.superhero.client.gui.JarvisGUIOverlay;
import com.duzo.superhero.client.gui.SpiderManGUIOverlay;
import com.duzo.superhero.client.models.SuperheroModels;
import com.duzo.superhero.client.renderers.*;
import com.duzo.superhero.client.gui.screen.SuitMakerScreen;
import com.duzo.superhero.client.gui.menu.SuperheroMenuTypes;
import com.duzo.superhero.entities.SuperheroEntities;
import com.duzo.superhero.items.SuperheroArmourItem;
import com.duzo.superhero.network.Network;
import com.duzo.superhero.network.packets.AbilityC2SPacket;
import com.duzo.superhero.util.KeyBinds;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Superhero.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SuperheroClientEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(SuperheroMenuTypes.SUIT_MAKER_MENU.get(), SuitMakerScreen::new);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers renderers) {
        renderers.registerEntityRenderer(SuperheroEntities.IRON_MAN_ENTITY.get(), IronManEntityRenderer::new);
        renderers.registerEntityRenderer(SuperheroEntities.UNIBEAM_ENTITY.get(), UnibeamRenderer::new);
        renderers.registerEntityRenderer(SuperheroEntities.ROCKET_ENTITY.get(), RocketRenderer::new);
        renderers.registerEntityRenderer(SuperheroEntities.WEB_ROPE_ENTITY.get(), WebRopeEntityRenderer::new);
        renderers.registerEntityRenderer(SuperheroEntities.GRAPPLE_ROPE_ENTITY.get(), GrapplingHookRopeEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        SuperheroModels.addModels(event);
    }

    @Mod.EventBusSubscriber(modid = Superhero.MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (KeyBinds.ABILITY_ONE.consumeClick()) {
                System.out.println("is this continuously looping?");
                Network.sendToServer(new AbilityC2SPacket(1));
            }
            if (KeyBinds.ABILITY_TWO.consumeClick()) {
                Network.sendToServer(new AbilityC2SPacket(2));
            }
            if (KeyBinds.ABILITY_THREE.consumeClick()) {
                Network.sendToServer(new AbilityC2SPacket(3));
            }
            if (KeyBinds.ABILITY_FOUR.consumeClick()) {
                Network.sendToServer(new AbilityC2SPacket(4));
            }
        }

        @SubscribeEvent
        public static void changeFOV(ComputeFovModifierEvent event) {
            ItemStack chest = event.getPlayer().getItemBySlot(EquipmentSlot.CHEST);

            if (!(chest.getItem() instanceof SuperheroArmourItem hero)) return;

            if (!event.getPlayer().onGround() && Screen.hasControlDown() && hero.getIdentifier().isValidArmour(event.getPlayer()) && hero.getIdentifier().getCapabilities().has(SuperheroCapabilityRegistry.BLAST_OFF)) {
                event.setNewFovModifier(event.getFovModifier() * 1.25f);
//                event.setNewFovModifier((float) (event.getFovModifier() * hero.getMark().getBlastOffSpeed()));
            }
        }

        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if(KeyBinds.ABILITY_THREE.isDown()) {
                //event.get
            }
        }
    }
    @Mod.EventBusSubscriber(modid = Superhero.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinds.ABILITY_ONE);
            event.register(KeyBinds.ABILITY_TWO);
            event.register(KeyBinds.ABILITY_THREE);
        }
        @SubscribeEvent
        public static void renderOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("jarvis_hud", JarvisGUIOverlay.HUD_JARVIS);
            event.registerAboveAll("flash_hud", FlashGUIOverlay.HUD_FLASH);
            event.registerAboveAll("spiderman_hud", SpiderManGUIOverlay.HUD_SPIDERMAN);
        }
    }
}

