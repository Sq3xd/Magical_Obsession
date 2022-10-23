package com.sq3xd.magical_obsession.init;

import com.sq3xd.magical_obsession.MagicalObsession;
import com.sq3xd.magical_obsession.item.MagicDustItem;
import com.sq3xd.magical_obsession.item.tab.ModTabs;
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

    public static RegistryObject<Item> MAGIC_DUST = ITEMS.register("magic_dust", () ->
            new MagicDustItem(new Item.Properties().rarity(Rarity.RARE).tab(ModTabs.MAGICAL_OBSESSION)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
