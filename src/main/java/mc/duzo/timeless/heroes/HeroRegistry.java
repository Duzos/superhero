package mc.duzo.timeless.heroes;

import mc.duzo.timeless.TimelessHeroes;
import mc.duzo.timeless.network.TimelessServerNetworking;
import mc.duzo.timeless.network.packets.SyncHeroesPacket;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class HeroRegistry {
    private static final HashMap<Identifier, HeroSchema> REGISTRY = new HashMap<>();

    public static void register(HeroSchema schema, Identifier id) {
        REGISTRY.put(id, schema);
    }
    public static void register(HeroSchema schema) {
        register(schema, schema.id());
    }

    public static Collection<HeroSchema> values() {
        return REGISTRY.values();
    }
    public static Iterator<HeroSchema> iterator() {
        return REGISTRY.values().iterator();
    }
    public static int size() {
        return REGISTRY.size();
    }

    private static void clear() {
        REGISTRY.clear();
    }

    // I dont think this is necessary?
    private static void syncToAll() {
        TimelessServerNetworking.sendToAll(new SyncHeroesPacket(REGISTRY.values()));
    }
    private static void syncToClient(ServerPlayerEntity player) {
        TimelessServerNetworking.send(new SyncHeroesPacket(REGISTRY.values()), player);
    }

    public static void init() {
        clear();

        // ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> syncToClient(handler.getPlayer()));

        // Reading Datapack Heroes
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return new Identifier(TimelessHeroes.MOD_ID, "heroes");
            }

            @Override
            public void reload(ResourceManager manager) {
                clear();

                for (Identifier id : manager.findResources("heroes", file -> file.getPath().endsWith(".json")).keySet()) {
                    try (InputStream stream = manager.getResource(id).get().getInputStream()) {
                        HeroSchema created = DatapackHero.fromInputStream(stream);

                        if (created == null) {
                            stream.close();
                            continue;
                        }

                        register(created);
                        stream.close();
                        TimelessHeroes.LOGGER.info("Loaded datapack hero: " + created.id().toString());
                        // syncToAll();

                    } catch (Exception e) {
                        TimelessHeroes.LOGGER.error("Error while loading resource json: " + id.toString(), e);
                    }
                }
            }
        });
    }
}
