package com.siuzu.magical_obsession.block.tile;

import com.mojang.math.Vector3d;
import com.siuzu.magical_obsession.init.ModBlockEntities;
import com.siuzu.magical_obsession.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class MagicalFieldShieldBlockEntity extends BlockEntity {
    public boolean detected = false;
    public int[] cords = new int[]{0, 0, 0};

    public MagicalFieldShieldBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MAGICAL_FIELD_SHIELD.get(), pos, state);
    }

    // Cords

    public int getCords(int i){
        return cords[i];
    }

    public int setCords(int i, int j){
        return cords[i] = j;
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
    // Tick

    public static void tick(Level level, BlockPos pos, BlockState state, MagicalFieldShieldBlockEntity entity) {
        // Craft for client side
        if (level.isClientSide) {
            if (level.getBlockEntity(pos.west(entity.getCords(0))) instanceof MagicalCatallyzatorBlockEntity entity1) {
                if (entity1.getSphere() > 30) {
                    entity1.setSphere(-30);
                }
            }
        }

        if (!level.isClientSide) {
            if (level.getBlockEntity(pos.west(entity.getCords(0))) instanceof MagicalCatallyzatorBlockEntity entity1) {
                if (entity1.getSphere() > 30) {
                    entity1.setSphere(-30);
                }
            }
        }

        if (level.isClientSide) {
            if (level.getBlockEntity(pos.north(entity.getCords(2))) instanceof MagicalCatallyzatorBlockEntity entity1) {
                if (entity1.getSphere() > 30) {
                    entity1.setSphere(-30);
                }
            }
        }

        if (!level.isClientSide) {
            if (level.getBlockEntity(pos.north(entity.getCords(2))) instanceof MagicalCatallyzatorBlockEntity entity1) {
                if (entity1.getSphere() > 30) {
                    entity1.setSphere(-30);
                }
            }
        }

        if (!(entity.getLevel().getBlockEntity(pos.west(entity.getCords(0))) instanceof MagicalCatallyzatorBlockEntity) && !(entity.getLevel().getBlockEntity(pos.north(entity.getCords(2))) instanceof MagicalCatallyzatorBlockEntity)) {
            entity.setCords(0, 0);
            entity.setCords(1, 0);
            entity.setCords(2, 0);
            entity.detected = false;
        }

        if (!entity.detected) {
            for (int x = -6; x <= 6; x++){
                if (entity.getLevel().getBlockState(pos.west(1)).is(ModBlocks.MAGICAL_CATALLYZATOR.get()) || entity.getLevel().getBlockState(pos.west(1)).is(ModBlocks.MAGICAL_FIELD_SHIELD.get()) || entity.getLevel().getBlockState(pos.west(1)).is(Blocks.AIR)) {
                    if (entity.getLevel().getBlockState(pos.west(x)).is(ModBlocks.MAGICAL_CATALLYZATOR.get())) {
                        entity.setCords(0, x);
                        entity.detected = true;
                    }
                } else {
                    entity.setCords(0, 0);
                    entity.detected = false;
                }
            }
        }

        if (!entity.detected) {
            for (int z = -6; z <= 6; z++) {
                if (entity.getLevel().getBlockState(pos.north(1)).is(ModBlocks.MAGICAL_CATALLYZATOR.get()) || entity.getLevel().getBlockState(pos.north(1)).is(ModBlocks.MAGICAL_FIELD_SHIELD.get()) || entity.getLevel().getBlockState(pos.north(1)).is(Blocks.AIR)) {
                    if (entity.getLevel().getBlockState(pos.north(z)).is(ModBlocks.MAGICAL_CATALLYZATOR.get())) {
                        entity.setCords(2, z);
                        entity.detected = true;
                    }
                } else {
                    entity.setCords(2, 0);
                    entity.detected = false;
                }
            }
        }
    }
}
