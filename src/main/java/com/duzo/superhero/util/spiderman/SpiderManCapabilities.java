package com.duzo.superhero.util.spiderman;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Deprecated
/**
 * Replace with {@link com.duzo.superhero.util.SuperheroCapabilities}
 */
public class SpiderManCapabilities implements Iterable<SpiderManCapability> {
    private List<SpiderManCapability> capabilities = new ArrayList<>();
    public SpiderManCapabilities add(SpiderManCapability capability) {
        this.capabilities.add(capability);
        return this;
    }
    public SpiderManCapabilities add(SpiderManCapability... capabilities) {
        for (SpiderManCapability capability : capabilities) {
            this.add(capability);
        }
        return this;
    }
    public boolean has(SpiderManCapability capability) {
        return this.capabilities.contains(capability);
    }

    @NotNull
    @Override
    public Iterator<SpiderManCapability> iterator() {
        return this.capabilities.iterator();
    }
}
