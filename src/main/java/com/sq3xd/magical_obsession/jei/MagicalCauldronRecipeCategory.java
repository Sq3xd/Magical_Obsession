package com.sq3xd.magical_obsession.jei;

import com.sq3xd.magical_obsession.MagicalObsession;
import com.sq3xd.magical_obsession.init.ModBlocks;
import com.sq3xd.magical_obsession.recipe.MagicalCauldronRecipe;
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

public class MagicalCauldronRecipeCategory implements IRecipeCategory<MagicalCauldronRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(MagicalObsession.MOD_ID, "magical_cauldron");
    public static final ResourceLocation BG =
            new ResourceLocation(MagicalObsession.MOD_ID, "textures/gui/magical_cauldron_gui.png");
    private final IDrawable background;
    private final IDrawable icon;

    public MagicalCauldronRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(BG, 0, 0, 176, 89);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.MAGICAL_CAULDRON.get()));
    }

    @Override
    public RecipeType<MagicalCauldronRecipe> getRecipeType() {
        return JeiPlugin.MAGICAL_CAULDRON_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Magical Cauldron");
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
    public void setRecipe(IRecipeLayoutBuilder builder, MagicalCauldronRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 84, 18).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 84, 63).addItemStack(recipe.getResultItem());
    }
}