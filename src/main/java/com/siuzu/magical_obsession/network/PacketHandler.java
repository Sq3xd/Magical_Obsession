package com.siuzu.magical_obsession.network;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketHandler {
    /*public static void handle(MyClientMessage msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
                // Make sure it's only executed on the physical client
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandlerClass.handlePacket(msg, ctx))
        );
        ctx.get().setPacketHandled(true);
    }

    // In ClientPacketHandlerClass
    public static void handlePacket(MyClientMessage msg, Supplier<NetworkEvent.Context> ctx) {
        // Do stuff
    }*/
}
