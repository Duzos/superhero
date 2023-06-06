package com.duzo.superhero.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

import java.util.ArrayList;
import java.util.List;

public class IronManCapabilities {
    private List<IronManCapability> capabilities = new ArrayList<>();
    public IronManCapabilities add(IronManCapability capability) {
        this.capabilities.add(capability);
        return this;
    }
    public boolean has(IronManCapability capability) {
        return this.capabilities.contains(capability);
    }

//    public static class Serializer {
//        public void serialize(CompoundTag tag, IronManCapabilities cap) {
//            ListTag capTags = new ListTag();
//            cap.capabilities.forEach((capability -> capTags.add(capability.serialize())));
//
//            tag.put("capabilities",capTags);
//        }
//
//        public IronManCapabilities deserialize
//    }
}
