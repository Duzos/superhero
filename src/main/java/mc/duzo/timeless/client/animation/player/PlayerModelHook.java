package mc.duzo.timeless.client.animation.player;

import java.util.Optional;

import net.minecraft.client.model.ModelPart;

/**
 * For creating custom methods in player model class
 * Not very good, and should be changed
 *
 * @author duzo
 */
public interface PlayerModelHook {
    Optional<ModelPart> timeless$getChild(String name);
    ModelPart timeless$getPart();
}