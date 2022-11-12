package com.sq3xd.magical_obsession.event;

import com.sq3xd.magical_obsession.MagicalObsession;
import com.sq3xd.magical_obsession.init.ModBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ModEvents {
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
