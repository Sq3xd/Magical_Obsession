package com.sq3xd.magical_obsession.init;

import com.sq3xd.magical_obsession.MagicalObsession;
import com.sq3xd.magical_obsession.block.tile.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MagicalObsession.MOD_ID);

    public static final RegistryObject<BlockEntityType<SpecialCauldronBlockEntity>> SPECIAL_CAULDRON =
            BLOCK_ENTITIES.register("special_cauldron", () ->
                    BlockEntityType.Builder.of(SpecialCauldronBlockEntity::new,
                            ModBlocks.SPECIAL_CAULDRON.get()).build(null));

    public static final RegistryObject<BlockEntityType<MagicalCauldronBlockEntity>> MAGICAL_CAULDRON =
            BLOCK_ENTITIES.register("magical_cauldron", () ->
                    BlockEntityType.Builder.of(MagicalCauldronBlockEntity::new,
                            ModBlocks.MAGICAL_CAULDRON.get()).build(null));

    public static final RegistryObject<BlockEntityType<MagicalCatallyzatorBlockEntity>> MAGICAL_CATALLYZATOR =
            BLOCK_ENTITIES.register("magical_catallyzator", () ->
                    BlockEntityType.Builder.of(MagicalCatallyzatorBlockEntity::new,
                            ModBlocks.MAGICAL_CATALLYZATOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<MagicalFieldShieldBlockEntity>> MAGICAL_FIELD_SHIELD =
            BLOCK_ENTITIES.register("magical_field_shield", () ->
                    BlockEntityType.Builder.of(MagicalFieldShieldBlockEntity::new,
                            ModBlocks.MAGICAL_FIELD_SHIELD.get()).build(null));

    public static final RegistryObject<BlockEntityType<MagicPipeBlockEntity>> MAGIC_PIPE =
            BLOCK_ENTITIES.register("magic_pipe", () ->
                    BlockEntityType.Builder.of(MagicPipeBlockEntity::new,
                            ModBlocks.MAGIC_PIPE.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
