package com.duzo.superhero.events;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.client.gui.JarvisGUIOverlay;
import com.duzo.superhero.client.models.entities.HumanoidEntityModel;
import com.duzo.superhero.client.models.entities.IronManEntityModel;
import com.duzo.superhero.client.models.items.IronManArmourModel;
import com.duzo.superhero.client.renderers.IronManEntityRenderer;
import com.duzo.superhero.entities.SuperheroEntities;
import com.duzo.superhero.items.IronManArmourItem;
import com.duzo.superhero.network.Network;
import com.duzo.superhero.network.packets.TakeOffIronManSuitC2SPacket;
import com.duzo.superhero.util.KeyBinds;
import com.mojang.blaze3d.vertex.PoseStack;
import cpw.mods.jarhandling.impl.Jar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Superhero.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FakePlayersClientEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers renderers) {
        renderers.registerEntityRenderer(SuperheroEntities.IRON_MAN_ENTITY.get(), IronManEntityRenderer::new);
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
            if (KeyBinds.TAKE_OFF_IRON_MAN_SUIT.consumeClick()) {
                Network.sendToServer(new TakeOffIronManSuitC2SPacket());
            }
        }
    }
    @Mod.EventBusSubscriber(modid = Superhero.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinds.TAKE_OFF_IRON_MAN_SUIT);
        }
        @SubscribeEvent
        public static void renderOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("jarvis_hud", JarvisGUIOverlay.HUD_JARVIS);
        }

    }
}

