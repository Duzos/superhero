package com.duzo.superhero;

import com.duzo.superhero.blocks.SuperheroBlocks;
import com.duzo.superhero.data.client.ModelProviderItem;
import com.duzo.superhero.entities.SuperheroEntities;
import com.duzo.superhero.items.IronManArmourItem;
import com.duzo.superhero.items.SuperheroItems;
import com.duzo.superhero.network.Network;
import com.duzo.superhero.sounds.SuperheroSounds;
import com.mojang.logging.LogUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.CreativeModeTabRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
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
        SuperheroItems.init();
        SuperheroItems.ITEMS.register(modEventBus);
        SuperheroBlocks.BLOCKS.register(modEventBus);
        SuperheroSounds.SOUNDS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::registerCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        Network.register();
    }

    private void addCreative(CreativeModeTabEvent.BuildContents event)
    {

    }

    @SubscribeEvent
    public void onFall(LivingFallEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        if (player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof IronManArmourItem) {
            event.setDistance(0);
        }
    }

    private void registerCreative(CreativeModeTabEvent.Register event) {
        event.registerCreativeModeTab(new ResourceLocation(MODID,"superhero"), builder ->
                builder.title(Component.translatable("item_group." + MODID + ".superhero"))
                        .icon(() -> new ItemStack(Items.IRON_INGOT))
                        .displayItems(((parms, output) -> {
                            for (RegistryObject<Item> item : SuperheroItems.ITEMS.getEntries()) {
                                output.accept(item.get());
                            }
                            for (RegistryObject<Block> block : SuperheroBlocks.BLOCKS.getEntries()) {
                                output.accept(block.get().asItem());
                            }
        })));
    }
}
