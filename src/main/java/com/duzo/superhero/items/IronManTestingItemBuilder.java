package com.duzo.superhero.items;

import com.duzo.superhero.entities.IronManEntity;
import com.duzo.superhero.util.IronManMark;
import net.minecraft.world.item.Item;

public class IronManTestingItemBuilder extends IronManTestingItem {
    private IronManMark mark;
    public IronManTestingItemBuilder(Item.Properties properties, IronManMark mark) {
        super(properties);
        this.mark = mark;
    }
    public IronManTestingItemBuilder(Item.Properties properties) {
        super(properties);
        this.mark = IronManEntity.DEFAULT_MARK;
    }

    public IronManTestingItemBuilder mark(IronManMark mark) {
        this.mark = mark;
        return this;
    }

    @Override
    protected IronManMark getMark() {
        return this.mark;
    }
}
