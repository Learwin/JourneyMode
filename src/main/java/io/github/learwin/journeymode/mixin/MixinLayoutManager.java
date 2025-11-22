package io.github.learwin.journeymode.mixin;

import codechicken.lib.config.ConfigTag;
import codechicken.lib.config.ConfigTagParent;
import codechicken.nei.Button;
import codechicken.nei.LayoutManager;
import codechicken.nei.NEIClientConfig;
import codechicken.nei.VisiblityData;
import io.github.learwin.journeymode.client.JourneyModeButton;
import io.github.learwin.journeymode.intf.IJourneyButtonGetter;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static codechicken.nei.LayoutManager.addWidget;
import static codechicken.nei.NEIClientConfig.canPerformAction;

@Mixin(value = LayoutManager.class, remap = false)
public class MixinLayoutManager implements IJourneyButtonGetter {

    @Unique
    private static Button journeyMode$journeyMode;

    @Inject(method = "init", at = @At("TAIL"))
    private static void init(CallbackInfo ci) {
        journeyMode$journeyMode = new JourneyModeButton();
        journeyMode$journeyMode.state |= 0x4;
    }

    @Inject(method = "updateWidgetVisiblities", at = @At("TAIL"))
    private static void updateWidgetVisiblities(GuiContainer gui, VisiblityData visiblity, CallbackInfo ci) {

        ConfigTagParent tag = NEIClientConfig.global.config;
        ConfigTag journeyModeEnabledTag = tag.getTag("inventory.journeymode");

        if (!journeyModeEnabledTag.getBooleanValue())
            return;

        if (canPerformAction("journeymode"))
            addWidget(journeyMode$journeyMode);

    }

    @Override
    public Button getJourneyButton() {
        return journeyMode$journeyMode;
    }
}
