package io.github.learwin.journeymode.common.event;

import io.github.learwin.journeymode.common.data.JourneyTeamData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import io.github.learwin.journeymode.JourneyUtil;
import io.github.learwin.journeymode.common.data.JourneyData;

public class ItemPickupHandler {

    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event) {
        EntityPlayer player = event.entityPlayer;
        ItemStack stack = event.item.getEntityItem();
        String key = JourneyUtil.getKey(stack);

        if (JourneyData.addUnlock(player, key)) {
            JourneyData.sendAddUnlock(player, key);
        }
        JourneyTeamData.unlock_for_team(player, key);
    }

}
