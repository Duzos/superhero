package mc.duzo.timeless.suit.ironman.mk3.client;

import mc.duzo.timeless.suit.client.ClientSuit;
import mc.duzo.timeless.suit.ironman.client.GenericIronManModel;
import mc.duzo.timeless.suit.set.SetRegistry;

public class MarkThreeModel extends GenericIronManModel {
    private ClientSuit parent;

    @Override
    public ClientSuit getSuit() {
        if (this.parent == null) {
            this.parent = SetRegistry.MARK_THREE.suit().toClient();
        }

        return this.parent;
    }
}
