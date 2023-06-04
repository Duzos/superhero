package com.duzo.superhero;

import com.duzo.superhero.data.client.ModelProviderItem;
import com.duzo.superhero.entities.SuperheroEntities;
import com.duzo.superhero.items.SuperheroItems;
import com.duzo.superhero.network.Network;
import com.mojang.logging.LogUtils;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Superhero.MODID)
public class Superhero {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "superhero";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public Superhero() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        SuperheroEntities.ENTITIES.register(modEventBus);
        SuperheroItems.ITEMS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        Network.register();
    }

    private void addCreative(CreativeModeTabEvent.BuildContents event)
    {

    }
}
