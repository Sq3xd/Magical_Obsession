package com.siuzu.magical_obsession.event;

import com.siuzu.magical_obsession.MagicalObsession;
import com.siuzu.magical_obsession.particle.ModParticles;
import com.siuzu.magical_obsession.particle.custom.MagicDustParticles;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MagicalObsession.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(ModParticles.MAGIC_DUST_PARTICLES.get(),
                MagicDustParticles.Provider::new);
    }
}
