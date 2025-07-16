package me.thecuddlybear.hodgepodge.neoforge.client;

import me.thecuddlybear.hodgepodge.client.HodgepodgeClient;
import net.neoforged.bus.api.IEventBus;

public class HodgepodgeNeoforgeClient {

    public static void init(IEventBus eventBus){
        eventBus.addListener(HodgepodgeNeoforgeClient::onClientSetup);
        eventBus.addListener(HodgepodgeNeoforgeClient::onCommonSetup);
        eventBus.addListener(HodgepodgeNeoforgeClient::onRegisterEntities);
    }

    public static void onClientSetup(IEventBus eventBus){
        HodgepodgeClient.init();
    }

    public static void onCommonSetup(IEventBus eventBus){

    }

    public static void onRegisterEntities(IEventBus eventBus){

    }

}
