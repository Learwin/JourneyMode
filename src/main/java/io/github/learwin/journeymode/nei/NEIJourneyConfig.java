package io.github.learwin.journeymode.nei;

import codechicken.lib.config.ConfigTagParent;
import codechicken.nei.NEIActions;
import codechicken.nei.NEIClientConfig;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEIJourneyConfig implements IConfigureNEI {

    @Override
    public void loadConfig() {
        ConfigTagParent tag = NEIClientConfig.global.config;
        tag.getTag("inventory.journeymode").setDefaultValue("false");
        API.addOption(new JourneyModeNEIOption());

        NEIActions.addAction("journeymode");
        NEIActions.smpRequired("journeymode");

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
