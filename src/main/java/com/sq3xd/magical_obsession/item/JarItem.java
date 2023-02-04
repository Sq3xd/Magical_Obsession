package com.sq3xd.magical_obsession.item;

import com.sq3xd.magical_obsession.block.tile.EntityDuplicatorBlockEntity;
import com.sq3xd.magical_obsession.block.tile.MagicalCatallyzatorBlockEntity;
import com.sq3xd.magical_obsession.item.tab.ModTabs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class JarItem extends Item {

    private final EntityType<?> entityType;

    public JarItem(Properties properties, EntityType<?> entityType) {
        super(properties);
        this.entityType = entityType;
    }

    @Override
    public Component getName(ItemStack item) {
        ItemModelShaper itemModelShaper = Minecraft.getInstance().getItemRenderer().getItemModelShaper();
        itemModelShaper.register(item.getItem(), new ModelResourceLocation("magical_obsession:soul_jar", "inventory"));
        return Component.translatable("item.magical_obsession.soul_jar");
    }

    public EntityType<?> getEntityType() {
        return entityType;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();

        Entity entity = entityType.create(level);
        entity.moveTo(context.getClickedPos().getX() + 0.5f, context.getClickedPos().getY() + 1, context.getClickedPos().getZ() + 0.5f);
        entity.addTag("respawned");

        if (context.getHand().equals(InteractionHand.MAIN_HAND) && !context.getPlayer().isShiftKeyDown()) {
            if (context.getPlayer().isCreative()) {
                context.getPlayer().getMainHandItem().shrink(1);
                level.addFreshEntity(entity);
                level.playSound(context.getPlayer(), new BlockPos(entity.getX(), entity.getY(), entity.getZ()), SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.WEATHER, 1.1f, 1.1f);
                spawnParticles(level, new BlockPos(entity.getX(), entity.getY(), entity.getZ()));
            } else {
                context.getPlayer().displayClientMessage(Component.translatable("message.magical_obsession.player.soul_jar"), true);
            }
        }
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        String name = EntityType.getKey(entityType).toString();
        if(Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.magical_obsession.soul_jar"));
            tooltipComponents.add(Component.literal("§9§o" + name));
        } else {
            tooltipComponents.add(Component.translatable("tooltip.magical_obsession.shift"));
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