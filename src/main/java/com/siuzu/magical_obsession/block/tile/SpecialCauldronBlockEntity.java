package com.siuzu.magical_obsession.block.tile;

import com.siuzu.magical_obsession.init.ModBlockEntities;
import com.siuzu.magical_obsession.util.ParticlesHelper;
import com.siuzu.magical_obsession.recipe.SpecialCauldronCampfireRecipe;
import com.siuzu.magical_obsession.recipe.SpecialCauldronRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

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
                    ParticlesHelper.cauldronParticles(level, pos);
                }

                if (entity.progress >= entity.maxProgress + new Random().nextInt(1, 59)) {
                    craftItem(entity, level);
                    ParticlesHelper.cauldronCrafted(level, pos);
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
                    ParticlesHelper.cauldronParticles(level, pos);
                }

                if (entity.progress >= entity.maxAdvancedProgress + new Random().nextInt(1, 59)) {
                    craftCampfireItem(entity, level);
                    ParticlesHelper.cauldronCrafted(level, pos);
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
}