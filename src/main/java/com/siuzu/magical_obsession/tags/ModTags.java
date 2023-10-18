package com.siuzu.magical_obsession.tags;

import com.siuzu.magical_obsession.MagicalObsession;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> SPECIAL_CAULDRON_EXPLODES
                = BlockTags.create(new ResourceLocation(MagicalObsession.MOD_ID, "special_cauldron_explodes"));
    }

    public static class Items {
        public static final TagKey<Item> SPECIAL_CAULDRON_DANGEROUS_ITEMS
                = ItemTags.create(new ResourceLocation(MagicalObsession.MOD_ID, "special_cauldron_dangerous_items"));
    }
}
