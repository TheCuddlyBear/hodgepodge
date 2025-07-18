package me.thecuddlybear.hodgepodge.neoforge.client;

import me.thecuddlybear.hodgepodge.client.HodgepodgeClient;
import me.thecuddlybear.hodgepodge.client.entity.ShroomieEntityRenderer;
import me.thecuddlybear.hodgepodge.entity.HodgepodgeEntities;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public class HodgepodgeNeoforgeClient {

    public static void init(IEventBus eventBus){
        eventBus.addListener(HodgepodgeNeoforgeClient::onClientSetup);
        eventBus.addListener(HodgepodgeNeoforgeClient::onCommonSetup);
        eventBus.addListener(HodgepodgeNeoforgeClient::onRegisterEntities);
    }

    public static void onClientSetup(FMLClientSetupEvent event){
        HodgepodgeClient.init();
    }

    public static void onCommonSetup(FMLCommonSetupEvent event){

    }

    public static void onRegisterEntities(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(HodgepodgeEntities.SHROOMIE.get(), ShroomieEntityRenderer::new);
    }

}
