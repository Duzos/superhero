package mc.duzo.timeless.power;

import mc.duzo.timeless.datagen.provider.lang.Translatable;
import mc.duzo.timeless.registry.Identifiable;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public abstract class Power implements Identifiable, Translatable {
    public abstract boolean run(ServerPlayerEntity player);
    public abstract void tick(ServerPlayerEntity player);

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

            @Override
            public void tick(ServerPlayerEntity player) {

            }
        };
    }

    public static class Builder {
        private final Identifier id;
        private Consumer<ServerPlayerEntity> run = player -> {};
        private Consumer<ServerPlayerEntity> tick = player -> {};

        private Builder(Identifier id) {
            this.id = id;
        }
        public static Builder create(Identifier id) {
            return new Builder(id);
        }

        public Builder run(Consumer<ServerPlayerEntity> run) {
            this.run = run;
            return this;
        }

        public Builder tick(Consumer<ServerPlayerEntity> tick) {
            this.tick = tick;
            return this;
        }

        public Power build() {
            return new Power() {
                @Override
                public Identifier id() {
                    return id;
                }

                @Override
                public boolean run(ServerPlayerEntity player) {
                    run.accept(player);
                    return true;
                }

                @Override
                public void tick(ServerPlayerEntity player) {
                    tick.accept(player);
                }
            };
        }
    }
}
