package mc.duzo.timeless.commands.argument;

import java.util.function.Supplier;

import com.mojang.brigadier.arguments.ArgumentType;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;

import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.util.Identifier;

import mc.duzo.timeless.Timeless;

public class TimelessArgumentRegister {
    public static void register() {
        register("suit", SuitArgumentType.class, SuitArgumentType::suit);
    }

    private static <T extends ArgumentType<?>> void register(String name, Class<T> t, Supplier<T> supplier) {
        ArgumentTypeRegistry.registerArgumentType(new Identifier(Timeless.MOD_ID, name), t, ConstantArgumentSerializer.of(supplier));
    }
}
