package com.sq3xd.magical_obsession.event;

import net.minecraft.network.chat.Component;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

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
