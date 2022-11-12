package com.sq3xd.magical_obsession.block.tile;

import com.sq3xd.magical_obsession.init.ModBlockEntities;
import com.sq3xd.magical_obsession.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class MagicPipeBlockEntity extends BlockEntity {
    public MagicPipeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MAGIC_PIPE.get(), pos, state);
    }

     // Tick
     public static void tick(Level level, BlockPos pos, BlockState state, MagicPipeBlockEntity entity) {
        if (level.isClientSide){
            if (level.getBlockState(pos.above()).is(ModBlocks.MAGIC_PIPE.get())){
                entity.getBlockState().setValue(BlockStateProperties.UP, true);
                state.setValue(BlockStateProperties.UP, true);
                System.out.println("YES");
            }
        }

        if (!level.isClientSide) {
            if (level.getBlockState(pos.above()).is(ModBlocks.MAGIC_PIPE.get())){
                entity.getBlockState().setValue(BlockStateProperties.UP, true);
            }
        }
     }
}
