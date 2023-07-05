package com.duzo.superhero.util.player;

import net.minecraft.nbt.CompoundTag;

public class PlayerDataUtil {
    // @TODO syncs for all these.
    /**
     * Adds custom data to a provided entity
     * @param entity
     * @param tag The data you want saved
     * @param name The name to save it under
     * @return The persistent data
     */
    public static CompoundTag insertData(IEntityDataSaver entity, CompoundTag tag, String name) {
        CompoundTag pers = entity.getPersistentData();

        pers.put(name,tag);

        return pers;
    }
    /**
     * Adds custom data to a provided entity
     * @param entity
     * @param val The integer you want saved
     * @param name The name to save it under
     * @return The persistent data
     */
    public static CompoundTag insertData(IEntityDataSaver entity, int val, String name) {
        CompoundTag pers = entity.getPersistentData();

        pers.putInt(name,val);

        return pers;
    }
    /**
     * Adds custom data to a provided entity
     * @param entity
     * @param val The double you want saved
     * @param name The name to save it under
     * @return The persistent data
     */
    public static CompoundTag insertData(IEntityDataSaver entity, double val, String name) {
        CompoundTag pers = entity.getPersistentData();

        pers.putDouble(name,val);

        return pers;
    }
    /**
     * Adds custom data to a provided entity
     * @param entity
     * @param val The string you want saved
     * @param name The name to save it under
     * @return The persistent data
     */
    public static CompoundTag insertData(IEntityDataSaver entity, String val, String name) {
        CompoundTag pers = entity.getPersistentData();

        pers.putString(name,val);

        return pers;
    }
    /**
     * Adds custom data to a provided entity
     * @param entity
     * @param val The string you want saved
     * @param name The name to save it under
     * @return The persistent data
     */
    public static CompoundTag insertData(IEntityDataSaver entity, boolean val, String name) {
        CompoundTag pers = entity.getPersistentData();

        pers.putBoolean(name,val);

        return pers;
    }

    /**
     *
     * @param data
     * @return The entitys data
     */
    public static CompoundTag getCustomData(IEntityDataSaver data) {
        return data.getPersistentData();
    }
}
