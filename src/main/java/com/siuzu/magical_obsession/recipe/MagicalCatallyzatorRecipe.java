package com.siuzu.magical_obsession.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.siuzu.magical_obsession.MagicalObsession;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class MagicalCatallyzatorRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    private final int sphere;
    private final int time;

    public MagicalCatallyzatorRecipe(ResourceLocation id, ItemStack output,
                                     NonNullList<Ingredient> recipeItems, int sphere, int time) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.sphere = sphere;
        this.time = time;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return recipeItems.get(0).test(pContainer.getItem(0));
        }

        return recipeItems.get(0).test(pContainer.getItem(0));
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public int getSphere() {
        return sphere;
    }

    public int getTime() {
        return time;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<MagicalCatallyzatorRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "magical_catallyzator";
    }


    public static class Serializer implements RecipeSerializer<MagicalCatallyzatorRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(MagicalObsession.MOD_ID, "magical_catallyzator");

        @Override
        public MagicalCatallyzatorRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));


            int sphere = pSerializedRecipe.getAsJsonPrimitive("sphere").getAsInt();
            int time = pSerializedRecipe.getAsJsonPrimitive("time").getAsInt();

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new MagicalCatallyzatorRecipe(pRecipeId, output, inputs, sphere, time);
        }

        @Override
        public @Nullable MagicalCatallyzatorRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {

            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack output = buf.readItem();
            int sphere = buf.readInt();
            int time = buf.readInt();
            return new MagicalCatallyzatorRecipe(id, output, inputs, sphere, time);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, MagicalCatallyzatorRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(), false);
        }
    }
}