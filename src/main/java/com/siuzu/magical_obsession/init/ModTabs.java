package com.siuzu.magical_obsession.init;

import com.siuzu.magical_obsession.init.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModTabs {
    public static final CreativeModeTab MAGICAL_OBSESSION = new CreativeModeTab("magical_obsession") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.CRYSTAL_ITEM.get());
        }
    };

    public static final CreativeModeTab MAGICAL_OBSESSION_SOULS = new CreativeModeTab("magical_obsession_souls") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.MOB_SOUL.get());
        }
    };
}
