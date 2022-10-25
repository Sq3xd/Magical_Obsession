package com.sq3xd.magical_obsession.jei;

import com.sq3xd.magical_obsession.MagicalObsession;
import com.sq3xd.magical_obsession.init.ModBlocks;
import com.sq3xd.magical_obsession.recipe.SpecialCauldronRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class SpecialCauldronRecipeCategory implements IRecipeCategory<SpecialCauldronRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(MagicalObsession.MOD_ID, "special_cauldron");
    public final static ResourceLocation BG =
            new ResourceLocation(MagicalObsession.MOD_ID, "textures/gui/special_cauldron_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public SpecialCauldronRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(BG, 0, 0, 176, 89);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.SPECIAL_CAULDRON.get()));
    }

    @Override
    public RecipeType<SpecialCauldronRecipe> getRecipeType() {
        return JeiPlugin.INFUSION_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Special Cauldron");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SpecialCauldronRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 84, 18).addIngredients(recipe.getIngredients().get(0));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 84, 63).addItemStack(recipe.getResultItem());
    }
}