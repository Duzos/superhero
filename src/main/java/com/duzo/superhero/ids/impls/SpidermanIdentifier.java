package com.duzo.superhero.ids.impls;

import com.duzo.superhero.capabilities.SuperheroCapabilities;
import com.duzo.superhero.capabilities.SuperheroCapability;
import com.duzo.superhero.ids.BaseIdentifier;
import com.duzo.superhero.util.spiderman.SpiderManUtil;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

import static com.duzo.superhero.entities.ironman.IronManEntity.fileNameToUsable;
import static com.duzo.superhero.entities.ironman.IronManEntity.nameFromSlot;

/**
 * An identifier that comes with the base spiderman abilities
 */
public class SpidermanIdentifier extends BaseIdentifier {
    private final boolean female;
    /**
     * Create an instance
     * @param name The superheroes name
     * @param caps The additional capabilities to be added to the base ones
     * @param female "Spider-Woman" instead of "SpiderMan"
     */
    public SpidermanIdentifier(String name, SuperheroCapabilities caps, boolean female) {
        super(name, caps);
        this.female = female;
        this.caps.add(SuperheroCapability.WEB_SHOOTING,SuperheroCapability.WALL_CLIMBING,SuperheroCapability.SUPER_STRENGTH,SuperheroCapability.FAST_MOBILITY,SuperheroCapability.SPIDERMAN_HUD);
    }
    public SpidermanIdentifier(String name, SuperheroCapabilities caps) {
        this(name,caps,false);
    }

    public SpidermanIdentifier(String name) {
        this(name, new SuperheroCapabilities());
    }

    @Override
    public boolean isValidArmour(LivingEntity player) {
        return SpiderManUtil.isValidArmor(player);
    }

    @Override
    public String getLangFileName(EquipmentSlot slot) {
        String slotName = fileNameToUsable(nameFromSlot(slot));

        if (this.female) {
            return "SpiderWoman" + " " + slotName;
        }
        return "SpiderMan" + " " + slotName;
    }
}
