package com.sq3xd.magical_obsession.item.tab;

import com.sq3xd.magical_obsession.init.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModTabs {
    public static final CreativeModeTab MAGICAL_OBSESSION = new CreativeModeTab("magical_obsession") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.CRYSTAL_ITEM.get());
        }
    };

    public static final CreativeModeTab MAGICAL_OBSESSION_JARS = new CreativeModeTab("magical_obsession_jars") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.SOUL_JAR.get());
        }
    };
}
