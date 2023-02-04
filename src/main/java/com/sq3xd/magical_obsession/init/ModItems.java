package com.sq3xd.magical_obsession.init;

import com.sq3xd.magical_obsession.MagicalObsession;
import com.sq3xd.magical_obsession.item.ImmolationSwordItem;
import com.sq3xd.magical_obsession.item.MagicDustItem;
import com.sq3xd.magical_obsession.item.JarItem;
import com.sq3xd.magical_obsession.item.SuspendedRedstoneItem;
import com.sq3xd.magical_obsession.item.tab.ModTabs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.util.thread.SidedThreadGroups;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MagicalObsession.MOD_ID);

    // Register
    public static RegistryObject<Item> CRYSTAL_ITEM = ITEMS.register("crystal", () ->
            new Item(new Item.Properties().rarity(Rarity.RARE)));

    public static RegistryObject<Item> SPHERE_ITEM = ITEMS.register("sphere", () ->
            new Item(new Item.Properties().rarity(Rarity.RARE)));

    public static RegistryObject<Item> ENTITY_DUPLICATOR_PARTICLE = ITEMS.register("entity_duplicator_particle", () ->
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

    public static RegistryObject<Item> SOUL_JAR = ITEMS.register("soul_jar", () ->
            new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).tab(ModTabs.MAGICAL_OBSESSION_JARS)));

    // Tools

    public static RegistryObject<Item> IMMOLATION_SWORD = ITEMS.register("immolation_sword", () ->
            new ImmolationSwordItem(ModTiers.IMMOLATION, 1, -2.4f, new Item.Properties()
                    .fireResistant().tab(ModTabs.MAGICAL_OBSESSION)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
        Set<ResourceLocation> entityKeyList = ForgeRegistries.ENTITY_TYPES.getKeys();

        for (ResourceLocation k : entityKeyList) {
            EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(k);
            if (!entityType.getCategory().equals(MobCategory.MISC)) {
                RegistryObject<Item> SOUL_JAR = ITEMS.register("soul_jar" + '_' + k.toString().replace(':', '_').replace('.', '_'), () ->
                        new JarItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).tab(ModTabs.MAGICAL_OBSESSION_JARS), entityType));
            }
        }
    }
}
