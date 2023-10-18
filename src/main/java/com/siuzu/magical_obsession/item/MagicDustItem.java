package com.siuzu.magical_obsession.item;

import com.siuzu.magical_obsession.init.ModBlocks;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class MagicDustItem extends Item {
    public MagicDustItem(Properties properties){
        super(properties);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Random random = new Random();
        if (context.getLevel().isClientSide) {
            Level level = context.getLevel();
            Player player = context.getPlayer();
            BlockPos pos = context.getClickedPos();
            BlockState state = level.getBlockState(pos);

            if (state.is(Blocks.CAULDRON) && !player.isShiftKeyDown()){
                level.destroyBlock(pos, false);
                stack.shrink(1);
                level.playLocalSound(pos.getX() + random.nextDouble(-3d, 3d), pos.getY() + random.nextDouble(-3d, 3d), pos.getZ() + random.nextDouble(-3d, 3d), SoundEvents.VEX_CHARGE, SoundSource.BLOCKS, 1.5f, 0.9f, true);
                level.playLocalSound(pos.getX() + random.nextDouble(-3d, 3d), pos.getY() + random.nextDouble(-3d, 3d), pos.getZ() + random.nextDouble(-3d, 3d), SoundEvents.VEX_CHARGE, SoundSource.BLOCKS, 1.3f, 0.7f, true);
                level.playLocalSound(pos.getX() + random.nextDouble(-3d, 3d), pos.getY() + random.nextDouble(-3d, 3d), pos.getZ() + random.nextDouble(-3d, 3d), SoundEvents.VEX_CHARGE, SoundSource.BLOCKS, 1.2f, 0.5f, true);
                level.playLocalSound(pos.getX() + random.nextDouble(-3d, 3d), pos.getY() + random.nextDouble(-3d, 3d), pos.getZ() + random.nextDouble(-3d, 3d), SoundEvents.VEX_CHARGE, SoundSource.BLOCKS, 1.1f, 0.4f, true);
                level.playLocalSound(pos.getX() + random.nextDouble(-3d, 3d), pos.getY() + random.nextDouble(-3d, 3d), pos.getZ() + random.nextDouble(-3d, 3d), SoundEvents.VEX_CHARGE, SoundSource.BLOCKS, 1.0f, 0.3f, true);
                spawnParticles(level, pos);
                level.setRainLevel(1.5f);
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.PASS;
            }
        }

        if (!context.getLevel().isClientSide) {
            Level level = context.getLevel();
            Player player = context.getPlayer();
            BlockPos pos = context.getClickedPos();
            BlockState state = level.getBlockState(pos);

            if (state.is(Blocks.CAULDRON) && !player.isShiftKeyDown()){
                if (random.nextInt(8) != 1){
                    level.destroyBlock(pos, false);
                    LightningBolt lb = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                    lb.setPos(pos.getX(), pos.getY(), pos.getZ());
                    level.addFreshEntity(lb);
                    level.setRainLevel(1.5f);
                    setBlock(level, pos);
                } else {
                    explode(level, pos);
                }
                stack.shrink(1);
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.PASS;
            }
        }

        return InteractionResult.FAIL;
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
                level.addParticle(ParticleTypes.DRAGON_BREATH, (double)pos.getX() + d1, (double)pos.getY() + d2 + 1.77D, (double)pos.getZ() + d3, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    public static void setBlock(Level level, BlockPos pos) {
        if (!level.isClientSide){
            level.setBlock(pos, ModBlocks.SPECIAL_CAULDRON.get().defaultBlockState(), 1);
        }
    }

    public static void explode(Level level, BlockPos pos){
        if (!level.isClientSide){
            level.explode(null, DamageSource.MAGIC, null, pos.getX(), pos.getY(), pos.getZ(), 3.5f, false, Explosion.BlockInteraction.DESTROY);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level pLevel, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        if(Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.magical_obsession.magic_dust"));
        } else {
            tooltipComponents.add(Component.translatable("tooltip.magical_obsession.shift"));
        }
    }
}
