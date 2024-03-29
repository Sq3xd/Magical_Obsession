package com.siuzu.magical_obsession.block;

import com.siuzu.magical_obsession.block.tile.AbstractCauldronBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;

public abstract class AbstractBaseBlock extends Block {
    public AbstractBaseBlock(Properties properties) {
        super(properties);
    }

    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if (!level.isClientSide()){
            if (level.getBlockEntity(pos) instanceof AbstractCauldronBlockEntity entity){
                if (!entity.inventory.getStackInSlot(0).is(ItemStack.EMPTY.getItem())) {
                    if (entity.inventory.getStackInSlot(0).isStackable()){
                        ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY() + 1d, pos.getZ(), entity.inventory.getStackInSlot(0).getItem().getDefaultInstance());
                        level.addFreshEntity(itemEntity);
                    } else{
                        ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY() + 1d, pos.getZ(), entity.inventory.getStackInSlot(0));
                        level.addFreshEntity(itemEntity);
                    }
                }
                entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
            }
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {

        // Interaction - Server Side
        if (!level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof AbstractCauldronBlockEntity entity) {
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
            if (level.getBlockEntity(pos) instanceof AbstractCauldronBlockEntity entity) {
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
                        level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.75f, 1f, true);
                        entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
                    }
                }
            }
        }
        return InteractionResult.SUCCESS;
    }
}
