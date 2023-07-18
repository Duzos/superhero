package com.duzo.superhero.ids;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.capabilities.SuperheroCapabilities;
import com.duzo.superhero.items.SuperheroArmourItem;
import com.duzo.superhero.util.SuperheroIdentifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import static com.duzo.superhero.entities.ironman.IronManEntity.fileNameToUsable;
import static com.duzo.superhero.entities.ironman.IronManEntity.nameFromSlot;
import static com.duzo.superhero.util.SuperheroUtil.DEFAULT_TEXTURE;
import static com.duzo.superhero.util.SuperheroUtil.doesResourceLocationExist;

public abstract class AbstractIdentifier {
    protected String name;
    protected SuperheroCapabilities caps;
    protected ResourceLocation texture = new ResourceLocation(Superhero.MODID,"textures/heroes/" + this.getSerializedName() + ".png");
    protected ResourceLocation lightmap = new ResourceLocation(Superhero.MODID,"textures/heroes/" + this.getSerializedName() + "_l.png");

    public AbstractIdentifier(String name, SuperheroCapabilities caps) {
        this.name = name;
        this.caps = caps;
    }
    public AbstractIdentifier(String name) {
        this(name,new SuperheroCapabilities());
    }

    public String name() {return this.name;}
    public String getSerializedName() {
        return this.name().toLowerCase();
    }

    /**
     * Defines whether this identifier will use an alex or steve skin in the generic renderer
     * @return
     */
    public boolean isSlim() {
        return false;
    }

    /**
     * Defines if this will be auto created in things like lang files and items so it doesnt need to be done manually
     * @return
     */
    public boolean autoAdd() {
        return true;
    }
    public String getLangFileName(EquipmentSlot slot) {
        String base = fileNameToUsable(this.getSerializedName());
        String slotName = fileNameToUsable(nameFromSlot(slot));

        return "Superhero" + " " + slotName;
    }
    public String getHoverTextName() {
        return fileNameToUsable(this.getSerializedName());
    }
    public SuperheroCapabilities getCapabilities() {return this.caps;}
//    public HashMap<String,?> getEnumSpecificValues() {
//        return new HashMap<>();
//    }

    public ResourceLocation texture() {
        if (!doesResourceLocationExist(this.texture)) {
            return DEFAULT_TEXTURE;
        }
        return this.texture;
    }

    public ResourceLocation lightmap() {
        if (!doesResourceLocationExist(this.lightmap)) {
            return DEFAULT_TEXTURE;
        }
        return this.lightmap;
    }

    public boolean isValidArmour(LivingEntity player) {
        SuperheroIdentifier currentID = null;

        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            if (!equipmentSlot.isArmor()) continue;
            ItemStack currentSlot = player.getItemBySlot(equipmentSlot);
            if (currentSlot.getItem() instanceof SuperheroArmourItem item) {
                if (currentID == null) {
                    currentID = item.getIdentifier();
                } else if (!currentID.equals(item.getIdentifier())) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }
    public boolean usesDefaultRenderer() {
        return true;
    }
}
