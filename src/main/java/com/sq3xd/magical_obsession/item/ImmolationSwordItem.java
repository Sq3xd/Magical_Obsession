package com.sq3xd.magical_obsession.item;

import com.sq3xd.magical_obsession.init.ModItems;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class ImmolationSwordItem extends SwordItem implements BakedModel {
    public ImmolationSwordItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, damage, speed, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity player) {
        Entity entityGet = (Entity) entity;
        EntityType<?> entityType = entityGet.getType();
        String finalEntity = entityType.toString().replaceFirst("entity.", "").replace('.', '_');
        String finalText = "give " + player.getDisplayName().getString().toString() + " magical_obsession:soul_jar_" + finalEntity;
        Level level = player.getLevel();

        spawnParticles(level, new BlockPos(entity.getX(), entity.getY(), entity.getZ()));
        spawnParticles(level, new BlockPos(entity.getX(), entity.getY(), entity.getZ()));
        player.setHealth(player.getHealth() + 1);

        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.SOUL_JAR.get())) {
            if (!entity.getTags().contains("respawned")){
                if (entity.getHealth() <= 0 && entity.getMaxHealth() <= 50) {
                    player.getServer().getCommands().performPrefixedCommand(
                            new CommandSourceStack(CommandSource.NULL, player.getPosition(1), null, player.getServer().overworld()
                                    , 2, player.getDisplayName().getString(), player.getDisplayName(), player.getServer(), null), finalText);
                    player.getItemInHand(InteractionHand.OFF_HAND).shrink(1);
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

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState p_235039_, @Nullable Direction p_235040_, RandomSource p_235041_) {
        return null;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return true;
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    public boolean isCustomRenderer() {
        return true;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return null;
    }

    @Override
    public ItemOverrides getOverrides() {
        return null;
    }
}
