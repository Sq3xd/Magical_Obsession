package com.siuzu.magical_obsession.init;

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
}
