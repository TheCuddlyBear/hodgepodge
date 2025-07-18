package me.thecuddlybear.hodgepodge.config;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;

public class Configs {

    public static HodgepodgeConfig hodgepodgeConfig = ConfigApiJava.registerAndLoadConfig(HodgepodgeConfig::new);

    public static void init(){}

}
