package io.github.learwin.journeymode.common.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import io.github.learwin.journeymode.common.data.JourneyData;

public class PlayerLoginHandler {

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        JourneyData.sendFullUnlocks(event.player);
    }

}
