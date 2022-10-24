package com.sq3xd.magical_obsession.recipe;

import com.sq3xd.magical_obsession.MagicalObsession;
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

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
