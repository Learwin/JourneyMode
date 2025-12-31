package io.github.learwin.journeymode.nei;

import codechicken.nei.api.ItemFilter;
import io.github.learwin.journeymode.client.data.ClientJourneyData;
import net.minecraft.item.ItemStack;

import java.util.regex.Pattern;

public class JourneyUnlockItemFilter implements ItemFilter {

    private final Pattern pattern;

    public JourneyUnlockItemFilter(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean matches(ItemStack item) {
        if (!ClientJourneyData.isUnlocked(item))
            return false;

        return pattern.matcher(item.getDisplayName()).find();
    }
}
