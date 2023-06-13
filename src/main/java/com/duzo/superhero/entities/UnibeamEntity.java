package com.duzo.superhero.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class UnibeamEntity extends Entity {
    public UnibeamEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    public UnibeamEntity(Level level) {
        super(SuperheroEntities.UNIBEAM_ENTITY.get(),level);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_20052_) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_20139_) {

    }
}
