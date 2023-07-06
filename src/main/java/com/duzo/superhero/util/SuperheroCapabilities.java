package com.duzo.superhero.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.ListCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SuperheroCapabilities implements Iterable<SuperheroCapability> {
    public static final Codec<SuperheroCapabilities> CODEC = RecordCodecBuilder.create(
            instance -> {
                return instance.group(
                        ListCodec.STRING.fieldOf("capabilities").forGetter()
                ).apply(instance,SuperheroCapabilities::new);
            }
    );

    private List<SuperheroCapability> capabilities = new ArrayList<>();

    public SuperheroCapabilities(SuperheroCapability... capabilities) {
        this.add(capabilities);
    }

    public SuperheroCapabilities add(SuperheroCapability capability) {
        this.capabilities.add(capability);
        return this;
    }
    public SuperheroCapabilities add(SuperheroCapability... capabilities) {
        for (SuperheroCapability capability : capabilities) {
            this.add(capability);
        }
        return this;
    }
    public boolean has(SuperheroCapability capability) {
        return this.capabilities.contains(capability);
    }

    public boolean has(SuperheroCapability... capabilities) {
        boolean flag = true;

        for (SuperheroCapability capability : capabilities) {
            flag = flag && this.has(capability);
        }

        return flag;
    }

    public boolean has(List<SuperheroCapability> capabilities) {
        boolean flag = true;

        for (SuperheroCapability capability : capabilities) {
            flag = flag && this.has(capability);
        }

        return flag;
    }

    @NotNull
    @Override
    public Iterator<SuperheroCapability> iterator() {
        return this.capabilities.iterator();
    }
}
