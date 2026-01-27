package io.github.learwin.journeymode.mixin;

import codechicken.nei.NEICPH;
import codechicken.nei.NEIClientUtils;
import codechicken.nei.NEISPH;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.api.GuiInfo;
import codechicken.nei.recipe.DefaultOverlayHandler;
import com.mojang.realmsclient.gui.EditOnlineWorldScreen;
import io.github.learwin.journeymode.client.data.ClientJourneyData;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mixin(value = DefaultOverlayHandler.class, remap = false)

public class MixinDefaultOverlayHandler {

    @Inject(method = "moveIngredients", at = @At("TAIL"), cancellable = true)

    private void moveIngredients(GuiContainer gui, List<DefaultOverlayHandler.IngredientDistribution> assignedIngredients, int multiplier, CallbackInfo ci) {
        if(!ClientJourneyData.getJourneyModeEnabled())
            return;

        for (var dist : assignedIngredients) {
            if (!ClientJourneyData.isUnlocked(dist.permutation) || dist.slots[0].getHasStack())
                continue;

            NEICPH.sendSetSlot(dist.slots[0].slotNumber, NEIServerUtils.copyStack(dist.permutation, 1), true);
        }
    }
}
