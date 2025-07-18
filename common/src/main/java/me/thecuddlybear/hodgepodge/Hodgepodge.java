package me.thecuddlybear.hodgepodge;

import dev.architectury.event.events.common.TickEvent;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import me.thecuddlybear.hodgepodge.config.Configs;
import me.thecuddlybear.hodgepodge.config.HodgepodgeConfig;
import me.thecuddlybear.hodgepodge.entity.HodgepodgeEntities;
import me.thecuddlybear.hodgepodge.entity.ShroomieEntity;
import me.thecuddlybear.hodgepodge.entity.ai.HodgepodgeMemoryTypes;
import me.thecuddlybear.hodgepodge.util.DayCounter;
import me.thecuddlybear.hodgepodge.world.HodgepodgeDayCounterSavedData;
import net.minecraft.server.level.ServerLevel;

public final class Hodgepodge {
    public static final String MOD_ID = "hodgepodge";

    public static void init() {
        // Write common init code here.
        HodgepodgeEntities.init();
        HodgepodgeMemoryTypes.init();
        Configs.init();

        EntityAttributeRegistry.register(HodgepodgeEntities.SHROOMIE, ShroomieEntity.createShroomieAttributes());

        //Events
        TickEvent.SERVER_POST.register((server) -> {
            if(Configs.hodgepodgeConfig.doDayCounter) {
                if(server == null) return;
                ServerLevel overworld = server.overworld();

                HodgepodgeDayCounterSavedData data = overworld.getDataStorage().computeIfAbsent(HodgepodgeDayCounterSavedData.ID);

                long currentTime = overworld.getDayTime();
                long currentDay = currentTime / 24000L;
                long lastDay = data.getLastDayTime() / 24000L;

                if (currentDay > lastDay) {
                    data.setDayCounter(data.getDayCounter() + 1);
                    //System.out.println("Day " + data.getDayCounter() + " has begun!");
                    DayCounter.startAnimation(server, data.getDayCounter(), (data.getDayCounter() % Configs.hodgepodgeConfig.milestoneDay) == 0);
                }

                DayCounter.updateAnimation(server);
                DayCounter.updateSpecialAnimation(server);

                data.setLastDayTime(currentTime);
            }
        });


    }
}
