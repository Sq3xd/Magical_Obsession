package com.siuzu.magical_obsession.init;

import com.siuzu.magical_obsession.MagicalObsession;
import com.siuzu.magical_obsession.item.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MagicalObsession.MOD_ID);

    // Register
    public static RegistryObject<Item> CRYSTAL_ITEM = ITEMS.register("crystal", () ->
            new Item(new Item.Properties().rarity(Rarity.RARE)));

    public static RegistryObject<Item> SPHERE_ITEM = ITEMS.register("sphere", () ->
            new Item(new Item.Properties().rarity(Rarity.RARE)));

    public static RegistryObject<Item> SPECIAL_CAULDRON_CAMPFIRE_ITEM = ITEMS.register("special_cauldron_campfire", () ->
            new Item(new Item.Properties().rarity(Rarity.RARE)));

    public static RegistryObject<Item> MAGIC_DUST = ITEMS.register("magic_dust", () ->
            new MagicDustItem(new Item.Properties().rarity(Rarity.RARE).tab(ModTabs.MAGICAL_OBSESSION)));

    public static RegistryObject<Item> SUSPENDED_REDSTONE = ITEMS.register("suspended_redstone", () ->
            new SuspendedRedstoneItem(new Item.Properties().rarity(Rarity.RARE).tab(ModTabs.MAGICAL_OBSESSION)));

    // Nuggets
    public static RegistryObject<Item> TERRA_NUGGET = ITEMS.register("terra_nugget", () ->
            new Item(new Item.Properties().fireResistant().tab(ModTabs.MAGICAL_OBSESSION)));

    public static RegistryObject<Item> DIAMOND_NUGGET = ITEMS.register("diamond_nugget", () ->
            new Item(new Item.Properties().tab(ModTabs.MAGICAL_OBSESSION)));

    public static RegistryObject<Item> EMERALD_NUGGET = ITEMS.register("emerald_nugget", () ->
            new Item(new Item.Properties().tab(ModTabs.MAGICAL_OBSESSION)));

    public static RegistryObject<Item> LAPIS_NUGGET = ITEMS.register("lapis_nugget", () ->
            new Item(new Item.Properties().tab(ModTabs.MAGICAL_OBSESSION)));

    public static RegistryObject<Item> COAL_NUGGET = ITEMS.register("coal_nugget", () ->
            new Item(new Item.Properties().tab(ModTabs.MAGICAL_OBSESSION)));

    public static RegistryObject<Item> MOB_SOUL = ITEMS.register("mob_soul", () ->
            new MobSoulItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));

    public static RegistryObject<Item> DEBUG = ITEMS.register("debug", () ->
            new DebugItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).tab(ModTabs.MAGICAL_OBSESSION_SOULS)));

    // Tools

    public static RegistryObject<Item> IMMOLATION_SWORD = ITEMS.register("immolation_sword", () ->
            new ImmolationSwordItem(ModTiers.IMMOLATION, 1, -2.4f, new Item.Properties()
                    .fireResistant().tab(ModTabs.MAGICAL_OBSESSION)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
