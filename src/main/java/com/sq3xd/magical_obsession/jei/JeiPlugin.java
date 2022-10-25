package com.sq3xd.magical_obsession.jei;

import com.sq3xd.magical_obsession.MagicalObsession;
import com.sq3xd.magical_obsession.init.ModBlocks;
import com.sq3xd.magical_obsession.init.ModItems;
import com.sq3xd.magical_obsession.recipe.SpecialCauldronRecipe;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin {
    Component magic_dust = Component.translatable("gui.jei.description.magic_dust");
    Component special_cauldron = Component.translatable("gui.jei.description.special_cauldron");

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(MagicalObsession.MOD_ID, "jei_plugin");
    }

    public static RecipeType<SpecialCauldronRecipe> SPECIAL_CAULDRON_TYPE =
            new RecipeType<>(SpecialCauldronRecipeCategory.UID, SpecialCauldronRecipe.class);

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ModBlocks.SPECIAL_CAULDRON_ITEM.get().asItem().getDefaultInstance(), SPECIAL_CAULDRON_TYPE);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addIngredientInfo(new ItemStack(ModItems.MAGIC_DUST.get()), VanillaTypes.ITEM_STACK, magic_dust);
        registration.addIngredientInfo(new ItemStack(ModBlocks.SPECIAL_CAULDRON_ITEM.get()), VanillaTypes.ITEM_STACK, special_cauldron);

        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<SpecialCauldronRecipe> recipesSpecialCauldron = rm.getAllRecipesFor(SpecialCauldronRecipe.Type.INSTANCE);
        registration.addRecipes(SPECIAL_CAULDRON_TYPE, recipesSpecialCauldron);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new
                SpecialCauldronRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }
}