package mc.duzo.timeless.power;

import java.util.ArrayList;
import java.util.Collections;

import net.minecraft.server.network.ServerPlayerEntity;

public class PowerList extends ArrayList<Power> {
    public boolean run(int index, ServerPlayerEntity player) {
        if (index < 0 || index >= this.size()) return false;
        return this.get(index).run(player);
    }

    public static PowerList of(Power... powers) {
        PowerList list = new PowerList();
        Collections.addAll(list, powers);
        return list;
    }
}
