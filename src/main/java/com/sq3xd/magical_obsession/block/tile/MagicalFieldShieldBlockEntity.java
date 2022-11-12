package com.sq3xd.magical_obsession.block.tile;

import com.sq3xd.magical_obsession.init.ModBlockEntities;
import com.sq3xd.magical_obsession.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class MagicalFieldShieldBlockEntity extends BlockEntity {
    public static float[] cords = new float[3];

    public MagicalFieldShieldBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MAGICAL_FIELD_SHIELD.get(), pos, state);
        for (float i : cords) {
            cords[(int) i] = 0;
        }
    }

    // Cords

    public int getCords(int i){
        return (int) cords[i];
    }

    public int setCords(int i, int j){
        return (int) (cords[i] = j);
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

    // Tick

    public static void tick(Level level, BlockPos pos, BlockState state, MagicalFieldShieldBlockEntity entity) {
        // Craft for client side
        for (int x = -6; x <= 6; x++){ // Todo delete cords when theres no catallyzator in the area
            if (entity.getLevel().getBlockState(pos.west(x)).is(ModBlocks.MAGICAL_CATALLYZATOR.get()) || entity.getLevel().getBlockState(pos.west(x)).is(ModBlocks.MAGICAL_FIELD_SHIELD.get()) || entity.getLevel().getBlockState(pos.west(x)).is(Blocks.AIR)) {
                if (entity.getLevel().getBlockState(pos.west(x)).is(ModBlocks.MAGICAL_CATALLYZATOR.get()) && entity.getLevel().getBlockState(pos.west(x).east(1)).is(Blocks.AIR)) {
                    entity.setCords(0, x);
                }
            } else {
                entity.setCords(0, 0);
            }
        }

        for (int z = -6; z <= 6*6; z++){
            if (entity.getLevel().getBlockState(pos.north(z)).is(ModBlocks.MAGICAL_CATALLYZATOR.get()) || entity.getLevel().getBlockState(pos.north(z)).is(ModBlocks.MAGICAL_FIELD_SHIELD.get()) || entity.getLevel().getBlockState(pos.north(z)).is(Blocks.AIR)) {
                if (entity.getLevel().getBlockState(pos.north(z)).is(ModBlocks.MAGICAL_CATALLYZATOR.get()) && entity.getLevel().getBlockState(pos.north(z).south(1)).is(Blocks.AIR)) {
                    entity.setCords(2, z);
                }
            } else {
                entity.setCords(2, 0);
            }
        }

        for (float i : cords) {
            System.out.println(i);
        }
    }
}
