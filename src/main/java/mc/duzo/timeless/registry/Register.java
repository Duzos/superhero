package mc.duzo.timeless.registry;

import java.util.function.Supplier;

import mc.duzo.animation.api.AnimationEvents;
import mc.duzo.animation.player.holder.PlayerAnimationHolder;
import mc.duzo.animation.registry.AnimationRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import mc.duzo.timeless.Timeless;
import mc.duzo.timeless.power.PowerRegistry;
import mc.duzo.timeless.suit.Suit;
import mc.duzo.timeless.suit.SuitRegistry;
import mc.duzo.timeless.suit.client.animation.SuitAnimationTracker;
import mc.duzo.timeless.suit.client.animation.impl.ironman.mk5.MarkFiveAnimations;
import mc.duzo.timeless.suit.client.animation.impl.ironman.mk5.MarkFiveCaseAnimation;
import mc.duzo.timeless.suit.client.animation.impl.ironman.mk5.MarkFiveMaskAnimation;
import mc.duzo.timeless.suit.ironman.mk5.MarkFiveCase;
import mc.duzo.timeless.suit.set.SetRegistry;

public class Register {
    public static class Items {
        public static MarkFiveCase MARK_FIVE_CASE = register("mark_five_case", new MarkFiveCase());

        public static <T extends Item> T register(Identifier id, T entry) {
            return Registry.register(Registries.ITEM, id, entry);
        }
        private static <T extends Item> T register(String name, T entry) {
            return register(new Identifier(Timeless.MOD_ID, name), entry);
        }
    }

    public static class ItemGroups {
        public static final RegistryKey<ItemGroup> KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(Timeless.MOD_ID, "item_group"));
        public static final ItemGroup GROUP = FabricItemGroup.builder()
                .icon(() -> new ItemStack(Items.MARK_FIVE_CASE))
                .displayName(Text.translatable("itemGroup." + Timeless.MOD_ID))
                .build();

        public static void init() {
            Registry.register(Registries.ITEM_GROUP, KEY, GROUP);

            ItemGroupEvents.modifyEntriesEvent(KEY).register(group -> {
                group.add(Items.MARK_FIVE_CASE);
            });
        }
    }

    public static class Sounds {
        public static final SoundEvent THRUSTER = register("thruster");
        public static final SoundEvent MARK5_NOISES = register("mark5_noises");
        public static final SoundEvent IRONMAN_STEP = register("ironman_step");
        public static final SoundEvent IRONMAN_MASK = register("ironman_mask");

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
    public static class Animations {

        public static class Players {
            public static final Supplier<PlayerAnimationHolder> MARK_FIVE_CASE_OPEN = AnimationRegistry.instance().register(() -> new PlayerAnimationHolder(new Identifier(Timeless.MOD_ID, "ironman_mk5_case_open_player"), MarkFiveAnimations.CASE_OPEN_PLAYER));
            public static final Supplier<PlayerAnimationHolder> MARK_FIVE_CASE_CLOSE = AnimationRegistry.instance().register(() -> new PlayerAnimationHolder(new Identifier(Timeless.MOD_ID, "ironman_mk5_case_close_player"), MarkFiveAnimations.CASE_CLOSE_PLAYER));

            public static void init() {}

        }
        public static class Suits {
            public static class MarkFive {
                public static final Supplier<MarkFiveCaseAnimation> CASE_OPEN = AnimationRegistry.instance().register(() -> new MarkFiveCaseAnimation(true));
                public static final Supplier<MarkFiveCaseAnimation> CASE_CLOSE = AnimationRegistry.instance().register(() -> new MarkFiveCaseAnimation(false));
                public static final Supplier<MarkFiveMaskAnimation> MASK_OPEN = AnimationRegistry.instance().register(() -> new MarkFiveMaskAnimation(true));
                public static final Supplier<MarkFiveMaskAnimation> MASK_CLOSE = AnimationRegistry.instance().register(() -> new MarkFiveMaskAnimation(false));

                public static void init() {

                }
            }

            public static void init() {
                MarkFive.init();

                AnimationEvents.FIND_ANIMATION_INFO.register(player -> {
                    Suit suit = Suit.findSuit(player).orElse(null);
                    if (suit == null) return AnimationEvents.Result.pass();

                    return new AnimationEvents.Result<>(suit.toClient().getAnimationInfo(player));
                });
            }
        }

        public static void init() {
            Players.init();
            Suits.init();
        }
    }

    public static void init() {
        PowerRegistry.init();
        SetRegistry.init();
        SuitRegistry.init();
        ItemGroups.init();
        Sounds.init();
        Trackers.init();
        Animations.init();
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
