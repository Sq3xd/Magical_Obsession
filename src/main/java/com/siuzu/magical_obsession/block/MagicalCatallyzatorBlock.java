package com.siuzu.magical_obsession.block;

import com.siuzu.magical_obsession.block.tile.MagicalCatallyzatorBlockEntity;
import com.siuzu.magical_obsession.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class MagicalCatallyzatorBlock extends Block implements EntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    private static final VoxelShape BASE = Block.box(7.0D, 0.0D, 7.0D, 9.0D, 12.0D, 9.0D);

    public MagicalCatallyzatorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH)); // FACING
    }

    // Direction

    public BlockState rotate(BlockState p_48722_, Rotation p_48723_) {
        return p_48722_.setValue(FACING, p_48723_.rotate(p_48722_.getValue(FACING)));
    }

    public BlockState mirror(BlockState p_48719_, Mirror p_48720_) {
        return p_48719_.rotate(p_48720_.getRotation(p_48719_.getValue(FACING)));
    }

    public BlockState getStateForPlacement(BlockPlaceContext p_48689_) {
        return this.defaultBlockState().setValue(FACING, p_48689_.getHorizontalDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_48725_) {
        p_48725_.add(FACING);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_60550_) {
        return RenderShape.MODEL;
    }

    // Reaction

    @Override
    public PushReaction getPistonPushReaction(BlockState p_60584_) {
        return PushReaction.DESTROY;
    }

    // Using

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (!level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof MagicalCatallyzatorBlockEntity entity) {
                if (entity.inventory.getStackInSlot(0).is(ItemStack.EMPTY.getItem()) && !player.getMainHandItem().is(ItemStack.EMPTY.getItem())) {
                    ItemStack item = player.getItemInHand(hand).copy();
                    item.setCount(1);
                    entity.inventory.setStackInSlot(0, item);
                    player.getMainHandItem().shrink(1);
                } else {
                    if (!entity.inventory.getStackInSlot(0).is(ItemStack.EMPTY.getItem())) {
                        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ItemStack.EMPTY.getItem())) {
                            player.setItemInHand(InteractionHand.MAIN_HAND, entity.inventory.getStackInSlot(0));
                        } else {
                            player.addItem(entity.inventory.getStackInSlot(0));
                        }
                        entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
                    }
                }
            }
        }

        // Interaction - Client Side
        if (level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof MagicalCatallyzatorBlockEntity entity) {
                if (entity.inventory.getStackInSlot(0).is(ItemStack.EMPTY.getItem()) && !player.getMainHandItem().is(ItemStack.EMPTY.getItem())) {
                    ItemStack item = player.getItemInHand(hand).copy();
                    item.setCount(1);
                    entity.inventory.setStackInSlot(0, item);
                } else {
                    if (!entity.inventory.getStackInSlot(0).is(ItemStack.EMPTY.getItem())) {
                        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ItemStack.EMPTY.getItem())) {
                            player.setItemInHand(InteractionHand.MAIN_HAND, entity.inventory.getStackInSlot(0));
                        } else {
                            player.addItem(entity.inventory.getStackInSlot(0));
                        }
                        entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
                    }
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    // Entity


    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if (!level.isClientSide()){
            if (level.getBlockEntity(pos) instanceof MagicalCatallyzatorBlockEntity entity){
                if (entity.getSphere() >= 12000) {
                    entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
                    level.explode(null, DamageSource.MAGIC, null, pos.getX(), pos.getY(), pos.getZ(), 9.5f, false, Explosion.BlockInteraction.DESTROY);
                }

                if (!entity.inventory.getStackInSlot(0).is(ItemStack.EMPTY.getItem())) {
                    if (entity.inventory.getStackInSlot(0).isStackable()){
                        ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY() + 1d, pos.getZ(), entity.inventory.getStackInSlot(0).getItem().getDefaultInstance());
                        level.addFreshEntity(itemEntity);
                    } else{
                        ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY() + 1d, pos.getZ(), entity.inventory.getStackInSlot(0));
                        level.addFreshEntity(itemEntity);
                    }
                }
                entity.setSphere(-entity.getSphere());
                entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
            }
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MagicalCatallyzatorBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.MAGICAL_CATALLYZATOR.get(),
                MagicalCatallyzatorBlockEntity::tick);
    }

    @javax.annotation.Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> p_152133_, BlockEntityType<E> p_152134_, BlockEntityTicker<? super E> p_152135_) {
        return p_152134_ == p_152133_ ? (BlockEntityTicker<A>)p_152135_ : null;
    }

    // Shape

    public VoxelShape getShape(BlockState p_151964_, BlockGetter p_151965_, BlockPos p_151966_, CollisionContext p_151967_) {
        return BASE;
    }
}