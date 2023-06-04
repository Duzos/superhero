package com.duzo.superhero.items;

import com.duzo.superhero.entities.IronManEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

public class IronManArmourItemBuilder extends IronManArmourItem {
    private String mark;
    public IronManArmourItemBuilder(ArmorMaterial material, Type type, Properties properties, String mark) {
        super(material, type, properties);
        this.mark = mark;
    }
    public IronManArmourItemBuilder(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
        this.mark = IronManEntity.DEFAULT_MARK;
    }

    public IronManArmourItemBuilder mark(String mark) {
        this.mark = mark;
        return this;
    }

    @Override
    public String getMark() {
        return this.mark;
    }
}
