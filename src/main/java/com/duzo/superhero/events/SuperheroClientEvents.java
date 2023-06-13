package com.duzo.superhero.events;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.client.gui.JarvisGUIOverlay;
import com.duzo.superhero.client.models.entities.HumanoidEntityModel;
import com.duzo.superhero.client.models.entities.IronManEntityModel;
import com.duzo.superhero.client.models.items.IronManArmourModel;
import com.duzo.superhero.client.renderers.IronManEntityRenderer;
import com.duzo.superhero.client.renderers.UnibeamRenderer;
import com.duzo.superhero.entities.SuperheroEntities;
import com.duzo.superhero.entities.UnibeamEntity;
import com.duzo.superhero.items.ironman.IronManArmourItem;
import com.duzo.superhero.network.Network;
import com.duzo.superhero.network.packets.AbilityC2SPacket;
import com.duzo.superhero.util.IronManCapability;
import com.duzo.superhero.util.KeyBinds;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Superhero.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SuperheroClientEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers renderers) {
        renderers.registerEntityRenderer(SuperheroEntities.IRON_MAN_ENTITY.get(), IronManEntityRenderer::new);
        renderers.registerEntityRenderer(SuperheroEntities.UNIBEAM_ENTITY.get(), UnibeamRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HumanoidEntityModel.LAYER_LOCATION,() -> LayerDefinition.create(PlayerModel.createMesh(CubeDeformation.NONE,false),64,64));
        event.registerLayerDefinition(IronManEntityModel.LAYER_LOCATION,() -> LayerDefinition.create(PlayerModel.createMesh(CubeDeformation.NONE,false),64,64));
        event.registerLayerDefinition(IronManArmourModel.LAYER_LOCATION,IronManArmourModel::createBodyLayer);
    }

    @Mod.EventBusSubscriber(modid = Superhero.MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (KeyBinds.ABILITY_ONE.consumeClick()) {
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

            if (!(chest.getItem() instanceof IronManArmourItem hero)) return;

            if (!event.getPlayer().isOnGround() && Screen.hasControlDown() && hero.isValidArmor(event.getPlayer()) && hero.getMark().getCapabilities().has(IronManCapability.BLAST_OFF)) {
                event.setNewFovModifier(event.getFovModifier() * 1.25f);
//                event.setNewFovModifier((float) (event.getFovModifier() * hero.getMark().getBlastOffSpeed()));
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
        }
    }
}

