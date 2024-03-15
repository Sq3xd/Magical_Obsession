package com.siuzu.magical_obsession.block.tile;

import com.siuzu.magical_obsession.init.ModBlockEntities;
import com.siuzu.magical_obsession.item.MobSoulItem;
import com.siuzu.magical_obsession.util.ParticlesHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityDuplicatorBlockEntity extends AbstractCauldronBlockEntity {
    public static Direction direction;
    protected final ContainerData data;

    public EntityDuplicatorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ENTITY_DUPLICATOR.get(), pos, state);

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> EntityDuplicatorBlockEntity.this.progress;
                    case 1 -> EntityDuplicatorBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> EntityDuplicatorBlockEntity.this.progress = value;
                    case 1 -> EntityDuplicatorBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
    }

    // NBT Tags
    public int getProgress() {
        return progress;
    }
    private void resetProgress() {
        this.progress = 0;
    }



    // Tick Managment

    public static void tick(Level level, BlockPos pos, BlockState state, EntityDuplicatorBlockEntity entity) {
        RandomSource random = RandomSource.create();

        if (level.isClientSide) {
            if (!entity.inventory.getStackInSlot(0).is(ItemStack.EMPTY.getItem()) && entity.inventory.getStackInSlot(0).getItem() instanceof MobSoulItem item && !(entity.inventory.getStackInSlot(0).getTag() == null)) {
                ItemStack jar = entity.inventory.getStackInSlot(0).copy();
                if (jar.getTag() != null) {
                    ResourceLocation rl = new ResourceLocation(jar.getTag().getString("id"));

                    EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(rl);
                    LivingEntity livingEntity = (LivingEntity) entityType.create(level);

                    if (livingEntity.getSoundSource().equals(SoundSource.HOSTILE) && level.getDifficulty().equals(Difficulty.PEACEFUL)) {

                    } else {
                        entity.progress++;
                        if (entity.progress == random.nextInt(entity.progress, entity.progress + 50)){
                            level.playLocalSound(pos.getX() + random.nextInt(-3, 3), pos.getY() + random.nextInt(-3, 3), pos.getZ() + random.nextInt(-3, 3),
                                    SoundEvents.ALLAY_AMBIENT_WITH_ITEM, SoundSource.BLOCKS, 1.5f, 0.9f, true);
                            ParticlesHelper.cauldronParticles(level, pos);
                        }

                        if (entity.progress >= entity.maxProgress) {

                            level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.BLOCKS, 1.0f, 1.0f, true);
                            entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
                            level.playLocalSound(entity.getBlockPos().getX(), entity.getBlockPos().getY(), entity.getBlockPos().getZ(), SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.WEATHER, 1.1f, 1.1f, true);
                            ParticlesHelper.spawnFlashParticles(level, new BlockPos(entity.getBlockPos().getX() + 0.5f, entity.getBlockPos().getY() + 1.5f, entity.getBlockPos().getZ() + 0.5f));
                            entity.resetProgress();
                        }
                    }
                }
            } else
                entity.resetProgress();
        }

        // Craft for server side
        if (!level.isClientSide) {
            if (!entity.inventory.getStackInSlot(0).is(ItemStack.EMPTY.getItem()) && entity.inventory.getStackInSlot(0).getItem() instanceof MobSoulItem item&& !(entity.inventory.getStackInSlot(0).getTag() == null)) {
                ItemStack jar = entity.inventory.getStackInSlot(0).copy();
                if (jar.getTag() != null) {
                    ResourceLocation rl = new ResourceLocation(jar.getTag().getString("id"));

                    EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(rl);
                    LivingEntity livingEntity = (LivingEntity) entityType.create(level);

                    if (livingEntity.getSoundSource().equals(SoundSource.HOSTILE) && level.getDifficulty().equals(Difficulty.PEACEFUL)) {

                    } else {
                        entity.progress++;
                        if (entity.progress >= entity.maxProgress) {
                            livingEntity.moveTo(entity.getBlockPos().getX() + 0.5f, entity.getBlockPos().getY() + 1.5f, entity.getBlockPos().getZ() + 0.5f);
                            livingEntity.addTag("respawned");
                            livingEntity.setYHeadRot(RandomSource.create().nextInt(1, 360));
                            livingEntity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(livingEntity.getMaxHealth() / 2);
                            level.addFreshEntity(livingEntity);

                            entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
                            entity.resetProgress();
                    }
                }
            }
        } else
                entity.resetProgress(); }
    }
}
