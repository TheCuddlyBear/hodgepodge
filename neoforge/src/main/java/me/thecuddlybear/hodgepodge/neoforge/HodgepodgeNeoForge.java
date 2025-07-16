package me.thecuddlybear.hodgepodge.neoforge;

import me.thecuddlybear.hodgepodge.Hodgepodge;
import me.thecuddlybear.hodgepodge.neoforge.client.HodgepodgeNeoforgeClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;

@Mod(Hodgepodge.MOD_ID)
public final class HodgepodgeNeoForge {
    public HodgepodgeNeoForge(ModContainer modContainer, IEventBus eventBus) {
        IEventBus bus = NeoForge.EVENT_BUS;

        // Run our common setup.
        Hodgepodge.init();

        if(FMLEnvironment.dist == Dist.CLIENT) {
            HodgepodgeNeoforgeClient.init(eventBus);
        }

    }
}
