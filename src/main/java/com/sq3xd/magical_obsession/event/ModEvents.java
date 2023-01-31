package com.sq3xd.magical_obsession.event;

import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;

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
