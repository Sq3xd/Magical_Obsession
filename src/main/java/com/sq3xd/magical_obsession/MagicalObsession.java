package com.sq3xd.magical_obsession;

import com.mojang.logging.LogUtils;
import com.sq3xd.magical_obsession.init.ModBlockEntities;
import com.sq3xd.magical_obsession.init.ModBlocks;
import com.sq3xd.magical_obsession.init.ModItems;
import com.sq3xd.magical_obsession.render.block.SpecialCauldronRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
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

        MinecraftForge.EVENT_BUS.register(new MyForgeEventHandler());
        MinecraftForge.EVENT_BUS.register(MyStaticClientOnlyEventHandler.class);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public class MyForgeEventHandler {
        @SubscribeEvent
        public void wakeUpEvent(PlayerWakeUpEvent event) {
            if (!event.getEntity().getLevel().isClientSide) {
                if (!event.getEntity().getTags().contains("get_tip")){
                    event.getEntity().sendSystemMessage(Component.translatable("message.player.tip"));
                    event.getEntity().addTag("get_tip");
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class MyStaticClientOnlyEventHandler {
        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event){
            event.registerBlockEntityRenderer(ModBlockEntities.SPECIAL_CAULDRON.get(), SpecialCauldronRenderer::new);
        }
    }

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
