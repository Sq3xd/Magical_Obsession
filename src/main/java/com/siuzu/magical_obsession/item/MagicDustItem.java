package com.siuzu.magical_obsession.item;

import com.siuzu.magical_obsession.init.ModBlocks;
import com.siuzu.magical_obsession.particle.ModParticles;
import com.siuzu.magical_obsession.sound.ModSounds;
import net.minecraft.advancements.critereon.UsedEnderEyeTrigger;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static com.siuzu.magical_obsession.mixin.ParticlesMixin.spawnCircle;
import static com.siuzu.magical_obsession.mixin.ParticlesMixin.spawnCreatedCauldron;

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
