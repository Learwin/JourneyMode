package io.github.learwin.journeymode.common.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import io.github.learwin.journeymode.JourneyUtil;

public class JourneyInventoryTracker {

    private static final Map<UUID, Map<String, Integer>> LAST = new HashMap<>();

    public static void handle(EntityPlayer player) {
        UUID id = player.getUniqueID();
        Map<String, Integer> previous = LAST.get(id);
        Map<String, Integer> now = snapshot(player);

        if (previous != null) {
            for (Map.Entry<String, Integer> e : now.entrySet()) {
                int oldCount = previous.getOrDefault(e.getKey(), 0);
                if (e.getValue() > oldCount) {
                    if (JourneyData.addUnlock(player, e.getKey())) {
                        JourneyData.sendAddUnlock(player, e.getKey());
                    }
                }
            }
        }

        LAST.put(id, now);
    }

    private static Map<String, Integer> snapshot(EntityPlayer player) {
        Map<String, Integer> map = new HashMap<>();
        for (ItemStack stack : player.inventory.mainInventory) {
            if (stack != null) {
                map.merge(JourneyUtil.getKey(stack), stack.stackSize, Integer::sum);
            }
        }
        return map;
    }

}
