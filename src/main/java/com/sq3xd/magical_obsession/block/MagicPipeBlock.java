package com.sq3xd.magical_obsession.block;

import com.sq3xd.magical_obsession.block.tile.MagicPipeBlockEntity;
import com.sq3xd.magical_obsession.block.tile.MagicalCauldronBlockEntity;
import com.sq3xd.magical_obsession.block.tile.SpecialCauldronBlockEntity;
import com.sq3xd.magical_obsession.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

public class MagicPipeBlock extends Block implements EntityBlock {
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;

    public MagicPipeBlock(Properties properties){
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(UP, false));
        this.registerDefaultState(this.stateDefinition.any().setValue(DOWN, false));
        this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, false));
        this.registerDefaultState(this.stateDefinition.any().setValue(SOUTH, false));
        this.registerDefaultState(this.stateDefinition.any().setValue(EAST, false));
        this.registerDefaultState(this.stateDefinition.any().setValue(WEST, false));
    }

    public BlockState getStateForPlacement(BlockPlaceContext p_48689_) {
        return this.defaultBlockState().setValue(UP, false).setValue(DOWN, false).setValue(NORTH, false).setValue(SOUTH, false).setValue(EAST, false).setValue(WEST, false);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_48725_) {
        p_48725_.add(UP, DOWN, NORTH, SOUTH, EAST, WEST);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_60550_) {
        return RenderShape.MODEL;
    }

    // ENTITY

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MagicPipeBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.MAGIC_PIPE.get(),
                MagicPipeBlockEntity::tick);
    }

    @javax.annotation.Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> p_152133_, BlockEntityType<E> p_152134_, BlockEntityTicker<? super E> p_152135_) {
        return p_152134_ == p_152133_ ? (BlockEntityTicker<A>)p_152135_ : null;
    }
}
