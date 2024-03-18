package com.siuzu.magical_obsession.init;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModTiers {
    public static final ForgeTier IMMOLATION = new ForgeTier(1, 480, 0f,
            4f, 11, BlockTags.NEEDS_STONE_TOOL, () ->
            Ingredient.of(ModItems.LAPIS_NUGGET.get()));
}
