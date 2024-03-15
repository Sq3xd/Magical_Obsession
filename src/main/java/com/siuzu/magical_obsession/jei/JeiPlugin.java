package com.siuzu.magical_obsession.jei;

import com.siuzu.magical_obsession.MagicalObsession;
import com.siuzu.magical_obsession.init.ModBlocks;
import com.siuzu.magical_obsession.init.ModItems;
import com.siuzu.magical_obsession.recipe.MagicalCatallyzatorRecipe;
import com.siuzu.magical_obsession.recipe.MagicalCauldronRecipe;
import com.siuzu.magical_obsession.recipe.SpecialCauldronCampfireRecipe;
import com.siuzu.magical_obsession.recipe.SpecialCauldronRecipe;
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
    Component special_cauldron = Component.translatable("gui.jei.description.special_cauldron");
    Component magical_cauldron = Component.translatable("gui.jei.description.magical_cauldron");
    Component magical_cattalyzator = Component.translatable("gui.jei.description.magical_catallyzator");
    Component magical_field_shield = Component.translatable("gui.jei.description.magical_field_shield");
    Component entity_duplicator = Component.translatable("gui.jei.description.entity_duplicator");
    Component magical_pentagram = Component.translatable("gui.jei.description.magical_pentagram");
    Component magic_dust = Component.translatable("gui.jei.description.magic_dust");
    Component suspended_redstone = Component.translatable("gui.jei.description.suspended_redstone");
    Component nugget = Component.translatable("gui.jei.description.nugget");
    Component soul_jar = Component.translatable("gui.jei.description.soul_jar");
    Component immolation_sword = Component.translatable("gui.jei.description.immolation_sword");

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(MagicalObsession.MOD_ID, "jei_plugin");
    }

     // Recipe Types
    public static RecipeType<SpecialCauldronRecipe> SPECIAL_CAULDRON_TYPE =
            new RecipeType<>(SpecialCauldronRecipeCategory.UID, SpecialCauldronRecipe.class);

    public static RecipeType<SpecialCauldronCampfireRecipe> SPECIAL_CAULDRON_CAMPFIRE_TYPE =
            new RecipeType<>(SpecialCauldronCampfireRecipeCategory.UID, SpecialCauldronCampfireRecipe.class);

    public static RecipeType<MagicalCauldronRecipe> MAGICAL_CAULDRON_TYPE =
            new RecipeType<>(MagicalCauldronRecipeCategory.UID, MagicalCauldronRecipe.class);

    public static RecipeType<MagicalCatallyzatorRecipe> MAGICAL_CATALLYZATOR_TYPE =
            new RecipeType<>(MagicalCatallyzatorRecipeCategory.UID, MagicalCatallyzatorRecipe.class);

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ModBlocks.SPECIAL_CAULDRON_ITEM.get().asItem().getDefaultInstance(), SPECIAL_CAULDRON_TYPE);
        registration.addRecipeCatalyst(ModBlocks.SPECIAL_CAULDRON_ITEM.get().asItem().getDefaultInstance(), SPECIAL_CAULDRON_CAMPFIRE_TYPE);
        registration.addRecipeCatalyst(ModBlocks.MAGICAL_CAULDRON_ITEM.get().asItem().getDefaultInstance(), MAGICAL_CAULDRON_TYPE);
        registration.addRecipeCatalyst(ModBlocks.MAGICAL_CATALLYZATOR_ITEM.get().asItem().getDefaultInstance(), MAGICAL_CATALLYZATOR_TYPE);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addIngredientInfo(new ItemStack(ModBlocks.SPECIAL_CAULDRON_ITEM.get()), VanillaTypes.ITEM_STACK, special_cauldron);
        registration.addIngredientInfo(new ItemStack(ModBlocks.MAGICAL_CAULDRON_ITEM.get()), VanillaTypes.ITEM_STACK, magical_cauldron);
        registration.addIngredientInfo(new ItemStack(ModBlocks.MAGICAL_CATALLYZATOR_ITEM.get()), VanillaTypes.ITEM_STACK, magical_cattalyzator);
        registration.addIngredientInfo(new ItemStack(ModBlocks.MAGICAL_FIELD_SHIELD_ITEM.get()), VanillaTypes.ITEM_STACK, magical_field_shield);
        registration.addIngredientInfo(new ItemStack(ModBlocks.ENTITY_DUPLICATOR_ITEM.get()), VanillaTypes.ITEM_STACK, entity_duplicator);
        registration.addIngredientInfo(new ItemStack(ModBlocks.MAGICAL_PENTAGRAM.get()), VanillaTypes.ITEM_STACK, magical_pentagram);
        registration.addIngredientInfo(new ItemStack(ModItems.MAGIC_DUST.get()), VanillaTypes.ITEM_STACK, magic_dust);
        registration.addIngredientInfo(new ItemStack(ModItems.SUSPENDED_REDSTONE.get()), VanillaTypes.ITEM_STACK, suspended_redstone);
        registration.addIngredientInfo(new ItemStack(ModItems.TERRA_NUGGET.get()), VanillaTypes.ITEM_STACK, nugget);
        registration.addIngredientInfo(new ItemStack(ModItems.DIAMOND_NUGGET.get()), VanillaTypes.ITEM_STACK, nugget);
        registration.addIngredientInfo(new ItemStack(ModItems.EMERALD_NUGGET.get()), VanillaTypes.ITEM_STACK, nugget);
        registration.addIngredientInfo(new ItemStack(ModItems.LAPIS_NUGGET.get()), VanillaTypes.ITEM_STACK, nugget);
        registration.addIngredientInfo(new ItemStack(ModItems.COAL_NUGGET.get()), VanillaTypes.ITEM_STACK, nugget);
        registration.addIngredientInfo(new ItemStack(ModItems.MOB_SOUL.get()), VanillaTypes.ITEM_STACK, soul_jar);
        registration.addIngredientInfo(new ItemStack(ModItems.IMMOLATION_SWORD.get()), VanillaTypes.ITEM_STACK, immolation_sword);

        // Recipe Category

        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<SpecialCauldronRecipe> recipesSpecialCauldron = rm.getAllRecipesFor(SpecialCauldronRecipe.Type.INSTANCE);
        registration.addRecipes(SPECIAL_CAULDRON_TYPE, recipesSpecialCauldron);

        List<SpecialCauldronCampfireRecipe> recipesSpecialCauldronCampfire = rm.getAllRecipesFor(SpecialCauldronCampfireRecipe.Type.INSTANCE);
        registration.addRecipes(SPECIAL_CAULDRON_CAMPFIRE_TYPE, recipesSpecialCauldronCampfire);

        List<MagicalCauldronRecipe> recipesMagicalCauldron = rm.getAllRecipesFor(MagicalCauldronRecipe.Type.INSTANCE);
        registration.addRecipes(MAGICAL_CAULDRON_TYPE, recipesMagicalCauldron);

        List<MagicalCatallyzatorRecipe> recipesMagicalCatallyzator = rm.getAllRecipesFor(MagicalCatallyzatorRecipe.Type.INSTANCE);
        registration.addRecipes(MAGICAL_CATALLYZATOR_TYPE, recipesMagicalCatallyzator);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new
                SpecialCauldronRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

        registration.addRecipeCategories(new
                SpecialCauldronCampfireRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

        registration.addRecipeCategories(new
                MagicalCauldronRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

        registration.addRecipeCategories(new
                MagicalCatallyzatorRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }
}