package com.duzo.superhero.capabilities;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public class SuperheroCapabilities implements Iterable<Supplier<AbstractCapability>> {
    private List<Supplier<AbstractCapability>> capabilities = new ArrayList<>();

    @SafeVarargs
    public SuperheroCapabilities(Supplier<AbstractCapability>... capabilities) {
        this.add(capabilities);
    }

    public SuperheroCapabilities add(Supplier<AbstractCapability> capability) {
        this.capabilities.add(capability);
        return this;
    }
    public SuperheroCapabilities add(Supplier<AbstractCapability>... capabilities) {
        for (Supplier<AbstractCapability> capability : capabilities) {
            this.add(capability);
        }
        return this;
    }
    public SuperheroCapabilities add(SuperheroCapabilities caps) {
        for (Supplier<AbstractCapability> cap : caps) {
            this.add(cap);
        }
        return this;
    }
    public boolean has(Supplier<AbstractCapability> capability) {
        return this.capabilities.contains(capability);
    }

    public boolean has(Supplier<AbstractCapability>... capabilities) {
        boolean flag = true;

        for (Supplier<AbstractCapability> capability : capabilities) {
            flag = flag && this.has(capability);
        }

        return flag;
    }

    public boolean has(List<Supplier<AbstractCapability>> capabilities) {
        boolean flag = true;

        for (Supplier<AbstractCapability> capability : capabilities) {
            flag = flag && this.has(capability);
        }

        return flag;
    }

    @NotNull
    @Override
    public Iterator<Supplier<AbstractCapability>> iterator() {
        return this.capabilities.iterator();
    }
}
