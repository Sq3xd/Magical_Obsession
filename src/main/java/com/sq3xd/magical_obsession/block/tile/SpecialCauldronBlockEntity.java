package com.sq3xd.magical_obsession.block.tile;

import com.sq3xd.magical_obsession.init.ModBlockEntities;
import com.sq3xd.magical_obsession.recipe.SpecialCauldronCampfireRecipe;
import com.sq3xd.magical_obsession.recipe.SpecialCauldronRecipe;
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
import net.minecraftforge.items.ItemStackHandler;

import java.util.Optional;
import java.util.Random;

public class SpecialCauldronBlockEntity extends BlockEntity {
    public static Direction direction;
    protected final ContainerData data;

    public final ItemStackHandler itemStackHandler = new ItemStackHandler(1){
        @Override
        public void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private int sphere = 0;

    private int progress = 0;
    private int maxProgress = 320;
    private int maxAdvancedProgress = 590;

    // Initialisation

    public SpecialCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SPECIAL_CAULDRON.get(), pos, state);

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> SpecialCauldronBlockEntity.this.progress;
                    case 1 -> SpecialCauldronBlockEntity.this.maxProgress;
                    case 2 -> SpecialCauldronBlockEntity.this.maxAdvancedProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> SpecialCauldronBlockEntity.this.progress = value;
                    case 1 -> SpecialCauldronBlockEntity.this.maxProgress = value;
                    case 2 -> SpecialCauldronBlockEntity.this.maxAdvancedProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 5;
            }
        };
    }

    //

    public void setSphere(int plus) {
        sphere += plus;
    }

    public int getSphere() {
        return sphere;
    }

    public int getProgress() {
        return progress;
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemStackHandler.serializeNBT());
        nbt.putInt("progress", this.progress);
        nbt.putInt("sphere", this.sphere);
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemStackHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("progress");
        sphere = nbt.getInt("sphere");
    }

    // Packets

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }


    // Crafting

    private void resetProgress() {
        this.progress = 0;
    }

    // Tick
    public static void tick(Level level, BlockPos pos, BlockState state, SpecialCauldronBlockEntity entity) {
        // Craft for client side
        if (level.isClientSide) {
            if (!entity.itemStackHandler.getStackInSlot(0).is(ItemStack.EMPTY.getItem()) && hasRecipe(entity, level)) {
                entity.progress++;
                if (entity.progress == RandomSource.create().nextInt(entity.progress, entity.progress + 75)){
                    spawnParticles(level, pos);
                }

                entity.setSphere(1);
                if (level.getBlockState(pos.below()).is(Blocks.CAMPFIRE)){
                    entity.setSphere(2);
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
            if (!entity.itemStackHandler.getStackInSlot(0).is(ItemStack.EMPTY.getItem()) && hasCampfireRecipe(entity, level) && level.getBlockState(pos.below()).is(Blocks.CAMPFIRE)) {
                entity.progress++;
                if (entity.progress == RandomSource.create().nextInt(entity.progress, entity.progress + 75)){
                    spawnParticles(level, pos);
                }

                if (entity.progress >= entity.maxAdvancedProgress + new Random().nextInt(1, 59)) {
                    craftCampfireItem(entity, level);
                    crafted(level, pos);
                    entity.setSphere(270);
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
            if (!entity.itemStackHandler.getStackInSlot(0).is(ItemStack.EMPTY.getItem()) && hasRecipe(entity, level)) {
                entity.progress++;
                if (entity.progress >= entity.maxProgress + new Random().nextInt(1, 59)) {
                    craftItem(entity, level);
                    entity.setSphere(120);
                    if (level.getBlockState(pos.below()).is(Blocks.CAMPFIRE)){
                        entity.setSphere(320);
                    }
                    entity.resetProgress();
                }
            } else {
                if (!hasCampfireRecipe(entity, level))
                    entity.resetProgress();
            }
        }

         // Campfire Recipe
        if (!level.isClientSide) {
            if (!entity.itemStackHandler.getStackInSlot(0).is(ItemStack.EMPTY.getItem()) && hasCampfireRecipe(entity, level) && level.getBlockState(pos.below()).is(Blocks.CAMPFIRE)) {
                entity.progress++;
                if (entity.progress >= entity.maxAdvancedProgress + new Random().nextInt(1, 59)) {
                    craftCampfireItem(entity, level);
                    entity.setSphere(270);
                    entity.resetProgress();
                }
            } else {
                if (!hasRecipe(entity, level))
                    entity.resetProgress();
            }
        }

         // Explode
        if (!level.isClientSide) {
            if (entity.getSphere() >= 5100) {
                level.explode(null, DamageSource.MAGIC, null, pos.getX(), pos.getY(), pos.getZ(), 7.5f, false, Explosion.BlockInteraction.DESTROY);
            }
        }
    }

    // Craft
    private static void craftItem(SpecialCauldronBlockEntity entity, Level level) {
        SimpleContainer inventory = new SimpleContainer(entity.itemStackHandler.getStackInSlot(0));

        Optional<SpecialCauldronRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(SpecialCauldronRecipe.Type.INSTANCE, inventory, level);

        if (hasRecipe(entity, level))
            entity.itemStackHandler.setStackInSlot(0, new ItemStack(recipe.get().getResultItem().getItem()));
    }

    private static void craftCampfireItem(SpecialCauldronBlockEntity entity, Level level) {
        SimpleContainer inventory = new SimpleContainer(entity.itemStackHandler.getStackInSlot(0));

        Optional<SpecialCauldronCampfireRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(SpecialCauldronCampfireRecipe.Type.INSTANCE, inventory, level);

        if (hasCampfireRecipe(entity, level))
            entity.itemStackHandler.setStackInSlot(0, new ItemStack(recipe.get().getResultItem().getItem()));
    }

    private static boolean hasRecipe(SpecialCauldronBlockEntity entity, Level level) {
        SimpleContainer inventory = new SimpleContainer(entity.itemStackHandler.getStackInSlot(0));

        Optional<SpecialCauldronRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(SpecialCauldronRecipe.Type.INSTANCE, inventory, level);

        return recipe.isPresent();
    }

    private static boolean hasCampfireRecipe(SpecialCauldronBlockEntity entity, Level level) {
        SimpleContainer inventory = new SimpleContainer(entity.itemStackHandler.getStackInSlot(0));

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