package com.sq3xd.magical_obsession.block.tile;

import com.sq3xd.magical_obsession.init.ModBlockEntities;
import com.sq3xd.magical_obsession.item.JarItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Random;

public class EntityDuplicatorBlockEntity extends BlockEntity {
    private int sphere = 0;

    private int progress = 0;
    private int maxProgress = 275;

    public static Direction direction;
    protected final ContainerData data;

    public final ItemStackHandler itemStackHandler = new ItemStackHandler(1){
        @Override
        public void onContentsChanged(int slot) {
            setChanged();
        }
    };

    public EntityDuplicatorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ENTITY_DUPLICATOR.get(), pos, state);

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> EntityDuplicatorBlockEntity.this.progress;
                    case 1 -> EntityDuplicatorBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> EntityDuplicatorBlockEntity.this.progress = value;
                    case 1 -> EntityDuplicatorBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 5;
            }
        };
    }

    // NBT Tags

    public void setSphere(int plus) {
        sphere += plus;
    }

    public int getSphere() {
        return sphere;
    }

    public int getProgress() {
        return progress;
    }

    private void resetProgress() {
        this.progress = 0;
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

    // Tick Managment

    public static void tick(Level level, BlockPos pos, BlockState state, EntityDuplicatorBlockEntity entity) {
        if (level.isClientSide) {
            if (!entity.itemStackHandler.getStackInSlot(0).is(ItemStack.EMPTY.getItem()) && entity.itemStackHandler.getStackInSlot(0).getItem() instanceof JarItem item) {
                entity.progress++;
                entity.setSphere(1);
                if (entity.progress == RandomSource.create().nextInt(entity.progress, entity.progress + 59)){
                    Random random = new Random();
                    level.playLocalSound(pos.getX() + random.nextDouble(-3d, 3d), pos.getY() + random.nextDouble(-3d, 3d), pos.getZ() + random.nextDouble(-3d, 3d), SoundEvents.ALLAY_AMBIENT_WITH_ITEM, SoundSource.BLOCKS, 1.5f, 0.9f, true);
                    spawnParticles(level, pos);
                }

                if (entity.progress >= entity.maxProgress + new Random().nextInt(1, 59)) {
                    level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.BLOCKS, 1.0f, 1.0f, true);
                    entity.itemStackHandler.setStackInSlot(0, ItemStack.EMPTY);
                    level.playLocalSound(entity.getBlockPos().getX(), entity.getBlockPos().getY(), entity.getBlockPos().getZ(), SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.WEATHER, 1.1f, 1.1f, true);
                    spawnSuperParticles(level, new BlockPos(entity.getBlockPos().getX() + 0.5f, entity.getBlockPos().getY() + 1.5f, entity.getBlockPos().getZ() + 0.5f));
                    entity.resetProgress();
                }
            } else
                entity.resetProgress();
        }

        // Craft for server side
        if (!level.isClientSide) {
            if (!entity.itemStackHandler.getStackInSlot(0).is(ItemStack.EMPTY.getItem()) && entity.itemStackHandler.getStackInSlot(0).getItem() instanceof JarItem item) {
                entity.progress++;
                entity.setSphere(1);
                if (entity.progress >= entity.maxProgress + new Random().nextInt(1, 59)) {
                    EntityType<?> entityType = item.getEntityType();
                    Entity livingEntity = entityType.create(level);
                    livingEntity.moveTo(entity.getBlockPos().getX() + 0.5f, entity.getBlockPos().getY() + 1.5f, entity.getBlockPos().getZ() + 0.5f);
                    livingEntity.addTag("respawned");
                    level.addFreshEntity(livingEntity);
                    entity.itemStackHandler.setStackInSlot(0, ItemStack.EMPTY);
                    entity.resetProgress();
                }
            } else
                entity.resetProgress();
        }

        // Explode
        if (!level.isClientSide) {
            if (entity.getSphere() >= 1500) {
                level.explode(null, DamageSource.MAGIC, null, pos.getX(), pos.getY(), pos.getZ(), 8.5f, false, Explosion.BlockInteraction.DESTROY);
            }
        }
    }

    public static void spawnParticles(Level level, BlockPos pos) {
        double d0 = 0.5725D;
        RandomSource randomsource = level.random;

        for(Direction direction : Direction.values()) {
            BlockPos blockpos = pos.relative(direction);
            if (!level.getBlockState(blockpos).isSolidRender(level, blockpos)) {
                Direction.Axis direction$axis = direction.getAxis();
                double d1 = direction$axis == Direction.Axis.X ? 0.59D + 0.57551D * (double)direction.getStepX() : (double)randomsource.nextFloat();
                double d2 = direction$axis == Direction.Axis.Y ? 0.55D + 0.57251D * (double)direction.getStepY() : (double)randomsource.nextFloat();
                double d3 = direction$axis == Direction.Axis.Z ? 0.5D + 0.55155D * (double)direction.getStepZ() : (double)randomsource.nextFloat();
                level.addParticle(ParticleTypes.DRAGON_BREATH, (double)pos.getX() + d1, (double)pos.getY() + d2 + 1.37D, (double)pos.getZ() + d3, 0.0D, 0.0D, 0.0D);
                level.addParticle(ParticleTypes.ENCHANT, (double)pos.getX() + d1, (double)pos.getY() + d2 + 1.32D, (double)pos.getZ() + d3, 0.0D, 0.0D, 0.0D);
                level.addParticle(ParticleTypes.SMOKE, (double)pos.getX() + d1, (double)pos.getY() + d2 + 1.29D, (double)pos.getZ() + d3, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    public static void spawnSuperParticles(Level level, BlockPos pos) {
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
