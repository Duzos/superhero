package mc.duzo.timeless.power;

import java.util.function.Consumer;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import mc.duzo.timeless.datagen.provider.lang.Translatable;
import mc.duzo.timeless.registry.Identifiable;

public abstract class Power implements Identifiable, Translatable {
    public abstract boolean run(ServerPlayerEntity player);

    @Override
    public String getTranslationKey() {
        return this.id().getNamespace() + ".power." + this.id().getPath();
    }

    public Power register() {
        return PowerRegistry.register(this);
    }

    public static Power create(Identifier id, Consumer<ServerPlayerEntity> action) {
        return new Power() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public boolean run(ServerPlayerEntity player) {
                action.accept(player);
                return true;
            }
        };
    }
}
