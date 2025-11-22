package io.github.learwin.journeymode.mixin;

import codechicken.nei.*;
import io.github.learwin.journeymode.intf.IJourneyButtonGetter;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LayoutStyleMinecraft.class, remap = false)
public class MixinLayoutStyleMinecraft {

    @Inject(
        method = "layout",
        at = @At(
            value = "INVOKE",
            target = "Lcodechicken/nei/LayoutStyleMinecraft;layoutFooter(Lnet/minecraft/client/gui/inventory/GuiContainer;Lcodechicken/nei/VisiblityData;)V"
        )
    )
    private void onLayout(GuiContainer gui, VisiblityData visiblity, CallbackInfo ci) {

        ((IJourneyButtonGetter) LayoutManager.instance()).getJourneyButton().state = 0x4 | (getJourneyMode() ? 1 : 0);

        if (NEIClientConfig.canPerformAction("journeymode")) {
            ((InvokerLayoutStyleMinecraft) this).invoke_layoutButton(((IJourneyButtonGetter) LayoutManager.instance()).getJourneyButton());
        }
    }

    @Unique
    private boolean getJourneyMode() {
        return NEIClientConfig.enabledActions.contains("journeymode");
    }

    @Inject(method = "init", at = @At("TAIL"))
    public void init(CallbackInfo ci) {
        ((IJourneyButtonGetter) LayoutManager.instance()).getJourneyButton().icon = new Image(144, 12, 12, 12);
    }
}
