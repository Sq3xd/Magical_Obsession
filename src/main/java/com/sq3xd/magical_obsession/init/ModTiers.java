package com.sq3xd.magical_obsession.init;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModTiers {
    public static final ForgeTier IMMOLATION = new ForgeTier(1, 170, 0f,
            2.5f, 11, BlockTags.NEEDS_STONE_TOOL, () ->
            Ingredient.of(ModItems.LAPIS_NUGGET.get()));
}
