package com.duzo.superhero.client.gui.widgets;

import com.duzo.superhero.ids.AbstractIdentifier;

public class IdentifierButton extends ItemButton{
    public final AbstractIdentifier id;
    public IdentifierButton(int x, int y, int width, int height, OnPress onPress, AbstractIdentifier id) {
        super(x, y, width, height, onPress, id.icon());
        this.id = id;
    }
}
