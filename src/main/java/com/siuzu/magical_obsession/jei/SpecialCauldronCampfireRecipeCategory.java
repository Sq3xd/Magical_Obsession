package com.siuzu.magical_obsession.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import com.siuzu.magical_obsession.MagicalObsession;
import com.siuzu.magical_obsession.init.ModItems;
import com.siuzu.magical_obsession.recipe.SpecialCauldronCampfireRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SpecialCauldronCampfireRecipeCategory implements IRecipeCategory<SpecialCauldronCampfireRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(MagicalObsession.MOD_ID, "special_cauldron_campfire");
    public static final ResourceLocation BG =
            new ResourceLocation(MagicalObsession.MOD_ID, "textures/gui/special_cauldron_campfire_gui.png");
    private final IDrawable background;
    private final IDrawable icon;
    //private final IDrawable campfire;

    public SpecialCauldronCampfireRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(BG, 0, 0, 176, 89);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModItems.SPECIAL_CAULDRON_CAMPFIRE_ITEM.get()));
        //ResourceLocation campfire_rs = new ResourceLocation(MagicalObsession.MOD_ID, "textures/item/coal_nugget.png");
        //this.campfire = helper.createDrawable(campfire_rs, 1, 1, 15,15);

    }

    @Override
    public RecipeType<SpecialCauldronCampfireRecipe> getRecipeType() {
        return JeiPlugin.SPECIAL_CAULDRON_CAMPFIRE_TYPE;
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

//    @Override
//    public void draw(SpecialCauldronCampfireRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
//        background.draw(stack, 0, 0);
//        campfire.draw(stack, 1, 1);
//        IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
//    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SpecialCauldronCampfireRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 84, 18).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 84, 63).addItemStack(recipe.getResultItem());
    }
}