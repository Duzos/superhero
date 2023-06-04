package com.duzo.superhero.items;

import com.duzo.superhero.entities.IronManEntity;
import net.minecraft.world.item.Item;

public class IronManTestingItemBuilder extends IronManTestingItem {
    private String mark;
    public IronManTestingItemBuilder(Item.Properties properties, String mark) {
        super(properties);
        this.mark = mark;
    }
    public IronManTestingItemBuilder(Item.Properties properties) {
        super(properties);
        this.mark = IronManEntity.DEFAULT_MARK;
    }

    public IronManTestingItemBuilder mark(String mark) {
        this.mark = mark;
        return this;
    }

    @Override
    protected String getMark() {
        return this.mark;
    }
}
