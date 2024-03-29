package com.siuzu.magical_obsession.init;

import com.siuzu.magical_obsession.MagicalObsession;
import com.siuzu.magical_obsession.recipe.MagicalCatallyzatorRecipe;
import com.siuzu.magical_obsession.recipe.MagicalCauldronRecipe;
import com.siuzu.magical_obsession.recipe.SpecialCauldronCampfireRecipe;
import com.siuzu.magical_obsession.recipe.SpecialCauldronRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MagicalObsession.MOD_ID);

     // Register
    public static final RegistryObject<RecipeSerializer<SpecialCauldronRecipe>> SPECIAL_CAULDRON_SERIALIZER =
            SERIALIZERS.register("special_cauldron", () -> SpecialCauldronRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<SpecialCauldronCampfireRecipe>> SPECIAL_CAULDRON_CAMPFIRE_SERIALIZER =
            SERIALIZERS.register("special_cauldron_campfire", () -> SpecialCauldronCampfireRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<MagicalCauldronRecipe>> MAGICAL_CAULDRON_SERIALIZER =
            SERIALIZERS.register("magical_cauldron", () -> MagicalCauldronRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<MagicalCatallyzatorRecipe>> MAGICAL_CATALLYZATOR_SERIALIZER =
            SERIALIZERS.register("magical_catallyzator", () -> MagicalCatallyzatorRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
