package com.duzo.superhero;

import com.duzo.superhero.blocks.SuperheroBlocks;
import com.duzo.superhero.capabilities.SuperheroCapability;
import com.duzo.superhero.entities.SuperheroEntities;
import com.duzo.superhero.events.FlyingEventHandler;
import com.duzo.superhero.ids.AbstractIdentifier;
import com.duzo.superhero.ids.SuperheroIdentifierRegistry;
import com.duzo.superhero.items.SuperheroItems;
import com.duzo.superhero.network.Network;
import com.duzo.superhero.particles.SuperheroParticles;
import com.duzo.superhero.sounds.SuperheroSounds;
import com.duzo.superhero.util.ironman.IronManUtil;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import static com.duzo.superhero.util.SuperheroUtil.getIDFromStack;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Superhero.MODID)
public class Superhero {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "superhero";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public Superhero() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        SuperheroIdentifierRegistry.IDS.register(modEventBus);

        SuperheroEntities.ENTITIES.register(modEventBus);
        SuperheroItems.ITEMS.register(modEventBus);
        SuperheroItems.init(); // @TODO crashes game, dk why
        SuperheroBlocks.BLOCKS.register(modEventBus);
        SuperheroSounds.SOUNDS.register(modEventBus);
        SuperheroParticles.PARTICLE_TYPES.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(new FlyingEventHandler());

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

        // @TODO this better? as a capability but cba
        if (IronManUtil.isIronManSuit(player.getItemBySlot(EquipmentSlot.LEGS))) {
            event.setDistance(0);
        }
        AbstractIdentifier id = getIDFromStack(player.getItemBySlot(EquipmentSlot.FEET));
        if (id != null) {
            if (id.getCapabilities().has(SuperheroCapability.FAST_MOBILITY)){
                event.setDamageMultiplier(event.getDamageMultiplier() * 0.5f);
            }
        }
    }

    private void registerCreative(CreativeModeTabEvent.Register event) {
//        event.registerCreativeModeTab(new ResourceLocation(MODID,"superhero"), builder ->
//                builder.title(Component.translatable("item_group." + MODID + ".superhero"))
//                        .icon(() -> new ItemStack(SuperheroItems.NANOTECH.get()))
//                        .displayItems(((parms, output) -> {
//                            for (RegistryObject<Block> block : SuperheroBlocks.BLOCKS.getEntries()) {
//                                output.accept(block.get().asItem());
//                            }
//                            for (RegistryObject<Item> item : SuperheroItems.ITEMS.getEntries()) {
//                                if (item.get() instanceof SuperheroNanotechItem) continue;
//
//                                output.accept(item.get());
//                            }
//        })));
    }

    public static ResourceLocation id(String location) {
        return new ResourceLocation(MODID, location);
    }
}
