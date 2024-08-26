package mc.duzo.timeless.datagen.provider.lang;

import mc.duzo.timeless.Timeless;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.HashMap;

public class LanguageProvider extends FabricLanguageProvider {

	public HashMap<String, String> translations = new HashMap<>();

	public LanguageType languageType;
	private final FabricDataOutput dataGenerator;

	public LanguageProvider(FabricDataOutput dataOutput, LanguageType languageType) {
		super(dataOutput, languageType.name().toLowerCase());
		this.languageType = languageType;
		this.dataGenerator = dataOutput;
	}

	@Override
	public void generateTranslations(TranslationBuilder translationBuilder) {
		for (String key : translations.keySet()) {
			translationBuilder.add(key, translations.get(key));
		}

		dataGenerator.getModContainer().findPath("assets/" + Timeless.MOD_ID + "/lang/" + languageType.name().toLowerCase() + ".existing.json").ifPresent(existingFilePath -> {
			try {
				translationBuilder.add(existingFilePath);
			} catch (Exception e) {
				LOGGER.warn("Failed to add existing language file! " + "(" + languageType.name().toLowerCase() + ") | ", e);
			}
		});
	}

	/**
	 * Adds a translation to the language file.
	 *
	 * @param item        The item to add the translation for.
	 * @param translation The translation.
	 */
	public void addTranslation(Item item, String translation) {
		translations.put(item.getTranslationKey(), translation);
	}

	/**
	 * Adds a translation to the language file.
	 *
	 * @param key         The key to add the translation for.
	 * @param translation The translation.
	 */
	public void addTranslation(String key, String translation) {
		translations.put(key, translation);
	}

	/**
	 * Adds a translation to the language file
	 *
	 * @param block       The block to add the translation for
	 * @param translation The translation
	 */
	public void addTranslation(Block block, String translation) {
		translations.put(block.getTranslationKey(), translation);
	}
}