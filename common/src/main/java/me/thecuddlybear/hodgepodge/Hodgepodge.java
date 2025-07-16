package me.thecuddlybear.hodgepodge;

import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import me.thecuddlybear.hodgepodge.entity.HodgepodgeEntities;
import me.thecuddlybear.hodgepodge.entity.ShroomieEntity;
import me.thecuddlybear.hodgepodge.entity.ai.HodgepodgeMemoryTypes;

public final class Hodgepodge {
    public static final String MOD_ID = "hodgepodge";

    public static void init() {
        // Write common init code here.
        HodgepodgeEntities.init();
        HodgepodgeMemoryTypes.init();

        EntityAttributeRegistry.register(HodgepodgeEntities.SHROOMIE, ShroomieEntity.createShroomieAttributes());

    }
}
