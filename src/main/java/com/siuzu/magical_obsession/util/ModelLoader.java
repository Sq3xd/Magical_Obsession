package com.siuzu.magical_obsession.util;

import codechicken.lib.datagen.ItemModelProvider;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.siuzu.magical_obsession.MagicalObsession;
import net.minecraft.client.Minecraft;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import org.antlr.v4.codegen.model.ModelElement;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModelLoader {
    public static final ResourceLocation MODEL_HELPER = new ResourceLocation(MagicalObsession.MOD_ID, "models/item/entity_duplicator_particle");
}