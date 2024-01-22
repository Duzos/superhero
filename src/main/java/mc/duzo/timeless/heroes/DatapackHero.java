package mc.duzo.timeless.heroes;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mc.duzo.timeless.TimelessHeroes;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicReference;

public class DatapackHero extends HeroSchema {
    public static final Codec<HeroSchema> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Identifier.CODEC.fieldOf("id").forGetter(HeroSchema::id)
            ).apply(instance, DatapackHero::new)
    );

    protected DatapackHero(Identifier id) {
        super(id);
    }

    public static HeroSchema fromInputStream(InputStream stream) {
        return fromJson(JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject());
    }
    public static HeroSchema fromJson(JsonObject json) {
        AtomicReference<HeroSchema> created = new AtomicReference<>();

        CODEC.decode(JsonOps.INSTANCE, json)
                .get()
                .ifLeft(desktop -> { created.set(desktop.getFirst()); })
                .ifRight(err -> { created.set(null);
                    TimelessHeroes.LOGGER.error("Error decoding datapack hero: " + err);
                });

        return created.get();
    }
}
