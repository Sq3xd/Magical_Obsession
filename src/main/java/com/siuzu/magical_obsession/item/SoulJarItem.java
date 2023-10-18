package com.siuzu.magical_obsession.item;

import com.siuzu.magical_obsession.init.ModItems;
import com.siuzu.magical_obsession.item.tab.ModTabs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
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
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class SoulJarItem extends Item {

    public SoulJarItem(Properties properties) {
        super(properties);
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
        Level level = Minecraft.getInstance().level;

        Set<ResourceLocation> entityKeyList = ForgeRegistries.ENTITY_TYPES.getKeys();
        for (ResourceLocation k : entityKeyList) {
            EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(k);
            if (!entityType.getCategory().equals(MobCategory.MISC)) {
                ItemStack item = new ItemStack(ModItems.SOUL_JAR.get());

                CompoundTag nbt = new CompoundTag();
                nbt.putString("id", ForgeRegistries.ENTITY_TYPES.getKey(entityType).toString());
                item.setTag(nbt);

                LivingEntity entity = (LivingEntity) entityType.create(level);

                if (tab.equals(ModTabs.MAGICAL_OBSESSION_JARS)) {
                    if (entity.getMaxHealth() <= 50)
                        items.add(item);
                }
            }
        }
        super.fillItemCategory(tab, items);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (context.getItemInHand().getTag() != null && context.getItemInHand().getTag().contains("id", Tag.TAG_STRING)) {
            ResourceLocation rl = new ResourceLocation(context.getItemInHand().getTag().getString("id"));

            EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(rl);
            LivingEntity entity = (LivingEntity) entityType.create(level);

            entity.moveTo(context.getClickedPos().getX() + 0.5f, context.getClickedPos().getY() + 1, context.getClickedPos().getZ() + 0.5f);
            entity.addTag("respawned");
            if (context.getHand().equals(InteractionHand.MAIN_HAND) && !context.getPlayer().isShiftKeyDown()) {
                if (context.getPlayer().isCreative()) {
                    context.getPlayer().getMainHandItem().shrink(1);
                    level.addFreshEntity(entity);
                    level.playSound(context.getPlayer(), new BlockPos(entity.getX(), entity.getY(), entity.getZ()), SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.WEATHER, 1.1f, 1.1f);
                    spawnParticles(level, new BlockPos(entity.getX(), entity.getY(), entity.getZ()));
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
                tooltipComponents.add(Component.translatable("tooltip.magical_obsession.soul_jar"));
                tooltipComponents.add(Component.literal("§9§o" + name));
            } else {
                tooltipComponents.add(Component.translatable("tooltip.magical_obsession.shift"));
            }
        }
    }
    public static void spawnParticles(Level level, BlockPos pos) {
        double d0 = 0.5725D;
        RandomSource randomsource = level.random;

        for(Direction direction : Direction.values()) {
            BlockPos blockpos = pos.relative(direction);
            if (!level.getBlockState(blockpos).isSolidRender(level, blockpos)) {
                Direction.Axis direction$axis = direction.getAxis();
                double d1 = direction$axis == Direction.Axis.X ? 0.5D + 0.5625D * (double)direction.getStepX() : (double)randomsource.nextFloat();
                double d2 = direction$axis == Direction.Axis.Y ? 0.59D + 0.5625D * (double)direction.getStepY() : (double)randomsource.nextFloat();
                double d3 = direction$axis == Direction.Axis.Z ? 0.5D + 0.5625D * (double)direction.getStepZ() : (double)randomsource.nextFloat();
                level.addParticle(ParticleTypes.FLASH, (double)pos.getX() + d1, (double)pos.getY() + d2 + 1.11D, (double)pos.getZ() + d3, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}