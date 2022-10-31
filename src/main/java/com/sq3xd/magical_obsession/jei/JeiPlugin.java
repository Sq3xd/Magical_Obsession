package com.sq3xd.magical_obsession.jei;

import com.sq3xd.magical_obsession.MagicalObsession;
import com.sq3xd.magical_obsession.init.ModBlocks;
import com.sq3xd.magical_obsession.init.ModItems;
import com.sq3xd.magical_obsession.recipe.SpecialCauldronCampfireRecipe;
import com.sq3xd.magical_obsession.recipe.SpecialCauldronRecipe;
import com.sq3xd.magical_obsession.tags.ModTags;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin {
    Component magic_dust = Component.translatable("gui.jei.description.magic_dust");
    Component special_cauldron = Component.translatable("gui.jei.description.special_cauldron");
    Component nugget = Component.translatable("gui.jei.description.nugget");

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(MagicalObsession.MOD_ID, "jei_plugin");
    }

     // Recipe Types
    public static RecipeType<SpecialCauldronRecipe> SPECIAL_CAULDRON_TYPE =
            new RecipeType<>(SpecialCauldronRecipeCategory.UID, SpecialCauldronRecipe.class);

    public static RecipeType<SpecialCauldronCampfireRecipe> SPECIAL_CAULDRON_CAMPFIRE_TYPE =
            new RecipeType<>(SpecialCauldronCampfireRecipeCategory.UID, SpecialCauldronCampfireRecipe.class);

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ModBlocks.SPECIAL_CAULDRON_ITEM.get().asItem().getDefaultInstance(), SPECIAL_CAULDRON_TYPE);
        registration.addRecipeCatalyst(ModBlocks.SPECIAL_CAULDRON_ITEM.get().asItem().getDefaultInstance(), SPECIAL_CAULDRON_CAMPFIRE_TYPE);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addIngredientInfo(new ItemStack(ModItems.MAGIC_DUST.get()), VanillaTypes.ITEM_STACK, magic_dust);
        registration.addIngredientInfo(new ItemStack(ModBlocks.SPECIAL_CAULDRON_ITEM.get()), VanillaTypes.ITEM_STACK, special_cauldron);
        registration.addIngredientInfo(new ItemStack(ModItems.TERRA_NUGGET.get()), VanillaTypes.ITEM_STACK, nugget);
        registration.addIngredientInfo(new ItemStack(ModItems.DIAMOND_NUGGET.get()), VanillaTypes.ITEM_STACK, nugget);
        registration.addIngredientInfo(new ItemStack(ModItems.EMERALD_NUGGET.get()), VanillaTypes.ITEM_STACK, nugget);
        registration.addIngredientInfo(new ItemStack(ModItems.LAPIS_NUGGET.get()), VanillaTypes.ITEM_STACK, nugget);
        registration.addIngredientInfo(new ItemStack(ModItems.COAL_NUGGET.get()), VanillaTypes.ITEM_STACK, nugget);

        // Recipe Category

        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<SpecialCauldronRecipe> recipesSpecialCauldron = rm.getAllRecipesFor(SpecialCauldronRecipe.Type.INSTANCE);
        registration.addRecipes(SPECIAL_CAULDRON_TYPE, recipesSpecialCauldron);

        List<SpecialCauldronCampfireRecipe> recipesSpecialCauldronCampfire = rm.getAllRecipesFor(SpecialCauldronCampfireRecipe.Type.INSTANCE);
        registration.addRecipes(SPECIAL_CAULDRON_CAMPFIRE_TYPE, recipesSpecialCauldronCampfire);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new
                SpecialCauldronRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

        registration.addRecipeCategories(new
                SpecialCauldronCampfireRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }
}