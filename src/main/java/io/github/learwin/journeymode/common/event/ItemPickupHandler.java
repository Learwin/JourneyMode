package io.github.learwin.journeymode.common.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import io.github.learwin.journeymode.common.data.JourneyData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

public class ItemPickupHandler {

    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event) {
        EntityPlayer player = event.entityPlayer;
        ItemStack stack = event.item.getEntityItem();

        if (JourneyData.addUnlock(player, stack)) {
            String key = JourneyData.getKey(stack);
            JourneyData.sendAddUnlock(player, key);
        }
    }

}
