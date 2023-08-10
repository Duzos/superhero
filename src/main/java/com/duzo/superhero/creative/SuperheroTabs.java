package com.duzo.superhero.creative;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.blocks.IronManSuitCaseBlock;
import com.duzo.superhero.blocks.SuperheroBlocks;
import com.duzo.superhero.capabilities.SuperheroCapability;
import com.duzo.superhero.items.SuperheroArmourItem;
import com.duzo.superhero.items.SuperheroItems;
import com.duzo.superhero.items.SuperheroNanotechItem;
import com.duzo.superhero.items.batman.GrapplingHookWeaponItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SuperheroTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Superhero.MODID);

    public static final RegistryObject<CreativeModeTab> SH_TAB = TABS.register("sh_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("item_group." + Superhero.MODID + ".superhero"))
            .icon(() -> new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Superhero.MODID,"iron_spider_helmet"))))
            .displayItems((parms, output) -> {
                for (RegistryObject<Block> block : SuperheroBlocks.BLOCKS.getEntries()) {
                    if (block.get() instanceof IronManSuitCaseBlock) continue;

                    output.accept(block.get().asItem());
                }
                for (RegistryObject<Item> item : SuperheroItems.ITEMS.getEntries()) {
                    if (item.get() instanceof SuperheroNanotechItem || item.get() instanceof GrapplingHookWeaponItem) continue;
                    if (item.get() instanceof SuperheroArmourItem hero) {
                        if (!hero.getIdentifier().getCapabilities().has(SuperheroCapability.WEB_SHOOTING)) continue;
                        if (hero.getIdentifier().name() == "iron_spider") continue;
                    }
                    if (item.get() instanceof BlockItem block) {
                        if (block.getBlock() instanceof IronManSuitCaseBlock) continue;
                    }

                    output.accept(item.get());
                }
            }
        ).build()
    );
}
