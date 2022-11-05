package com.sq3xd.magical_obsession.init;

import com.sq3xd.magical_obsession.MagicalObsession;
import com.sq3xd.magical_obsession.block.MagicalCauldronBlock;
import com.sq3xd.magical_obsession.block.PipeBlock;
import com.sq3xd.magical_obsession.block.SpecialCauldronBlock;
import com.sq3xd.magical_obsession.item.tab.ModTabs;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MagicalObsession.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MagicalObsession.MOD_ID);

     // Register
    public static RegistryObject<Block> SPECIAL_CAULDRON = BLOCKS.register("special_cauldron", () ->
             new SpecialCauldronBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.METAL).requiresCorrectToolForDrops()
                     .explosionResistance(2f).destroyTime(2f).noOcclusion()));

    public static RegistryObject<Item> SPECIAL_CAULDRON_ITEM = ITEMS.register("special_cauldron", () ->
            new BlockItem(SPECIAL_CAULDRON.get(), new Item.Properties().rarity(Rarity.RARE)
                    .tab(ModTabs.MAGICAL_OBSESSION)));

    public static RegistryObject<Block> MAGICAL_CAULDRON = BLOCKS.register("magical_cauldron", () ->
            new MagicalCauldronBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.METAL).requiresCorrectToolForDrops()
                    .explosionResistance(2.5f).destroyTime(2.15f).noOcclusion()));

    public static RegistryObject<Item> MAGICAL_CAULDRON_ITEM = ITEMS.register("magical_cauldron", () ->
            new BlockItem(MAGICAL_CAULDRON.get(), new Item.Properties().rarity(Rarity.RARE)
                    .tab(ModTabs.MAGICAL_OBSESSION)));

     // Pipes
     public static RegistryObject<Block> MAGIC_PIPE = BLOCKS.register("magic_pipe", () ->
             new PipeBlock(BlockBehaviour.Properties.of(Material.GLASS, MaterialColor.SNOW)
                     .explosionResistance(0.5f).destroyTime(0.15f)));

    public static RegistryObject<Item> MAGIC_PIPE_ITEM = ITEMS.register("magic_pipe", () ->
            new BlockItem(MAGIC_PIPE.get(), new Item.Properties()
                    .tab(ModTabs.MAGICAL_OBSESSION)));

     // Ores
     public static RegistryObject<Block> TERRA_ORE = BLOCKS.register("terra_ore", () ->
             new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops()
                     .explosionResistance(3.5f).destroyTime(3.5f)));

    public static RegistryObject<Item> TERRA_ORE_ITEM = ITEMS.register("terra_ore", () ->
            new BlockItem(TERRA_ORE.get(), new Item.Properties()
                    .tab(ModTabs.MAGICAL_OBSESSION)));

    public static RegistryObject<Block> DEEPSLATE_TERRA_ORE = BLOCKS.register("deepslate_terra_ore", () ->
            new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops()
                    .explosionResistance(5f).destroyTime(5f)));

    public static RegistryObject<Item> DEEPSLATE_TERRA_ORE_ITEM = ITEMS.register("deepslate_terra_ore", () ->
            new BlockItem(DEEPSLATE_TERRA_ORE.get(), new Item.Properties()
                    .tab(ModTabs.MAGICAL_OBSESSION)));

    /*
    public static List<String> names = new ArrayList<String>();


    public static String getNameText() {
        int leftLimit = 97; // a
        int rightLimit = 122; // z
        int targetStringLength = 8;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    public static void setLen(int len) {
        for (int i = 0; i <= len; i++){
            names.add(getNameText());
        }
    }

    public static void register(IEventBus eventBus){
        setLen(15559);
        for (String name : names){
            registerBlocks(name);
        }
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }*/

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }
}
