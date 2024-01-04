package com.siuzu.magical_obsession.block.tile;

import com.siuzu.magical_obsession.block.MagicalCauldronBlock;
import com.siuzu.magical_obsession.init.ModBlockEntities;
import com.siuzu.magical_obsession.mixin.ParticlesMixin;
import com.siuzu.magical_obsession.particle.ModParticles;
import com.siuzu.magical_obsession.recipe.MagicalCauldronRecipe;
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
                    ParticlesMixin.cauldronCrafted(level, pos);
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