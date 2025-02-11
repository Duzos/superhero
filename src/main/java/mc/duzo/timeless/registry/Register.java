package mc.duzo.timeless.registry;

import dev.pavatus.lib.block.ABlockSettings;
import dev.pavatus.lib.container.RegistryContainer;
import dev.pavatus.lib.container.impl.BlockContainer;
import dev.pavatus.lib.container.impl.ItemContainer;
import dev.pavatus.lib.container.impl.ItemGroupContainer;
import dev.pavatus.lib.container.impl.SoundContainer;
import dev.pavatus.lib.item.AItemSettings;
import dev.pavatus.lib.itemgroup.AItemGroup;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import mc.duzo.timeless.Timeless;
import mc.duzo.timeless.block.SuitApplicationBlock;
import mc.duzo.timeless.block.entity.SuitApplicationBlockEntity;
import mc.duzo.timeless.commands.command.TimelessCommands;
import mc.duzo.timeless.power.PowerRegistry;
import mc.duzo.timeless.suit.SuitRegistry;
import mc.duzo.timeless.suit.client.animation.SuitAnimationTracker;
import mc.duzo.timeless.suit.ironman.IronManEntity;
import mc.duzo.timeless.suit.ironman.mk5.MarkFiveCase;
import mc.duzo.timeless.suit.set.SetRegistry;

public class Register {
    public static class Items extends ItemContainer {
        public static MarkFiveCase MARK_FIVE_CASE = new MarkFiveCase(new AItemSettings().group(ItemGroups.GROUP));
    }

    public static class ItemGroups implements ItemGroupContainer {
        public static final AItemGroup GROUP = AItemGroup.builder(new Identifier(Timeless.MOD_ID, "group"))
                .icon(() -> new ItemStack(Items.MARK_FIVE_CASE))
                .displayName(Text.translatable("itemGroup." + Timeless.MOD_ID))
                .build();
    }

    public static class Sounds implements SoundContainer {
        public static final SoundEvent THRUSTER = SoundEvent.of(new Identifier(Timeless.MOD_ID, "thruster"));
        public static final SoundEvent MARK5_NOISES = SoundEvent.of(new Identifier(Timeless.MOD_ID, "mark5_noises"));
        public static final SoundEvent IRONMAN_STEP = SoundEvent.of(new Identifier(Timeless.MOD_ID, "ironman_step"));
        public static final SoundEvent IRONMAN_MASK = SoundEvent.of(new Identifier(Timeless.MOD_ID, "ironman_mask"));
        public static final SoundEvent IRONMAN_POWERUP = SoundEvent.of(new Identifier(Timeless.MOD_ID, "ironman_powerup"));
        public static final SoundEvent IRONMAN_POWERDOWN = SoundEvent.of(new Identifier(Timeless.MOD_ID, "ironman_powerdown"));

        private static SoundEvent register(String name) {
            return register(new Identifier(Timeless.MOD_ID, name));
        }
        private static SoundEvent register(Identifier id) {
            return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
        }

        public static void init() {

        }
    }

    public static class Trackers {
        public static final SuitAnimationTracker SUIT = (SuitAnimationTracker) new SuitAnimationTracker().register();

        public static void init() {

        }
    }

    public static class Entities {
        public static final EntityType<IronManEntity> IRON_MAN = register(Registries.ENTITY_TYPE, "iron_man", EntityType.Builder.<IronManEntity>create(IronManEntity::new, SpawnGroup.MISC)
                .setDimensions(0.6f, 1.8f)
                .build("iron_man"));

        public static void init() {
            registerAttributes(IRON_MAN, MobEntity.createLivingAttributes().build());
        }

        private static void registerAttributes(EntityType<? extends LivingEntity> type, DefaultAttributeContainer attributes) {
            FabricDefaultAttributeRegistry.register(type, attributes);
        }
    }

    public static class Blocks extends BlockContainer {
        public static final Block SUIT_APPLICATION = new SuitApplicationBlock(ABlockSettings.create().itemSettings(new AItemSettings().group(ItemGroups.GROUP)));
    }
    public static class BlockEntities {
        public static final BlockEntityType<SuitApplicationBlockEntity> SUIT_APPLICATION_BE = register(Registries.BLOCK_ENTITY_TYPE, "suit_application", BlockEntityType.Builder.create(SuitApplicationBlockEntity::new, Blocks.SUIT_APPLICATION).build(null));

        public static void init() {

        }
    }

    public static void init() {
        RegistryContainer.register(ItemGroups.class, Timeless.MOD_ID);
        RegistryContainer.register(Items.class, Timeless.MOD_ID);
        RegistryContainer.register(Blocks.class, Timeless.MOD_ID);

        PowerRegistry.init();
        SetRegistry.init();
        SuitRegistry.init();
        Sounds.init();
        Trackers.init();
        Entities.init();
        BlockEntities.init();
        TimelessCommands.init();
    }

    public static <V, T extends V> T register(Registry<V> registry, String name, T entry) {
        return Registry.register(registry, new Identifier(Timeless.MOD_ID, name), entry);
    }

    public static <T extends Block> T registerBlockAndItem(String name, T entry) {
        T output = Register.register(Registries.BLOCK, name, entry);
        Registry.register(Registries.ITEM, new Identifier(Timeless.MOD_ID, name), new BlockItem(output, new FabricItemSettings()));
        return output;
    }
}
