package mc.duzo.timeless.commands.argument;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import net.minecraft.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import mc.duzo.timeless.suit.Suit;
import mc.duzo.timeless.suit.SuitRegistry;

public class SuitArgumentType implements ArgumentType<Suit> {
    private static final Collection<String> EXAMPLES = Arrays.asList("foo", "foo:bar", "012");

    public static SuitArgumentType suit() {
        return new SuitArgumentType();
    }

    public static Suit getSuit(CommandContext<ServerCommandSource> context, String name) {
        return context.getArgument(name, Suit.class);
    }

    public Suit parse(StringReader stringReader) throws CommandSyntaxException {
        Identifier id = Identifier.fromCommandInput(stringReader);
        Suit found = SuitRegistry.REGISTRY.get(id);

        if (found == null) throw new SimpleCommandExceptionType(Text.literal("Suit not found in registry")).create();

        return found;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return CommandSource.suggestMatching(SuitRegistry.REGISTRY.stream().map(Suit::id).map(Identifier::toString), builder);
    }

    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
