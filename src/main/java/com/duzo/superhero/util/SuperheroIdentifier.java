package com.duzo.superhero.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.ListCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.crafting.Recipe;

public class SuperheroIdentifier {
    public static final Codec<SuperheroIdentifier> CODEC = RecordCodecBuilder.create(
            instance -> {
                return instance.group(
                        Codec.STRING.fieldOf("name").forGetter(SuperheroIdentifier::getName),
                        SuperheroCapabilities.CODEC.fieldOf("capabilities").forGetter(SuperheroIdentifier::getCapabilities)
                ).apply(instance,SuperheroIdentifier::new);
            }
    );

    private final String name;
    private final SuperheroCapabilities capabilities;


    public SuperheroIdentifier(String name, SuperheroCapabilities capabilities) {
        this.name = name;
        this.capabilities = capabilities;
    }

    public String getName() {
        return this.name;
    }

    public SuperheroCapabilities getCapabilities() {
        return this.capabilities;
    }
}
