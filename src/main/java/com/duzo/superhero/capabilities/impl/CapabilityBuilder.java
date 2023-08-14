package com.duzo.superhero.capabilities.impl;

import com.duzo.superhero.capabilities.AbstractCapability;

import static com.duzo.superhero.entities.ironman.IronManEntity.fileNameToUsable;

public class CapabilityBuilder extends AbstractCapability {
    protected String nameForText;
    public CapabilityBuilder(String name) {
        super(name);
        this.nameForText = fileNameToUsable(this.getSerializedName());
    }

    public CapabilityBuilder runner(abilityRunner ticker) {
        this.runner = ticker;
        return this;
    }

    public CapabilityBuilder nameForText(String name) {
        this.nameForText = name;
        return this;
    }
}
