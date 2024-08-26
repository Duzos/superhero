package mc.duzo.timeless.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import mc.duzo.timeless.datagen.provider.lang.LanguageProvider;
import mc.duzo.timeless.datagen.provider.lang.LanguageType;
import mc.duzo.timeless.datagen.provider.sound.BasicSoundProvider;

public class TimelessDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator gen) {
        FabricDataGenerator.Pack pack = gen.createPack();

        genLang(pack);
        genSounds(pack);
    }

    private void genLang(FabricDataGenerator.Pack pack) {
        genEnglish(pack);
    }

    private void genEnglish(FabricDataGenerator.Pack pack) {
        pack.addProvider((((output, registriesFuture) -> {
            LanguageProvider provider = new LanguageProvider(output, LanguageType.EN_US);

            return provider;
        })));
    }

    private void genSounds(FabricDataGenerator.Pack pack) {
        pack.addProvider((((output, registriesFuture) -> {
            BasicSoundProvider provider = new BasicSoundProvider(output);



            return provider;
        })));
    }

    private static String convertToName(String str) {
        String[] split = str.split("_");

        for (int i = 0; i < split.length; i++) {
            split[i] = split[i].substring(0, 1).toUpperCase() + split[i].substring(1).toLowerCase();
        }

        return String.join(" ", split);
    }
}
