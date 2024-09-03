package mc.duzo.timeless.client;

import java.util.function.Supplier;

import mc.duzo.animation.api.AnimationEvents;
import mc.duzo.animation.generic.AnimationInfo;
import mc.duzo.animation.player.holder.PlayerAnimationHolder;
import mc.duzo.animation.registry.AnimationRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

import net.minecraft.util.Identifier;

import mc.duzo.timeless.Timeless;
import mc.duzo.timeless.client.gui.JarvisGui;
import mc.duzo.timeless.client.keybind.TimelessKeybinds;
import mc.duzo.timeless.client.network.ClientNetwork;
import mc.duzo.timeless.suit.Suit;
import mc.duzo.timeless.suit.client.ClientSuitRegistry;
import mc.duzo.timeless.suit.client.animation.SuitAnimationHolder;
import mc.duzo.timeless.suit.client.animation.impl.ironman.generic.GenericIronManAnimations;
import mc.duzo.timeless.suit.client.animation.impl.ironman.mk5.MarkFiveAnimations;
import mc.duzo.timeless.suit.client.animation.impl.ironman.mk5.MarkFiveCaseAnimation;
import mc.duzo.timeless.suit.client.animation.impl.ironman.mk5.MarkFiveMaskAnimation;

public class TimelessClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Animations.init();
        ClientNetwork.init();
        ClientSuitRegistry.init();
        TimelessKeybinds.init();

        HudRenderCallback.EVENT.register((stack, delta) -> {
            JarvisGui.render(stack, delta);
        });
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
            public static class IronMan {
                public static final Supplier<MarkFiveMaskAnimation> MASK_OPEN = AnimationRegistry.instance().register(() -> new SuitAnimationHolder(new Identifier(Timeless.MOD_ID, "ironman_generic_mask_open"), GenericIronManAnimations.MASK_OPEN, new AnimationInfo(AnimationInfo.RenderType.TORSO_HEAD, null, AnimationInfo.Movement.ALLOW, AnimationInfo.Transform.TARGETED), false));
                public static final Supplier<MarkFiveMaskAnimation> MASK_CLOSE = AnimationRegistry.instance().register(() -> new SuitAnimationHolder(new Identifier(Timeless.MOD_ID, "ironman_generic_mask_close"), GenericIronManAnimations.MASK_CLOSE, new AnimationInfo(AnimationInfo.RenderType.TORSO_HEAD, null, AnimationInfo.Movement.ALLOW, AnimationInfo.Transform.TARGETED), false));

                public static void init() {

                }
            }

            public static void init() {
                IronMan.init();
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
}
