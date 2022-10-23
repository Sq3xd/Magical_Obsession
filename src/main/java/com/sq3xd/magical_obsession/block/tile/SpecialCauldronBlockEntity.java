package com.sq3xd.magical_obsession.block.tile;

import com.sq3xd.magical_obsession.block.SpecialCauldronBlock;
import com.sq3xd.magical_obsession.init.ModBlockEntities;
import com.sq3xd.magical_obsession.tags.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.items.ItemStackHandler;

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
    private int maxAdvancedProgress = 790;

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
                return 6;
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

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemStackHandler.serializeNBT());
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) { // TODO FIX MODEL RENDERING FOR CLIENT SIDE
        super.load(nbt);
        itemStackHandler.deserializeNBT(nbt.getCompound("inventory"));
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

    public static void tick(Level level, BlockPos pos, BlockState state, SpecialCauldronBlockEntity entity){
        if (level.isClientSide){
            if (entity.itemStackHandler.getStackInSlot(0).is(ModTags.Items.SPECIAL_CAULDRON_CRAFT_LOW_ITEMS) && !entity.itemStackHandler.getStackInSlot(0).is(ItemStack.EMPTY.getItem())){
                entity.progress++;
                if (entity.progress >= entity.maxProgress){
                    entity.itemStackHandler.setStackInSlot(0, Items.DIAMOND.getDefaultInstance());
                    level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.END_PORTAL_SPAWN, SoundSource.BLOCKS, 1.0f, 1.0f, true);
                    entity.resetProgress();
                }
            } else {
                entity.resetProgress();
            }
        }

        if (!level.isClientSide){
            if (entity.itemStackHandler.getStackInSlot(0).is(ModTags.Items.SPECIAL_CAULDRON_CRAFT_LOW_ITEMS) && !entity.itemStackHandler.getStackInSlot(0).is(ItemStack.EMPTY.getItem())){
                entity.progress++;
                if (entity.progress >= entity.maxProgress){
                    entity.itemStackHandler.setStackInSlot(0, Items.DIAMOND.getDefaultInstance());
                    entity.resetProgress();
                }
            } else {
                entity.resetProgress();
            }
        }
        //System.out.println("YES");
        /*if (!level.isClientSide){
            System.out.println("SERVER SIDE - " + entity.itemStackHandler.getStackInSlot(0));
        }

        if (level.isClientSide){
            System.out.println("CLIENT SIDE - " + entity.itemStackHandler.getStackInSlot(0));
        }*/
        //entity.itemStackHandler.setStackInSlot(0, entity.itemStackHandler.getStackInSlot(0));
        /*ItemStack item = entity.itemStackHandler.getStackInSlot(0);

        if (!level.isClientSide){
            item = entity.itemStackHandler.getStackInSlot(0);
        }

        if (level.isClientSide){
            entity.itemStackHandler.setStackInSlot(0, item);
            System.out.println(item);
        }/*
        /*if (level.isClientSide){
            entity.itemStackHandler.setStackInSlot(0, entity.itemStackHandler.getStackInSlot(0));
        }*/
        /*if (!level.isClientSide){
            System.out.println("Yes " + entity.getCorruption());
            entity.setCorruption(1);
            if (entity.getCorruption() >= 300){
                state.setValue(BlockStateProperties.ENABLED, true);
            }
        }*/
    }

    /*public static void tick(Level level, BlockPos pos, BlockState state, SpecialCauldronBlockEntity entity){
        if(level.isClientSide()) {
            return;
        }

        if(hasRecipe(entity)) {
            this.progress++;
            setChanged(level, pos, state);

            if(entity.progress >= entity.maxProgress) {
                craftItem(entity);
            }
        } else {
            entity.resetProgress();
            setChanged(level, pos, state);
        }
    }

    private static void craftItem(SpecialCauldronBlockEntity entity) {

        if(hasRecipe(entity)) {
            entity.
            entity.resetProgress();
        }
    }

    private static boolean hasRecipe(SpecialCauldronBlockEntity entity) {
        SimpleContainer inventory = new SimpleContainer(entity.itemStackHandler.getSlots());
        for (int i = 0; i < entity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemStackHandler.getStackInSlot(i));
        }

        boolean hasItemInFirstSlot = entity.itemStackHandler.getStackInSlot(1).getItem() == ModItems.TIN_ORE_ITEM.get();

        return hasItemInFirstSlot && canInsertAmountIntoOutputSlot(inventory) &&
                canInsertItemIntoOutputSlot(inventory, new ItemStack(ModItems.TIN_INGOT.get(), 1));
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack stack) {
        return inventory.getItem(2).getItem() == stack.getItem() || inventory.getItem(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount();
    }*/
}
