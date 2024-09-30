package mc.duzo.timeless.suit.ironman.mk7.client;

import mc.duzo.timeless.suit.client.ClientSuit;
import mc.duzo.timeless.suit.ironman.client.GenericIronManModel;
import mc.duzo.timeless.suit.set.SetRegistry;

public class MarkSevenModel extends GenericIronManModel {
    private ClientSuit parent;

    @Override
    public ClientSuit getSuit() {
        if (this.parent == null) {
            this.parent = SetRegistry.MARK_SEVEN.suit().toClient();
        }

        return this.parent;
    }
}
