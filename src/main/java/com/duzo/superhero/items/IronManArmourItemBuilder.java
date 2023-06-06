package com.duzo.superhero.items;

import com.duzo.superhero.entities.IronManEntity;
import com.duzo.superhero.util.IronManMark;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

public class IronManArmourItemBuilder extends IronManArmourItem {
    private IronManMark mark;
    public IronManArmourItemBuilder(ArmorMaterial material, Type type, Properties properties, IronManMark mark) {
        super(material, type, properties);
        this.mark = mark;
    }
    public IronManArmourItemBuilder(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
        this.mark = IronManEntity.DEFAULT_MARK;
    }

    public IronManArmourItemBuilder mark(IronManMark mark) {
        this.mark = mark;
        return this;
    }

    @Override
    public IronManMark getMark() {
        return this.mark;
    }
}
