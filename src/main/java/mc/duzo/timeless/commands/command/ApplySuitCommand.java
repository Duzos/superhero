package mc.duzo.timeless.commands.command;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import mc.duzo.timeless.Timeless;
import mc.duzo.timeless.commands.argument.SuitArgumentType;
import mc.duzo.timeless.suit.Suit;
public class ApplySuitCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal(Timeless.MOD_ID)
                .then(literal("suit").requires(source -> source.hasPermissionLevel(2))
                        .then(literal("wear")
                                .then(argument("suit", SuitArgumentType.suit())
                                        .executes(ApplySuitCommand::runCommand)))
                ));
    }

    private static int runCommand(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        if (player == null) return 0;

        Suit suit = SuitArgumentType.getSuit(context, "suit");
        suit.getSet().wear(player);

        return Command.SINGLE_SUCCESS;
    }
}
