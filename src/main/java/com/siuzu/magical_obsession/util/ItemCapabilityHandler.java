package com.siuzu.magical_obsession.util;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.ItemStackHandler;

public class ItemCapabilityHandler extends ItemStackHandler {
    public ItemCapabilityHandler(BlockEntity blockEntity, int size) {
        final ItemStackHandler itemStackHandler = new ItemStackHandler(size){
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                blockEntity.setChanged();
            }
        };
    }
}
