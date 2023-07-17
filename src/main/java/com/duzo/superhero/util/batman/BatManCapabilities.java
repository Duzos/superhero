package com.duzo.superhero.util.batman;

import com.duzo.superhero.capabilities.SuperheroCapabilities;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Deprecated
/**
 * Replace with {@link SuperheroCapabilities}
 */
public class BatManCapabilities implements Iterable<BatManCapability> {
    private List<BatManCapability> capabilities = new ArrayList<>();
    public BatManCapabilities add(BatManCapability capability) {
        this.capabilities.add(capability);
        return this;
    }
    public BatManCapabilities add(BatManCapability... capabilities) {
        for (BatManCapability capability : capabilities) {
            this.add(capability);
        }
        return this;
    }
    public boolean has(BatManCapability capability) {
        return this.capabilities.contains(capability);
    }

    @NotNull
    @Override
    public Iterator<BatManCapability> iterator() {
        return this.capabilities.iterator();
    }
}
