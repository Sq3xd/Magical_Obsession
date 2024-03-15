package com.siuzu.magical_obsession.item;

import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import com.siuzu.magical_obsession.init.ModItems;
import com.siuzu.magical_obsession.init.ModTabs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraftforge.registries.ForgeRegistries;
import org.w3c.dom.Attr;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

public class MobSoulItem extends Item {
    public MobSoulItem(Properties properties) {
        super(properties);
    }




    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
        Level level = Minecraft.getInstance().level;

        Set<ResourceLocation> entityKeyList = ForgeRegistries.ENTITY_TYPES.getKeys();
        for (ResourceLocation k : entityKeyList) {
            EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(k);
            if (!entityType.getCategory().equals(MobCategory.MISC)) {
                ItemStack item = new ItemStack(ModItems.MOB_SOUL.get());

                CompoundTag nbt = new CompoundTag();
                nbt.putString("id", ForgeRegistries.ENTITY_TYPES.getKey(entityType).toString());
                item.setTag(nbt);

                LivingEntity entity = (LivingEntity) entityType.create(level);

                if (tab.equals(ModTabs.MAGICAL_OBSESSION_SOULS)) {
                    if (entity.getMaxHealth() <= 50)
                        items.add(item);
                }
            }
        }
        super.fillItemCategory(tab, items);
    }

    public static LivingEntity getEntity(ItemStack item, @Nullable Level level) {
        LivingEntity entity;

        if (item.getTag() != null) {
            ResourceLocation rl = new ResourceLocation(item.getTag().getString("id"));
            EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(rl);
            entity = (LivingEntity) entityType.create(level);
        } else {
            entity = null;
        }

        return entity;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (context.getItemInHand().getTag() != null && context.getItemInHand().getTag().contains("id", Tag.TAG_STRING)) {
            LivingEntity entity = getEntity(context.getItemInHand(), context.getLevel());
            entity.moveTo(context.getClickedPos().getX() + 0.5f, context.getClickedPos().getY() + 1, context.getClickedPos().getZ() + 0.5f);
            entity.rotate(Rotation.getRandom(RandomSource.create()));
            entity.setYHeadRot(RandomSource.create().nextInt(1, 360));
            entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(entity.getMaxHealth() / 2);
            entity.addTag("respawned");
            if (context.getHand().equals(InteractionHand.MAIN_HAND) && !context.getPlayer().isShiftKeyDown()) {
                if (context.getPlayer().isCreative()) {
                    context.getPlayer().getMainHandItem().shrink(1);
                    level.addFreshEntity(entity);
                    level.playSound(context.getPlayer(), new BlockPos(entity.getX(), entity.getY(), entity.getZ()), SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.WEATHER, 1.1f, 1.1f);
                }
            }
        }
        return super.useOn(context);
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (stack.getTag() != null && stack.getTag().contains("id", Tag.TAG_STRING)) {
            String name = stack.getTag().get("id").toString().replace("entity.", "").replace('.', ':');
            if(Screen.hasShiftDown()) {
                tooltipComponents.add(Component.translatable("tooltip.magical_obsession.mob_soul"));
                tooltipComponents.add(Component.literal("§9§o" + name));
            } else {
                tooltipComponents.add(Component.translatable("tooltip.magical_obsession.shift"));
            }
        }
    }
}