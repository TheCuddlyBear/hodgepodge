package me.thecuddlybear.hodgepodge;

import me.thecuddlybear.hodgepodge.entity.HodgepodgeEntities;
import me.thecuddlybear.hodgepodge.entity.ai.HodgepodgeMemoryTypes;

public final class Hodgepodge {
    public static final String MOD_ID = "hodgepodge";

    public static void init() {
        // Write common init code here.
        HodgepodgeEntities.init();
        HodgepodgeMemoryTypes.init();
    }
}
