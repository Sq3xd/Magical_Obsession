package com.siuzu.magical_obsession.item;

import com.siuzu.magical_obsession.block.tile.MagicalPentagramBlockEntity;
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
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.siuzu.magical_obsession.util.ParticlesHelper.spawnHeartParticles;

public class ImmolationSwordItem extends SwordItem {
    public ImmolationSwordItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, damage, speed, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity player) {
        Level level = player.getLevel();
        RandomSource rs = level.random;

        spawnHeartParticles(level, new BlockPos(entity.getX(), entity.getY(), entity.getZ()), rs.nextInt(3));
        player.setHealth(player.getHealth() + 1);

        if (entity.getHealth() <= 0) {
            if (!player.getLevel().isClientSide) {
                if (!player.getTags().contains("get_immolation_sword_tip")){
                    player.sendSystemMessage(Component.translatable("message.magical_obsession.player.immolation_sword"));
                    player.addTag("get_immolation_sword_tip");
                }
            }
        }

        if (!entity.getTags().contains("respawned")) {
            if (entity.getHealth() <= 0 && entity.getMaxHealth() <= 50) {
                if (level.getBlockEntity(new BlockPos(entity.getX(), entity.getY(), entity.getZ())) instanceof MagicalPentagramBlockEntity blockEntity) {
                    ItemEntity mob_soul = new ItemEntity(level, entity.getX(), entity.getY(), entity.getZ(), ModItems.MOB_SOUL.get().getDefaultInstance());

                    CompoundTag nbt = new CompoundTag();
                    nbt.putString("id", ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString());


                    mob_soul.getItem().setTag(nbt);
                    level.addFreshEntity(mob_soul);
                }
            }
        }

        return super.hurtEnemy(stack, entity, player);
    }
}
