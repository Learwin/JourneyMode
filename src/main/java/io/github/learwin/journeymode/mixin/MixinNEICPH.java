package io.github.learwin.journeymode.mixin;

import codechicken.lib.packet.PacketCustom;
import codechicken.nei.NEICPH;
import codechicken.nei.NEIClientConfig;
import io.github.learwin.journeymode.client.data.ClientJourneyData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = NEICPH.class, remap = false)
public class MixinNEICPH {

    @Inject(method = "handleLoginState", at = @At("TAIL"))
    private void handleLoginState(PacketCustom packet, CallbackInfo ci) {
        ClientJourneyData.setJourneyModeEnabled(NEIClientConfig.enabledActions.contains("journeymode"));
    }
}
