package me.thecuddlybear.hodgepodge.config;


import me.fzzyhmstrs.fzzy_config.annotations.Action;
import me.fzzyhmstrs.fzzy_config.annotations.RequiresAction;
import me.fzzyhmstrs.fzzy_config.api.FileType;
import me.fzzyhmstrs.fzzy_config.api.SaveType;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedNumber;
import me.thecuddlybear.hodgepodge.Hodgepodge;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class HodgepodgeConfig extends Config {

    public HodgepodgeConfig(){
        super(ResourceLocation.fromNamespaceAndPath(Hodgepodge.MOD_ID, "hodgepodge_config"));
    }


    public boolean doDayCounter = true;

    @ValidatedInt.Restrict(min=1, type= ValidatedNumber.WidgetType.TEXTBOX)
    public int milestoneDay = 100;


    @Override
    public int defaultPermLevel() {
        return 4;
    }

    @Override
    public @NotNull FileType fileType() {
        return FileType.TOML;
    }

    @Override
    public @NotNull SaveType saveType() {
        return SaveType.SEPARATE;
    }
}
