package com.duzo.superhero.util.ironman;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IronManCapabilities implements Iterable<IronManCapability> {
    private List<IronManCapability> capabilities = new ArrayList<>();
    public IronManCapabilities add(IronManCapability capability) {
        this.capabilities.add(capability);
        return this;
    }
    public IronManCapabilities add(IronManCapability... capabilities) {
        for (IronManCapability capability : capabilities) {
            this.add(capability);
        }
        return this;
    }
    public boolean has(IronManCapability capability) {
        return this.capabilities.contains(capability);
    }

    @NotNull
    @Override
    public Iterator<IronManCapability> iterator() {
        return this.capabilities.iterator();
    }
}
