package com.sq3xd.magical_obsession;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import com.mojang.math.Matrix4f;
import com.sq3xd.magical_obsession.init.ModBlockEntities;
import com.sq3xd.magical_obsession.init.ModBlocks;
import com.sq3xd.magical_obsession.init.ModItems;
import com.sq3xd.magical_obsession.render.block.SpecialCauldronRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(MagicalObsession.MOD_ID)
public class MagicalObsession
{
    public static final String MOD_ID = "magical_obsession";
    private static final Logger LOGGER = LogUtils.getLogger();

    public MagicalObsession()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();;

         // Register
        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModBlockEntities.register(eventBus);

        eventBus.addListener(this::commonSetup);
        //FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(MyStaticClientOnlyEventHandler.class);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public class MyForgeEventHandler {
        /*@SubscribeEvent
        public void pickupItem(EntityItemPickupEvent event) {
            if (!event.getEntity().getLevel().isClientSide) {
                Minecraft mc = Minecraft.getInstance();
                LOGGER.info("Picked!");
            }
        }*/
    }

    /*private void clientSetup (final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.INFUSER_BLOCK.get(), RenderType.cutout()); // Replace ModBlocks.INFUSER_BLOCK.get() with your block
    }*/

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class MyStaticClientOnlyEventHandler {
        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event){
            event.registerBlockEntityRenderer(ModBlockEntities.SPECIAL_CAULDRON.get(), SpecialCauldronRenderer::new);
        }
    }


    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
    }

     // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            LOGGER.info("Loaded Magic!");
        }
    }
}
