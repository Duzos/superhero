package mc.duzo.timeless.datagen.provider.model;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;

import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class TimelessModelProvider extends FabricModelProvider {
    private final FabricDataOutput output;

    public TimelessModelProvider(FabricDataOutput output) {
        super(output);

        this.output = output;
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        for (Item item : Registries.ITEM) {
            if (!(item instanceof AutomaticModel)) continue;

            generator.register(item, Models.GENERATED);
        }
    }
    public boolean doesTextureExist(Identifier texture) {
        return this.output.getModContainer().findPath("assets/" + texture.getNamespace() + "/textures/" + texture.getPath() + ".png").isPresent();
    }
}
