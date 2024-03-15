package com.siuzu.magical_obsession.item;

import com.siuzu.magical_obsession.init.ModItems;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ImmolationSwordItem extends SwordItem {
    public ImmolationSwordItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, damage, speed, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity player) {
        Level level = player.getLevel();

        spawnParticles(level, new BlockPos(entity.getX(), entity.getY(), entity.getZ()));
        spawnParticles(level, new BlockPos(entity.getX(), entity.getY(), entity.getZ()));
        player.setHealth(player.getHealth() + 1);

        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.MOB_SOUL.get())) {
            ItemStack jar = player.getItemInHand(InteractionHand.OFF_HAND);
            System.out.println("yes5");
            if (jar.getTag() != null && !jar.getTag().contains("id")) {

            } else {
                if (!entity.getTags().contains("respawned")) {
                    if (entity.getHealth() <= 0 && entity.getMaxHealth() <= 50) {
                        CompoundTag nbt = new CompoundTag();
                        nbt.putString("id", ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString());
                        player.getItemInHand(InteractionHand.OFF_HAND).setTag(nbt);
                        System.out.println("yes");
                    }
                }
            }
        } else if (entity.getHealth() <= 0) {
            if (!player.getLevel().isClientSide) {
                if (!player.getTags().contains("get_immolation_sword_tip")){
                    player.sendSystemMessage(Component.translatable("message.magical_obsession.player.immolation_sword"));
                    player.addTag("get_immolation_sword_tip");
                }
            }
        }

        return super.hurtEnemy(stack, entity, player);
    }

    public static void spawnParticles(Level level, BlockPos pos) {
        double d0 = 0.5725D;
        RandomSource randomsource = level.random;

        for(Direction direction : Direction.values()) {
            BlockPos blockpos = pos.relative(direction);
            if (!level.getBlockState(blockpos).isSolidRender(level, blockpos)) {
                Direction.Axis direction$axis = direction.getAxis();
                double d1 = direction$axis == Direction.Axis.X ? 0.5D + 0.5625D * (double)direction.getStepX() : (double)randomsource.nextFloat();
                double d2 = direction$axis == Direction.Axis.Y ? 0.59D + 0.5625D * (double)direction.getStepY() : (double)randomsource.nextFloat();
                double d3 = direction$axis == Direction.Axis.Z ? 0.5D + 0.5625D * (double)direction.getStepZ() : (double)randomsource.nextFloat();
                level.addParticle(ParticleTypes.ENCHANTED_HIT, (double)pos.getX() + d1, (double)pos.getY() + d2 + 1.29D, (double)pos.getZ() + d3, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
