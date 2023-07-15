package com.duzo.superhero.data;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.data.SuperheroData;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = Superhero.MODID)
public class SuperheroDataImpl implements ICapabilitySerializable<CompoundTag> {

    public static Capability<SuperheroData> PLAYER_DATA = CapabilityManager.get(new CapabilityToken<>() {
    });
    public final SuperheroData capability;
    public final LazyOptional<SuperheroData> lazyOptional;

    public SuperheroDataImpl(Player player) {
        this.capability = new SuperheroData(player);
        this.lazyOptional = LazyOptional.of(() -> this.capability);
    }

    @SubscribeEvent
    public static void init(RegisterCapabilitiesEvent e) {
        e.register(Superhero.class);
    }

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> e) {
        if (e.getObject() instanceof Player player) {
            e.addCapability(Superhero.id("player_data"), new SuperheroDataImpl(player));
        }
    }

    public static Optional<SuperheroData> get(LivingEntity player) {
        return player.getCapability(PLAYER_DATA).resolve();
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction arg) {
        return capability == PLAYER_DATA ? this.lazyOptional.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.capability.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag arg) {
        this.capability.deserializeNBT(arg);
    }
}