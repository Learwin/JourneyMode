package io.github.learwin.journeymode.nei;

import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.guihook.GuiContainerManager;

public class NEIJourneyConfig implements IConfigureNEI {

    @Override
    public void loadConfig() {

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
