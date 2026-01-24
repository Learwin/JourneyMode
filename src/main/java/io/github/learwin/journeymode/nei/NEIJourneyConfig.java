package io.github.learwin.journeymode.nei;

import net.minecraft.util.EnumChatFormatting;

import codechicken.lib.config.ConfigTagParent;
import codechicken.nei.NEIActions;
import codechicken.nei.NEIClientConfig;
import codechicken.nei.SearchField;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEIJourneyConfig implements IConfigureNEI {

    @Override
    public void loadConfig() {
        ConfigTagParent tag = NEIClientConfig.global.config;
        tag.getTag("inventory.journeymode")
            .setDefaultValue("false");
        tag.getTag("inventory.search.journeyUnlocksSearchMode")
            .setDefaultValue("1");
        API.addOption(new JourneyModeNEIOption());

        NEIActions.addAction("journeymode");
        NEIActions.smpRequired("journeymode");

        API.addSearchProvider(
            new SearchField.SearchParserProvider(
                ';',
                "journeyUnlocks",
                EnumChatFormatting.GREEN,
                JourneyUnlockItemFilter::new));
    }

    @Override
    public String getName() {
        return "Journey Mode NEI Plugin";
    }

    @Override
    public String getVersion() {
        return "0.0.1";
    }
}
