package com.siuzu.magical_obsession;

import codechicken.lib.model.ModelRegistryHelper;
import com.google.common.eventbus.Subscribe;
import com.mojang.logging.LogUtils;
import com.siuzu.magical_obsession.event.ModEvents;
import com.siuzu.magical_obsession.init.ModBlockEntities;
import com.siuzu.magical_obsession.init.ModBlocks;
import com.siuzu.magical_obsession.init.ModItems;
import com.siuzu.magical_obsession.init.ModRecipes;
import com.siuzu.magical_obsession.loot.ModLootModifiers;
import com.siuzu.magical_obsession.particle.ModParticles;
import com.siuzu.magical_obsession.render.block.*;
import com.siuzu.magical_obsession.render.item.MyBEWLR;
import com.siuzu.magical_obsession.sound.ModSounds;
import com.siuzu.magical_obsession.world.feature.ModConfiguredFeatures;
import com.siuzu.magical_obsession.world.feature.ModPlacedFeatures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

@Mod(MagicalObsession.MOD_ID)
public class MagicalObsession
{
    public static final String MOD_ID = "magical_obsession";
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final ModelRegistryHelper MODEL_HELPER = new ModelRegistryHelper();
    public MagicalObsession()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

         // Register
        ModBlocks.register(eventBus);
        ModBlockEntities.register(eventBus);
        ModItems.register(eventBus);
        ModRecipes.register(eventBus);
        ModConfiguredFeatures.register(eventBus);
        ModPlacedFeatures.register(eventBus);
        ModParticles.register(eventBus);
        ModSounds.register(eventBus);
        ModLootModifiers.register(eventBus);

        MinecraftForge.EVENT_BUS.register(new ModEvents());
        MinecraftForge.EVENT_BUS.register(MyStaticClientOnlyEventHandler.class);
        MinecraftForge.EVENT_BUS.register(this);

        MinecraftForge.EVENT_BUS.register(this);
    }

    /*@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModClientEvents {

        @SubscribeEvent
        public static void modelRegistry(ModelEvent.RegisterGeometryLoaders event) {
            event.register(new ResourceLocation(MOD_ID, "my_custom_model_loader"));
        }
    }*/

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class MyStaticClientOnlyEventHandler {
        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event){
            event.registerBlockEntityRenderer(ModBlockEntities.SPECIAL_CAULDRON.get(), SpecialCauldronRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.MAGICAL_CAULDRON.get(), MagicalCauldronRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.MAGICAL_CATALLYZATOR.get(), MagicalCatallyzatorRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.MAGICAL_FIELD_SHIELD.get(), MagicalFieldShieldRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.ENTITY_DUPLICATOR.get(), EntityDuplicatorRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.MAGICAL_PENTAGRAM.get(), MagicalPentagramRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.MAGICAL_PENTAGRAM.get(), MagicalPentagramRenderer::new);
            //MODEL_HELPER.register(new ModelResourceLocation(ForgeRegistries.ITEMS.getKey(ModItems.SOUL_JAR.get()), "inventory"), new MyBEWLR());
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            registerItemRenderers();
            LOGGER.info("Loaded Magic!");
        }
    }

    @SuppressWarnings ("ConstantConditions")
    private static void registerItemRenderers() {
        MODEL_HELPER.register(new ModelResourceLocation(ForgeRegistries.ITEMS.getKey(ModItems.SOUL_JAR.get()), "inventory"), new MyBEWLR());
    }

}
