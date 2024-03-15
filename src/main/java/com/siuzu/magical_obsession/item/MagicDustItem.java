package com.siuzu.magical_obsession.item;

import com.siuzu.magical_obsession.init.ModBlocks;
import com.siuzu.magical_obsession.init.ModSounds;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nullable;
import java.util.List;

import static com.siuzu.magical_obsession.util.ParticlesHelper.spawnCircle;
import static com.siuzu.magical_obsession.util.ParticlesHelper.spawnCreatedCauldron;

public class MagicDustItem extends Item {


    public MagicDustItem(Properties properties){
        super(properties);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        BlockPos pos = new BlockPos(entity.getPosition(1).x, entity.getPosition(1).y-0.1, entity.getPosition(1).z);
        Level level = entity.getLevel();
        entity.tickCount++;

        if (level.getBlockState(pos).equals(Blocks.CAULDRON.defaultBlockState())) {
            if (level.isClientSide) {
                if ((entity.tickCount & 7) == 0) {
                    spawnCircle(level, pos);
                }

                if (entity.tickCount >= 148) {
                    level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), ModSounds.MAGIC_DUST_ON_USE.get(), SoundSource.BLOCKS, 0.35f, 5f, true);
                    spawnCreatedCauldron(level, pos);
                }
            }

            if (entity.tickCount >= 150)
                createCauldron(pos, entity, level);
        }
        return super.onEntityItemUpdate(stack, entity);
    }

    public static void createCauldron(BlockPos pos, ItemEntity entity, Level level) {
        if (level.getBlockState(pos).equals(Blocks.CAULDRON.defaultBlockState())) {
            if (!level.isClientSide) {
                setBlock(level, pos);
            }

            entity.getItem().setCount(entity.getItem().getCount()-1);
        }
    }

    public static void setBlock(Level level, BlockPos pos) {
        level.setBlockAndUpdate(pos, ModBlocks.SPECIAL_CAULDRON.get().defaultBlockState());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level pLevel, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        if(Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.magical_obsession.magic_dust"));
        } else {
            tooltipComponents.add(Component.translatable("tooltip.magical_obsession.shift"));
        }
    }
}
