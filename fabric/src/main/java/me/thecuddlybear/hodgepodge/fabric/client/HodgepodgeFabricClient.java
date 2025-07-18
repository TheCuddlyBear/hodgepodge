package me.thecuddlybear.hodgepodge.fabric.client;

import me.thecuddlybear.hodgepodge.client.HodgepodgeClient;
import me.thecuddlybear.hodgepodge.client.entity.ShroomieEntityRenderer;
import me.thecuddlybear.hodgepodge.entity.HodgepodgeEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public final class HodgepodgeFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        HodgepodgeClient.init();

        // Entity Renderers
        EntityRendererRegistry.register(HodgepodgeEntities.SHROOMIE.get(), ShroomieEntityRenderer::new);

    }
}
