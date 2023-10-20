package com.siuzu.magical_obsession.block.tile;

import com.siuzu.magical_obsession.init.ModBlockEntities;
import com.siuzu.magical_obsession.recipe.SpecialCauldronCampfireRecipe;
import com.siuzu.magical_obsession.recipe.SpecialCauldronRecipe;
import com.siuzu.magical_obsession.util.ItemCapabilityHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Random;

public class SpecialCauldronBlockEntity extends AbstractCauldronBlockEntity {
    public static Direction direction;
    protected final ContainerData data;

    // Initialisation

    public SpecialCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SPECIAL_CAULDRON.get(), pos, state);

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> SpecialCauldronBlockEntity.super.progress;
                    case 1 -> SpecialCauldronBlockEntity.super.maxProgress;
                    case 2 -> SpecialCauldronBlockEntity.super.maxAdvancedProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> SpecialCauldronBlockEntity.super.progress = value;
                    case 1 -> SpecialCauldronBlockEntity.super.maxProgress = value;
                    case 2 -> SpecialCauldronBlockEntity.super.maxAdvancedProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
    }

    //

    public int getProgress() {
        return progress;
    }

    // Crafting
    private void resetProgress() {
        this.progress = 0;
    }

    // Tick
    public static void tick(Level level, BlockPos pos, BlockState state, SpecialCauldronBlockEntity entity) {
        // Craft for client side
        if (level.isClientSide) {
            if (!entity.inventory.getStackInSlot(0).is(ItemStack.EMPTY.getItem()) && hasRecipe(entity, level)) {
                entity.progress++;
                if (entity.progress == RandomSource.create().nextInt(entity.progress, entity.progress + 75)){
                    spawnParticles(level, pos);
                }

                if (entity.progress >= entity.maxProgress + new Random().nextInt(1, 59)) {
                    craftItem(entity, level);
                    crafted(level, pos);
                    level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 1.0f, 1.0f, true);
                    entity.resetProgress();
                }
            } else {
                if (!hasCampfireRecipe(entity, level))
                    entity.resetProgress();
            }
        }

        // Campfire Recipe
        if (level.isClientSide) {
            if (!entity.inventory.getStackInSlot(0).is(ItemStack.EMPTY.getItem()) && hasCampfireRecipe(entity, level) && level.getBlockState(pos.below()).is(Blocks.CAMPFIRE)) {
                entity.progress++;
                if (entity.progress == RandomSource.create().nextInt(entity.progress, entity.progress + 75)){
                    spawnParticles(level, pos);
                }

                if (entity.progress >= entity.maxAdvancedProgress + new Random().nextInt(1, 59)) {
                    craftCampfireItem(entity, level);
                    crafted(level, pos);
                    level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 1.0f, 1.0f, true);
                    entity.resetProgress();
                }
            } else {
                if (!hasRecipe(entity, level))
                    entity.resetProgress();
            }
        }

        // Craft for server side
        if (!level.isClientSide) {
            if (!entity.inventory.getStackInSlot(0).is(ItemStack.EMPTY.getItem()) && hasRecipe(entity, level)) {
                entity.progress++;
                if (entity.progress >= entity.maxProgress + new Random().nextInt(1, 59)) {
                    craftItem(entity, level);
                    entity.resetProgress();
                }
            } else {
                if (!hasCampfireRecipe(entity, level))
                    entity.resetProgress();
            }
        }

         // Campfire Recipe
        if (!level.isClientSide) {
            if (!entity.inventory.getStackInSlot(0).is(ItemStack.EMPTY.getItem()) && hasCampfireRecipe(entity, level) && level.getBlockState(pos.below()).is(Blocks.CAMPFIRE)) {
                entity.progress++;
                if (entity.progress >= entity.maxAdvancedProgress + new Random().nextInt(1, 59)) {
                    craftCampfireItem(entity, level);
                    entity.resetProgress();
                }
            } else {
                if (!hasRecipe(entity, level))
                    entity.resetProgress();
            }
        }
    }

    // Craft
    private static void craftItem(SpecialCauldronBlockEntity entity, Level level) {
        SimpleContainer inventory = new SimpleContainer(entity.inventory.getStackInSlot(0));

        Optional<SpecialCauldronRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(SpecialCauldronRecipe.Type.INSTANCE, inventory, level);

        if (hasRecipe(entity, level))
            entity.inventory.setStackInSlot(0, new ItemStack(recipe.get().getResultItem().getItem()));
    }

    private static void craftCampfireItem(SpecialCauldronBlockEntity entity, Level level) {
        SimpleContainer inventory = new SimpleContainer(entity.inventory.getStackInSlot(0));

        Optional<SpecialCauldronCampfireRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(SpecialCauldronCampfireRecipe.Type.INSTANCE, inventory, level);

        if (hasCampfireRecipe(entity, level))
            entity.inventory.setStackInSlot(0, new ItemStack(recipe.get().getResultItem().getItem()));
    }

    private static boolean hasRecipe(SpecialCauldronBlockEntity entity, Level level) {
        SimpleContainer inventory = new SimpleContainer(entity.inventory.getStackInSlot(0));

        Optional<SpecialCauldronRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(SpecialCauldronRecipe.Type.INSTANCE, inventory, level);

        return recipe.isPresent();
    }

    private static boolean hasCampfireRecipe(SpecialCauldronBlockEntity entity, Level level) {
        SimpleContainer inventory = new SimpleContainer(entity.inventory.getStackInSlot(0));

        Optional<SpecialCauldronCampfireRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(SpecialCauldronCampfireRecipe.Type.INSTANCE, inventory, level);

        return recipe.isPresent();
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
                level.addParticle(ParticleTypes.SMOKE, (double)pos.getX() + d1, (double)pos.getY() + d2 + 1.29D, (double)pos.getZ() + d3, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    public static void crafted(Level level, BlockPos pos) {
        double d0 = 0.5725D;
        RandomSource randomsource = level.random;

        for(Direction direction : Direction.values()) {
            BlockPos blockpos = pos.relative(direction);
            if (!level.getBlockState(blockpos).isSolidRender(level, blockpos)) {
                Direction.Axis direction$axis = direction.getAxis();
                double d1 = direction$axis == Direction.Axis.X ? 0.512D + 0.5725D * (double)direction.getStepX() : (double)randomsource.nextFloat();
                double d2 = direction$axis == Direction.Axis.Y ? 0.592D + 0.5725D * (double)direction.getStepY() : (double)randomsource.nextFloat();
                double d3 = direction$axis == Direction.Axis.Z ? 0.512D + 0.56725D * (double)direction.getStepZ() : (double)randomsource.nextFloat();
                level.addParticle(ParticleTypes.DRAGON_BREATH, (double)pos.getX() + d1, (double)pos.getY() + d2 + 1.27D, (double)pos.getZ() + d3, 0.011D, 0.011D, 0.011D);
            }
        }
    }
}