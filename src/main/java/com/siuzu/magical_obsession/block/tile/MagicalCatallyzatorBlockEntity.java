package com.siuzu.magical_obsession.block.tile;

import com.siuzu.magical_obsession.MagicalObsession;
import com.siuzu.magical_obsession.init.ModBlockEntities;
import com.siuzu.magical_obsession.mixin.ParticlesMixin;
import com.siuzu.magical_obsession.recipe.MagicalCatallyzatorRecipe;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Random;

public class MagicalCatallyzatorBlockEntity extends BlockEntity {
    public static Direction direction;
    protected final ContainerData data;

    public final ItemStackHandler inventory = new ItemCapabilityHandler(MagicalCatallyzatorBlockEntity.this, 1);
    private final LazyOptional<ItemStackHandler> optional = LazyOptional.of(() -> this.inventory);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return cap == ForgeCapabilities.ITEM_HANDLER ? this.optional.cast() : super.getCapability(cap);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.optional.invalidate();
    }

    private int sphere = 0;

    private int progress = 0;
    private int maxProgress = 1120;

    // Initialisation

    public MagicalCatallyzatorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MAGICAL_CATALLYZATOR.get(), pos, state);

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> MagicalCatallyzatorBlockEntity.this.progress;
                    case 1 -> MagicalCatallyzatorBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> MagicalCatallyzatorBlockEntity.this.progress = value;
                    case 1 -> MagicalCatallyzatorBlockEntity.this.maxProgress = value;
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
        super.saveAdditional(nbt);
        nbt.putInt("progress", this.progress);
        var modData = new CompoundTag();
        modData.put("inventory", this.inventory.serializeNBT());
        nbt.put(MagicalObsession.MOD_ID, modData);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        CompoundTag modData = nbt.getCompound(MagicalObsession.MOD_ID);
        this.inventory.deserializeNBT(modData.getCompound("inventory"));
        progress = nbt.getInt("progress");
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

    @Override
    public AABB getRenderBoundingBox() {
        BlockPos pos = this.getBlockPos();
        Vec3 d = new Vec3(pos.getX(), pos.getY(), pos.getZ());
        return AABB.ofSize(d, 16, 16, 16);
    }

    // Crafting

    private void resetProgress() {
        this.progress = 0;
    }

    // Tick
    public static void tick(Level level, BlockPos pos, BlockState state, MagicalCatallyzatorBlockEntity entity) {
        // Craft for client side
        if (level.isClientSide) {
            int sphere = getSphereJson(level, entity);
            int time = getTimeJson(level, entity);

            if (!entity.inventory.getStackInSlot(0).is(ItemStack.EMPTY.getItem()) && hasRecipe(entity, level)) {
                entity.progress++;
                entity.setSphere(sphere);
                if (entity.progress == RandomSource.create().nextInt(entity.progress, entity.progress + 50)){
                    Random random = new Random();
                    level.playLocalSound(pos.getX() + random.nextDouble(-3d, 3d), pos.getY() + random.nextDouble(-3d, 3d), pos.getZ() + random.nextDouble(-3d, 3d), SoundEvents.VEX_CHARGE, SoundSource.BLOCKS, 1.5f, 0.9f, true);
                    level.playLocalSound(pos.getX() + random.nextDouble(-3d, 3d), pos.getY() + random.nextDouble(-3d, 3d), pos.getZ() + random.nextDouble(-3d, 3d), SoundEvents.VEX_CHARGE, SoundSource.BLOCKS, 1.3f, 0.7f, true);
                    level.playLocalSound(pos.getX() + random.nextDouble(-3d, 3d), pos.getY() + random.nextDouble(-3d, 3d), pos.getZ() + random.nextDouble(-3d, 3d), SoundEvents.VEX_CHARGE, SoundSource.BLOCKS, 1.2f, 0.5f, true);
                    level.playLocalSound(pos.getX() + random.nextDouble(-3d, 3d), pos.getY() + random.nextDouble(-3d, 3d), pos.getZ() + random.nextDouble(-3d, 3d), SoundEvents.VEX_CHARGE, SoundSource.BLOCKS, 1.1f, 0.4f, true);
                    level.playLocalSound(pos.getX() + random.nextDouble(-3d, 3d), pos.getY() + random.nextDouble(-3d, 3d), pos.getZ() + random.nextDouble(-3d, 3d), SoundEvents.VEX_CHARGE, SoundSource.BLOCKS, 1.0f, 0.3f, true);
                    ParticlesMixin.cauldronParticles(level, pos);
                }

                if (entity.progress >= time + new Random().nextInt(1, 32)) {
                    craftItem(entity, level);
                    ParticlesMixin.cauldronCrafted(level, pos);
                    level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.BLOCKS, 1.0f, 1.0f, true);
                    entity.resetProgress();
                }
            } else
                entity.resetProgress();
        }

        // Craft for server side
        if (!level.isClientSide) {
            int sphere = getSphereJson(level, entity);
            int time = getTimeJson(level, entity);

            if (!entity.inventory.getStackInSlot(0).is(ItemStack.EMPTY.getItem()) && hasRecipe(entity, level)) {
                entity.progress++;
                entity.setSphere(sphere);
                if (entity.progress >= time + new Random().nextInt(1, 32)) {
                    craftItem(entity, level);
                    entity.resetProgress();
                }
            } else
                entity.resetProgress();
        }

         // Explode
        if (!level.isClientSide) {
            if (entity.getSphere() >= 17500) {
                level.explode(null, DamageSource.MAGIC, null, pos.getX(), pos.getY(), pos.getZ(), 8.5f, false, Explosion.BlockInteraction.DESTROY);
            }
        }
    }

    private static int getSphereJson(Level level, MagicalCatallyzatorBlockEntity entity) {
        SimpleContainer inventory = new SimpleContainer(entity.inventory.getStackInSlot(0));

        Optional<MagicalCatallyzatorRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(MagicalCatallyzatorRecipe.Type.INSTANCE, inventory, level);

        if (recipe.isPresent()) {
            int sphere = recipe.get().getSphere();
            return sphere;
        } else {
            int sphere = 1;
            return sphere;
        }
    }

    private static int getTimeJson(Level level, MagicalCatallyzatorBlockEntity entity) {
        SimpleContainer inventory = new SimpleContainer(entity.inventory.getStackInSlot(0));

        Optional<MagicalCatallyzatorRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(MagicalCatallyzatorRecipe.Type.INSTANCE, inventory, level);

        if (recipe.isPresent()) {
            int time = recipe.get().getTime();
            return time;
        } else {
            int time = 150;
            return time;
        }
    }

    // Craft
    private static void craftItem(MagicalCatallyzatorBlockEntity entity, Level level) {
        SimpleContainer inventory = new SimpleContainer(entity.inventory.getStackInSlot(0));

        Optional<MagicalCatallyzatorRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(MagicalCatallyzatorRecipe.Type.INSTANCE, inventory, level);

        if (hasRecipe(entity, level))
            entity.inventory.setStackInSlot(0, new ItemStack(recipe.get().getResultItem().getItem()));
    }

    private static boolean hasRecipe(MagicalCatallyzatorBlockEntity entity, Level level) {
        SimpleContainer inventory = new SimpleContainer(entity.inventory.getStackInSlot(0));

        Optional<MagicalCatallyzatorRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(MagicalCatallyzatorRecipe.Type.INSTANCE, inventory, level);

        return recipe.isPresent();
    }
}