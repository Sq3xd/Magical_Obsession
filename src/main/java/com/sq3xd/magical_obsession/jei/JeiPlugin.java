package com.sq3xd.magical_obsession.jei;

import com.sq3xd.magical_obsession.MagicalObsession;
import com.sq3xd.magical_obsession.init.ModBlocks;
import com.sq3xd.magical_obsession.init.ModItems;
import com.sq3xd.magical_obsession.recipe.SpecialCauldronRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.RecipeType;
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

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(MagicalObsession.MOD_ID, "jei_plugin");
    }

    public static RecipeType<SpecialCauldronRecipe> INFUSION_TYPE =
            new RecipeType<>(SpecialCauldronRecipeCategory.UID, SpecialCauldronRecipe.class);

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addIngredientInfo(new ItemStack(ModItems.MAGIC_DUST.get()), VanillaTypes.ITEM_STACK, magic_dust);
        registration.addIngredientInfo(new ItemStack(ModBlocks.SPECIAL_CAULDRON_ITEM.get()), VanillaTypes.ITEM_STACK, special_cauldron);

        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<SpecialCauldronRecipe> recipesInfusing = rm.getAllRecipesFor(SpecialCauldronRecipe.Type.INSTANCE);
        registration.addRecipes(INFUSION_TYPE, recipesInfusing);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new
                SpecialCauldronRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }
}