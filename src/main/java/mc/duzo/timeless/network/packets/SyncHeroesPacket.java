package mc.duzo.timeless.network.packets;

import mc.duzo.timeless.TimelessHeroes;
import mc.duzo.timeless.heroes.DatapackHero;
import mc.duzo.timeless.heroes.HeroSchema;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public record SyncHeroesPacket(Collection<HeroSchema> heroes) implements FabricPacket {
    public static final PacketType<SyncHeroesPacket> TYPE = PacketType.create(new Identifier(TimelessHeroes.MOD_ID, "sync_heroes"), SyncHeroesPacket::new);

    public SyncHeroesPacket(PacketByteBuf buf) {
        this(readHeroes(buf.readInt(), buf));
    }
    @Override
    public void write(PacketByteBuf buf) {
        buf.writeInt(this.heroes.size());

        for (HeroSchema schema : this.heroes) {
            buf.encodeAsJson(DatapackHero.CODEC, schema);
        }
    }
    public static Collection<HeroSchema> readHeroes(int size, PacketByteBuf buf) {
        List<HeroSchema> list = new ArrayList<>();

        for (int i = 0; i < size; ++i) {
            list.add(buf.decodeAsJson(DatapackHero.CODEC));
        }

        return list;
    }
    @Override
    public PacketType<?> getType() {
        return TYPE ;
    }
}
