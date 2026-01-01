package io.github.learwin.journeymode.common.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import io.github.learwin.journeymode.common.data.JourneyInventoryTracker;

public class PlayerTickHandler {

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.player.worldObj.isRemote) return;
        JourneyInventoryTracker.handle(event.player);
    }

}
