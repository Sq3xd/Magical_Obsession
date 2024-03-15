package com.siuzu.magical_obsession.block.tile;

import com.siuzu.magical_obsession.init.ModBlockEntities;
import com.siuzu.magical_obsession.util.ParticlesHelper;
import com.siuzu.magical_obsession.init.ModParticles;
import com.siuzu.magical_obsession.recipe.MagicalCauldronRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.Random;

public class MagicalCauldronBlockEntity extends AbstractCauldronBlockEntity {
    public static Direction direction;
    protected final ContainerData data;

    // Initialisation

    public MagicalCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MAGICAL_CAULDRON.get(), pos, state);

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> MagicalCauldronBlockEntity.super.progress;
                    case 1 -> MagicalCauldronBlockEntity.super.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> MagicalCauldronBlockEntity.super.progress = value;
                    case 1 -> MagicalCauldronBlockEntity.super.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
    }


    // Crafting

    public int getProgress() {
        return progress;
    }

    private void resetProgress() {
        this.progress = 0;
    }

    // Tick
    public static void tick(Level level, BlockPos pos, BlockState state, MagicalCauldronBlockEntity entity) {
        // Craft for client side
        if (level.isClientSide) {
            if (!entity.inventory.getStackInSlot(0).is(ItemStack.EMPTY.getItem()) && hasRecipe(entity, level)) {
                entity.progress++;
                if (entity.progress == RandomSource.create().nextInt(entity.progress, entity.progress + 5)){
                    level.addParticle(ModParticles.MAGIC_DUST_PARTICLES.get(), pos.getX()+0.5 + Math.cos(entity.getProgress()), pos.getY() + 1.75d, pos.getZ()+0.5 + Math.sin(entity.getProgress()), 0, 0, 0);
                }

                if (entity.progress >= entity.maxProgress + new Random().nextInt(1, 59)) {
                    craftItem(entity, level);
                    ParticlesHelper.cauldronCrafted(level, pos);
                    level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 1.0f, 1.0f, true);
                    entity.resetProgress();
                }
            } else
                entity.resetProgress();
        }

        // Craft for server side
        if (!level.isClientSide) {
            if (!entity.inventory.getStackInSlot(0).is(ItemStack.EMPTY.getItem()) && hasRecipe(entity, level)) {
                entity.progress++;

                if (entity.progress >= entity.maxProgress + new Random().nextInt(1, 59)) {
                    craftItem(entity, level);
                    entity.resetProgress();
                }
            } else
                entity.resetProgress();
        }
    }

    // Craft
    private static void craftItem(MagicalCauldronBlockEntity entity, Level level) {
        SimpleContainer inventory = new SimpleContainer(entity.inventory.getStackInSlot(0));

        Optional<MagicalCauldronRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(MagicalCauldronRecipe.Type.INSTANCE, inventory, level);

        if (hasRecipe(entity, level))
            entity.inventory.setStackInSlot(0, new ItemStack(recipe.get().getResultItem().getItem()));
    }

    private static boolean hasRecipe(MagicalCauldronBlockEntity entity, Level level) {
        SimpleContainer inventory = new SimpleContainer(entity.inventory.getStackInSlot(0));

        Optional<MagicalCauldronRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(MagicalCauldronRecipe.Type.INSTANCE, inventory, level);

        return recipe.isPresent();
    }
}