package com.duzo.superhero.items;

import com.duzo.superhero.client.models.AlexSkinModel;
import com.duzo.superhero.client.models.SteveSkinModel;
import com.duzo.superhero.util.SuperheroCapability;
import com.duzo.superhero.util.SuperheroIdentifier;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

import static com.duzo.superhero.util.SuperheroUtil.isEquipped;

public class SuperheroArmourItem extends ArmorItem {
    private final SuperheroIdentifier id;
    public static final SuperheroIdentifier DEFAULT_ID = SuperheroIdentifier.AMAZING_SPIDER_MAN;

    public SuperheroArmourItem(ArmorMaterial material, Type type, Properties properties, SuperheroIdentifier id) {
        super(material, type, properties);
        this.id = id;
    }
    public SuperheroArmourItem(ArmorMaterial material, Type type, Properties properties) {
        this(material,type,properties,DEFAULT_ID);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                SuperheroArmourItem item = (SuperheroArmourItem) itemStack.getItem(); // frick you craig im casting

                if (item.getIdentifier().isSlim()) {
                    AlexSkinModel<?> model = new AlexSkinModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(AlexSkinModel.LAYER_LOCATION));

                    model.setAllVisible(false);

                    if (equipmentSlot == EquipmentSlot.HEAD) {
                        model.hat.visible = true;
                        model.head.visible = true;
                    } else if (equipmentSlot == EquipmentSlot.CHEST) {
                        model.body.visible = true;
                        model.leftArm.visible = true;
                        model.rightArm.visible = true;
                    } else if (equipmentSlot == EquipmentSlot.FEET || equipmentSlot == EquipmentSlot.LEGS) {
                        model.leftLeg.visible = true;
                        model.rightLeg.visible = true;
                    }

                    if (livingEntity.isInvisible()) model.setAllVisible(false);

                    return model;
                } else {
                    SteveSkinModel<?> model = new SteveSkinModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(SteveSkinModel.LAYER_LOCATION));

                    model.setAllVisible(false);

                    if (equipmentSlot == EquipmentSlot.HEAD) {
                        model.hat.visible = true;
                        model.head.visible = true;
                    } else if (equipmentSlot == EquipmentSlot.CHEST) {
                        model.body.visible = true;
                        model.leftArm.visible = true;
                        model.rightArm.visible = true;
                    } else if (equipmentSlot == EquipmentSlot.FEET || equipmentSlot == EquipmentSlot.LEGS) {
                        model.leftLeg.visible = true;
                        model.rightLeg.visible = true;
                    }

                    if (livingEntity.isInvisible()) model.setAllVisible(false);

                    return model;
                }
            }
        });
        super.initializeClient(consumer);
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        if (entity.isInvisible()) return "superhero:textures/heroes/generic/invisible.png";

        return "superhero:textures/heroes/" + this.getIdentifier().getSerializedName() + ".png";
    }

    public SuperheroIdentifier getIdentifier() {
        return this.id;
    }

//    public abstract void runAbility(Player player, int number);

    public void runAbility(Player player, int number) {
        for (SuperheroCapability cap : this.getIdentifier().getCapabilities()) {
            cap.runAbility(number,player);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int p_41407_, boolean p_41408_) {
        super.inventoryTick(stack, level, entity, p_41407_, p_41408_);

        if (entity instanceof Player player) {

            for (SuperheroCapability cap : this.getIdentifier().getCapabilities()) {
                if (!isEquipped(stack,player)) {
                    cap.unequippedTick(stack,level,player);
                } else {
                    cap.tick(stack, level, player);
                }
            }
        }
    }

    // OR you can override this and add your custom hover text in the item class, this is just if you want to have /n in your translatable string and converted into newlines automatically.
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if (this.getIdentifier() != null && Screen.hasShiftDown()) {
            components.add(Component.translatable(this.getIdentifier().getHoverTextName()).withStyle(ChatFormatting.GOLD));
        }

        if (Screen.hasShiftDown()) {
            String[] split = splitStringByNewLines(Component.translatable("text." + stack.getItem().getDescriptionId() + ".shifting").getString());
            if (split.length > 0) {
                for (String line : split) {
                    components.add(Component.literal(line).withStyle(ChatFormatting.GREEN));
                }
            } else {
                components.add(Component.translatable("text." + stack.getItem().getDescriptionId()).withStyle(ChatFormatting.GREEN));
            }
        } else {
            String[] split = splitStringByNewLines(Component.translatable("text." + stack.getItem().getDescriptionId()).getString());
            if (split.length > 0) {
                for (String line : split) {
                    components.add(Component.literal(line).withStyle(ChatFormatting.GRAY));
                }
            } else {
                components.add(Component.translatable("text." + stack.getItem().getDescriptionId()).withStyle(ChatFormatting.GRAY));
            }
        }

        super.appendHoverText(stack, level, components, flag);
    }

    public String getHoverTextMessage() {
        return "Press <Shift> for more info";
    }
    public String getShiftingHoverTextMessage() {
        StringBuilder base = new StringBuilder();

        for (SuperheroCapability capability : this.getIdentifier().getCapabilities()) {
            base.append("/n").append(capability.getNameForText());
        }

        return base.toString();
    }

    public static String[] splitStringByNewLines(String string) {
        return string.split("/n");
    }
}
