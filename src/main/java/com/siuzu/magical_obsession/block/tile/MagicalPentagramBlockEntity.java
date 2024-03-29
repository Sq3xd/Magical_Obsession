package com.siuzu.magical_obsession.block.tile;

import com.siuzu.magical_obsession.init.ModBlockEntities;
import com.siuzu.magical_obsession.init.ModItems;
import com.siuzu.magical_obsession.util.ParticlesHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Random;

public class MagicalPentagramBlockEntity  extends BlockEntity {
    public static Direction direction;
    protected final ContainerData data;

    public final ItemStackHandler inventory = new ItemStackHandler(1){
        @Override
        public void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private int progress = 0;
    private int maxProgress = 175;

    // Initialisation

    public MagicalPentagramBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MAGICAL_PENTAGRAM.get(), pos, state);

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> MagicalPentagramBlockEntity.this.progress;
                    case 1 -> MagicalPentagramBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> MagicalPentagramBlockEntity.this.progress = value;
                    case 1 -> MagicalPentagramBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 5;
            }
        };
    }

    //

    public int getProgress() {
        return progress;
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", inventory.serializeNBT());
        nbt.putInt("progress", this.progress);
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        inventory.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("progress");
    }

    // Packets

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    // Crafting

    private void resetProgress() {
        this.progress = 0;
    }

    // Tick
    public static void tick(Level level, BlockPos pos, BlockState state, MagicalPentagramBlockEntity entity) {
        // Craft for client side
        if (level.isClientSide) {
            if (entity.inventory.getStackInSlot(0).is(ItemStack.EMPTY.getItem())) {
                entity.resetProgress();
            }

            if (entity.inventory.getStackInSlot(0).is(ModItems.MAGIC_DUST.get())) {
                entity.progress++;
                ParticlesHelper.cauldronParticles(level, pos);
                if (entity.progress >= 125) {
                    entity.resetProgress();
                    entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
                }
            }

            if (entity.inventory.getStackInSlot(0).is(ModItems.SUSPENDED_REDSTONE.get())) {
                entity.progress++;
                ParticlesHelper.spawnFlashParticles(level, pos);
                if (entity.progress >= 155) {
                    entity.resetProgress();
                    entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
                }
            }

            if (entity.inventory.getStackInSlot(0).is(ModItems.TERRA_NUGGET.get())) {
                entity.progress++;
                ParticlesHelper.cauldronParticles(level, pos);
                ParticlesHelper.spawnFlashParticles(level, pos);

                if (entity.progress == 370) {
                    level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENDER_DRAGON_AMBIENT, SoundSource.BLOCKS, 0.85f, 0.85f, true);
                    LightningBolt lb = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                    lb.setPos(pos.getX(), pos.getY(), pos.getZ());
                    level.addFreshEntity(lb);
                }

                if (entity.progress >= 570) {
                    level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.END_PORTAL_SPAWN, SoundSource.BLOCKS, 1.0f, 1.0f, true);
                    entity.resetProgress();
                    LightningBolt lb = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                    lb.setPos(pos.getX(), pos.getY(), pos.getZ());
                    level.addFreshEntity(lb);
                    entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
                }
            }
        }

        // Craft for server side
        if (!level.isClientSide) {
            if (entity.inventory.getStackInSlot(0).is(ItemStack.EMPTY.getItem())) {
                entity.resetProgress();
            }

            if (entity.inventory.getStackInSlot(0).is(ModItems.MAGIC_DUST.get())) {
                entity.progress++;
                if (entity.progress >= 125) {
                    level.explode(null, DamageSource.MAGIC, null, pos.getX(), pos.getY(), pos.getZ(), 11.5f, true, Explosion.BlockInteraction.DESTROY);
                    entity.resetProgress();
                    entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
                }
            }

            if (entity.inventory.getStackInSlot(0).is(ModItems.SUSPENDED_REDSTONE.get())) {
                entity.progress++;
                if (entity.progress >= 155) {
                    level.explode(null, DamageSource.MAGIC, null, pos.getX(), pos.getY(), pos.getZ(), 15.5f, true, Explosion.BlockInteraction.DESTROY);

                    for (int i = 0; i < 3; i++) {
                        Entity warrior = new Skeleton(EntityType.SKELETON, level);
                        warrior.setItemSlot(EquipmentSlot.MAINHAND, Items.IRON_SWORD.getDefaultInstance());
                        warrior.setItemSlot(EquipmentSlot.HEAD, Items.CHAINMAIL_HELMET.getDefaultInstance());
                        warrior.moveTo(entity.getBlockPos().getX(), entity.getBlockPos().getY(), entity.getBlockPos().getZ());
                        level.addFreshEntity(warrior);
                    }

                    LightningBolt lb = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                    lb.setPos(pos.getX(), pos.getY(), pos.getZ());
                    level.addFreshEntity(lb);
                    entity.resetProgress();
                    entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
                }
            }

            if (entity.inventory.getStackInSlot(0).is(ModItems.TERRA_NUGGET.get())) {
                entity.progress++;

                if (entity.progress == 370){
                    Entity warrior = new Skeleton(EntityType.SKELETON, level);
                    warrior.setItemSlot(EquipmentSlot.MAINHAND, Items.DIAMOND_SWORD.getDefaultInstance());
                    warrior.setItemSlot(EquipmentSlot.HEAD, Items.CHAINMAIL_HELMET.getDefaultInstance());
                    warrior.moveTo(entity.getBlockPos().getX(), entity.getBlockPos().getY(), entity.getBlockPos().getZ());
                    level.addFreshEntity(warrior);
                }

                if (entity.progress >= 570) {
                    level.explode(null, DamageSource.MAGIC, null, pos.getX(), pos.getY(), pos.getZ(), 5.5f, true, Explosion.BlockInteraction.DESTROY);
                    for (int i = 0; i < 15; i++) {
                        Random a = new Random();
                        Entity warrior = new Skeleton(EntityType.SKELETON, level);
                        warrior.setItemSlot(EquipmentSlot.MAINHAND, Items.DIAMOND_SWORD.getDefaultInstance());
                        warrior.setItemSlot(EquipmentSlot.HEAD, Items.CHAINMAIL_HELMET.getDefaultInstance());
                        warrior.setItemSlot(EquipmentSlot.CHEST, Items.CHAINMAIL_CHESTPLATE.getDefaultInstance());
                        warrior.setCustomName(Component.literal("Ender Warrior"));
                        warrior.moveTo(entity.getBlockPos().getX() + a.nextInt(-15, 15), entity.getBlockPos().getY() + a.nextInt(5), entity.getBlockPos().getZ() + a.nextInt(-15, 15));
                        level.addFreshEntity(warrior);
                    }

                    Entity enderman = new EnderMan(EntityType.ENDERMAN, level);
                    enderman.setCustomName(Component.literal("Ender Revenger"));
                    enderman.moveTo(entity.getBlockPos().getX(), entity.getBlockPos().getY(), entity.getBlockPos().getZ());
                    level.addFreshEntity(enderman);

                    entity.resetProgress();
                    entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
                }
            }
        }
    }
}
