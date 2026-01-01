package io.github.learwin.journeymode;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class JourneyUtil {

    public static String getKey(ItemStack stack) {
        return Item.itemRegistry.getNameForObject(stack.getItem()) + ":" + stack.getItemDamage();
    }

}
