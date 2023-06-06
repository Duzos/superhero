package com.duzo.superhero.events;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.entities.HumanoidEntity;
import com.duzo.superhero.entities.IronManEntity;
import com.duzo.superhero.entities.SuperheroEntities;
import com.duzo.superhero.items.IronManArmourItem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Superhero.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FakePlayersModEvents {
    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {});
    }

    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(SuperheroEntities.IRON_MAN_ENTITY.get(), HumanoidEntity.getHumanoidAttributes().build());
    }
}