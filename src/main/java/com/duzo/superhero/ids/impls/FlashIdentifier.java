package com.duzo.superhero.ids.impls;

import com.duzo.superhero.capabilities.SuperheroCapabilities;
import com.duzo.superhero.capabilities.SuperheroCapabilityRegistry;
import com.duzo.superhero.ids.BaseIdentifier;
import net.minecraft.world.entity.EquipmentSlot;

import static com.duzo.superhero.entities.ironman.IronManEntity.fileNameToUsable;
import static com.duzo.superhero.entities.ironman.IronManEntity.nameFromSlot;

/**
 * An identifier that comes with the base spiderman abilities
 */
public class FlashIdentifier extends BaseIdentifier {
    /**
     * Create an instance
     * @param name The superheroes name
     * @param caps The additional capabilities to be added to the base ones
     */
    public FlashIdentifier(String name, SuperheroCapabilities caps) {
        super(name);
        this.caps.add(caps);
        this.caps.add(SuperheroCapabilityRegistry.SPEEDSTER,SuperheroCapabilityRegistry.FLASH_HUD);
    }

    @Override
    public String getLangFileName(EquipmentSlot slot) {
        String slotName = fileNameToUsable(nameFromSlot(slot));

        return "Flash" + " " + slotName;
    }
}
