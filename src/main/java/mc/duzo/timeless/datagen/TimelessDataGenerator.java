package mc.duzo.timeless.datagen;

import dev.pavatus.lib.datagen.lang.LanguageType;
import dev.pavatus.lib.datagen.lang.SakitusLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import mc.duzo.timeless.Timeless;
import mc.duzo.timeless.datagen.provider.lang.AutomaticSuitEnglish;
import mc.duzo.timeless.datagen.provider.model.TimelessModelProvider;
import mc.duzo.timeless.datagen.provider.sound.BasicSoundProvider;
import mc.duzo.timeless.registry.Register;
import mc.duzo.timeless.suit.Suit;
import mc.duzo.timeless.suit.SuitRegistry;

public class TimelessDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator gen) {
        FabricDataGenerator.Pack pack = gen.createPack();

        genLang(pack);
        genSounds(pack);
        genModels(pack);
    }

    private void genLang(FabricDataGenerator.Pack pack) {
        genEnglish(pack);
    }

    private void genEnglish(FabricDataGenerator.Pack pack) {
        pack.addProvider((((output, registriesFuture) -> {
            SakitusLanguageProvider provider = new SakitusLanguageProvider(output, LanguageType.EN_US);

            for (Suit suit : SuitRegistry.REGISTRY) {
                if (!(suit instanceof AutomaticSuitEnglish)) continue;

                provider.addTranslation(suit.getTranslationKey(), convertToName(suit.id().getPath()));
            }

            provider.translateItems(Register.Items.class);
            provider.translateBlocks(Register.Blocks.class);

            provider.addTranslation("itemGroup." + Timeless.MOD_ID, "Timeless Heroes");

            return provider;
        })));
    }

    private void genSounds(FabricDataGenerator.Pack pack) {
        pack.addProvider((((output, registriesFuture) -> {
            BasicSoundProvider provider = new BasicSoundProvider(output);

            provider.addSound("thruster", Register.Sounds.THRUSTER);
            provider.addSound("mark5_noises", Register.Sounds.MARK5_NOISES);
            provider.addSound("ironman_step", Register.Sounds.IRONMAN_STEP);
            provider.addSound("ironman_mask", Register.Sounds.IRONMAN_MASK);
            provider.addSound("ironman_powerup", Register.Sounds.IRONMAN_POWERUP);
            provider.addSound("ironman_powerdown", Register.Sounds.IRONMAN_POWERDOWN);

            return provider;
        })));
    }

    private void genModels(FabricDataGenerator.Pack pack) {
        pack.addProvider(((output, registriesFuture) -> new TimelessModelProvider(output)));
    }

    private static String convertToName(String str) {
        String[] split = str.split("_");

        for (int i = 0; i < split.length; i++) {
            split[i] = split[i].substring(0, 1).toUpperCase() + split[i].substring(1).toLowerCase();
        }

        return String.join(" ", split);
    }
}
