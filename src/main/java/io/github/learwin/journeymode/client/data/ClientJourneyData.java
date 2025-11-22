package io.github.learwin.journeymode.client.data;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClientJourneyData {

    private static final Set<String> UNLOCKED = new HashSet<>();

    private static boolean journeyModeEnabled;

    public static void setUnlocks(List<String> keys) {
        synchronized (UNLOCKED) {
            UNLOCKED.clear();
            UNLOCKED.addAll(keys);
        }
    }

    public static void addUnlock(String key) {
        synchronized (UNLOCKED) {
            UNLOCKED.add(key);
        }
    }

    public static Set<String> getUnlocks() {
        synchronized (UNLOCKED) {
            return new HashSet<>(UNLOCKED);
        }
    }

    public static boolean isUnlocked(String key) {
        synchronized (UNLOCKED) {
            return UNLOCKED.contains(key);
        }
    }

    public static boolean isUnlocked(ItemStack stack) {
        if (stack == null)
            return false;
        return isUnlocked(getKey(stack));
    }

    public static String getKey(ItemStack stack) {
        return Item.itemRegistry.getNameForObject(stack.getItem()) + ":" + stack.getItemDamage();
    }

    public static boolean getJourneyModeEnabled() {
        return journeyModeEnabled;
    }

    public static void setJourneyModeEnabled(boolean status) {
        journeyModeEnabled = status;
    }

}
