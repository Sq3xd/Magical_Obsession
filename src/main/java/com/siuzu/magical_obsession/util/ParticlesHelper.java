package com.siuzu.magical_obsession.util;

import com.siuzu.magical_obsession.init.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

public class ParticlesHelper {
    public static void cauldronParticles(Level level, BlockPos pos) {
        double d0 = 0.5725D;
        RandomSource randomsource = level.random;

        for(Direction direction : Direction.values()) {
            BlockPos blockpos = pos.relative(direction);
            if (!level.getBlockState(blockpos).isSolidRender(level, blockpos)) {
                Direction.Axis direction$axis = direction.getAxis();
                double d1 = direction$axis == Direction.Axis.X ? 0.5D + 0.5625D * (double)direction.getStepX() : (double)randomsource.nextFloat();
                double d2 = direction$axis == Direction.Axis.Y ? 0.59D + 0.5625D * (double)direction.getStepY() : (double)randomsource.nextFloat();
                double d3 = direction$axis == Direction.Axis.Z ? 0.5D + 0.5625D * (double)direction.getStepZ() : (double)randomsource.nextFloat();
                level.addParticle(ParticleTypes.SMOKE, (double)pos.getX() + d1, (double)pos.getY() + d2 + 1.29D, (double)pos.getZ() + d3, 0.0D, 0.0D, 0.0D);
                level.addParticle(ParticleTypes.ENCHANT, (double)pos.getX() + d1, (double)pos.getY() + d2 + 1.11D, (double)pos.getZ() + d3, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    public static void cauldronCrafted(Level level, BlockPos pos) {
        double d0 = 0.5725D;
        RandomSource randomsource = level.random;

        for(Direction direction : Direction.values()) {
            BlockPos blockpos = pos.relative(direction);
            if (!level.getBlockState(blockpos).isSolidRender(level, blockpos)) {
                Direction.Axis direction$axis = direction.getAxis();
                double d1 = direction$axis == Direction.Axis.X ? 0.512D + 0.5725D * (double)direction.getStepX() : (double)randomsource.nextFloat();
                double d2 = direction$axis == Direction.Axis.Y ? 0.592D + 0.5725D * (double)direction.getStepY() : (double)randomsource.nextFloat();
                double d3 = direction$axis == Direction.Axis.Z ? 0.512D + 0.56725D * (double)direction.getStepZ() : (double)randomsource.nextFloat();
                level.addParticle(ParticleTypes.DRAGON_BREATH, (double)pos.getX() + d1, (double)pos.getY() + d2 + 1.27D, (double)pos.getZ() + d3, 0.0111D, 0.0111D, 0.0111D);
            }
        }
    }

    public static void spawnFlashParticles(Level level, BlockPos pos) {
        double d0 = 0.5725D;
        RandomSource randomsource = level.random;

        for (Direction direction : Direction.values()) {
            BlockPos blockpos = pos.relative(direction);
            if (!level.getBlockState(blockpos).isSolidRender(level, blockpos)) {
                Direction.Axis direction$axis = direction.getAxis();
                double d1 = direction$axis == Direction.Axis.X ? 0.5D + 0.5625D * (double) direction.getStepX() : (double) randomsource.nextFloat();
                double d2 = direction$axis == Direction.Axis.Y ? 0.59D + 0.5625D * (double) direction.getStepY() : (double) randomsource.nextFloat();
                double d3 = direction$axis == Direction.Axis.Z ? 0.5D + 0.5625D * (double) direction.getStepZ() : (double) randomsource.nextFloat();
                level.addParticle(ParticleTypes.FLASH, (double) pos.getX() + d1, (double) pos.getY() + d2 + 1.11D, (double) pos.getZ() + d3, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    public static void spawnCreatedCauldron(Level level, BlockPos pos) {

        for (int i = 0; i <= 10; i++) {
            level.addParticle(ModParticles.MAGIC_DUST_PARTICLES.get(), pos.getX() + (float)i/10, pos.getY() + 1d, pos.getZ() - 0.03, 0, -1, -1);
            level.addParticle(ModParticles.MAGIC_DUST_PARTICLES.get(), pos.getX() - 0.03, pos.getY() + 1d, pos.getZ() + (float)i/10, 0, -1, 1);
            level.addParticle(ModParticles.MAGIC_DUST_PARTICLES.get(), pos.getX() + (float)i/10, pos.getY() + 1d, pos.getZ() + 1.03, -1, -1, 0);
            level.addParticle(ModParticles.MAGIC_DUST_PARTICLES.get(), pos.getX() + 1.03, pos.getY() + 1d, pos.getZ() + (float)i/10, 1, -1, 0);
            level.addParticle(ModParticles.MAGIC_DUST_PARTICLES.get(), pos.getX() + 0.5d, pos.getY() + 1.5d, pos.getZ() + 0.5d, 0, -1, 0);
        }
    }

    public static void spawnCircle(Level level, BlockPos pos) {
        for (double angle = 0; angle < 2 * Math.PI; angle += Math.PI / 32) {
            double x = Math.cos(angle); // x-coordinate
            double y = Math.sin(angle); // y-coordinate

            // Spawn particle at position relative to the center (pos)
            level.addParticle(ModParticles.MAGIC_DUST_PARTICLES.get(),
                    pos.getX() + 0.5d + x,
                    pos.getY() + 1d,
                    pos.getZ() + 0.5d + y,
                    0, -1, 0);
        }
    }

    public static void spawnHeartParticles(Level level, BlockPos pos, int times) {
        double d0 = 0.5725D;
        RandomSource randomsource = level.random;

        for (int i = 0; i <= times; i++) {
            for(Direction direction : Direction.values()) {
                BlockPos blockpos = pos.relative(direction);
                if (!level.getBlockState(blockpos).isSolidRender(level, blockpos)) {
                    Direction.Axis direction$axis = direction.getAxis();
                    double d1 = direction$axis == Direction.Axis.X ? 0.5D + 0.5625D * (double)direction.getStepX() : (double)randomsource.nextFloat();
                    double d2 = direction$axis == Direction.Axis.Y ? 0.59D + 0.5625D * (double)direction.getStepY() : (double)randomsource.nextFloat();
                    double d3 = direction$axis == Direction.Axis.Z ? 0.5D + 0.5625D * (double)direction.getStepZ() : (double)randomsource.nextFloat();
                    level.addParticle(ParticleTypes.ENCHANTED_HIT, (double)pos.getX() + d1, (double)pos.getY() + d2 + 1.29D, (double)pos.getZ() + d3, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }
}
