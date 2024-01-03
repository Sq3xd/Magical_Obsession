package com.siuzu.magical_obsession.item;

import com.siuzu.magical_obsession.init.ModBlocks;
import com.siuzu.magical_obsession.particle.ModParticles;
import com.siuzu.magical_obsession.sound.ModSounds;
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
                level.playSound(player, pos, ModSounds.MAGIC_DUST_ON_USE.get(), SoundSource.BLOCKS,1f,1f);
                stack.shrink(1);
                spawnParticles(level, pos);
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
                setBlock(level, pos);
                stack.shrink(1);
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.PASS;
            }
        }

        return InteractionResult.FAIL;
    }

    public static void spawnParticles(Level level, BlockPos pos) {
        RandomSource randomsource = level.random;

        for (int i = 0; i <= 10; i++) {
            level.addParticle(ModParticles.MAGIC_DUST_PARTICLES.get(), pos.getX() + (float)i/10, pos.getY() + 1d, pos.getZ() - 0.03, 0, -1, -1);
            level.addParticle(ModParticles.MAGIC_DUST_PARTICLES.get(), pos.getX() - 0.03, pos.getY() + 1d, pos.getZ() + (float)i/10, 0, -1, 1);
            level.addParticle(ModParticles.MAGIC_DUST_PARTICLES.get(), pos.getX() + (float)i/10, pos.getY() + 1d, pos.getZ() + 1.03, -1, -1, 0);
            level.addParticle(ModParticles.MAGIC_DUST_PARTICLES.get(), pos.getX() + 1.03, pos.getY() + 1d, pos.getZ() + (float)i/10, 1, -1, 0);
            level.addParticle(ModParticles.MAGIC_DUST_PARTICLES.get(), pos.getX() + 0.5d, pos.getY() + 1.5d, pos.getZ() + 0.5d, 0, -1, 0);

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
