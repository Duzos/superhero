package mc.duzo.timeless.commands.command;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

import net.minecraft.server.command.ServerCommandSource;

import mc.duzo.timeless.commands.argument.TimelessArgumentRegister;

public class TimelessCommands {
    public static void init() {
        TimelessArgumentRegister.register();
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> registerCommands(dispatcher)));
    }

    private static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        ApplySuitCommand.register(dispatcher);
    }
}
