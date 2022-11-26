package com.sq3xd.magical_obsession.block.tile;

import com.sq3xd.magical_obsession.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class EntityDuplicatorBlockEntity extends BlockEntity {
    private int progress = 0;
    private int maxProgress = 1120;

    public static Direction direction;

    public EntityDuplicatorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ENTITY_DUPLICATOR.get(), pos, state);
    }

    // NBT Tags

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.putInt("progress", this.progress);
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
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

    // Tick Managment

    public static void tick(Level level, BlockPos pos, BlockState state, EntityDuplicatorBlockEntity entity) {
        // Craft for client side
        if (level.isClientSide) {
            System.out.println("YES");
        }
    }

    // Other

    public int getProgress() {
        return progress;
    }
}