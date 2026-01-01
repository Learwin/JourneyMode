package io.github.learwin.journeymode.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import codechicken.nei.NEIActions;

@Mixin(value = NEIActions.class, remap = false)
public class MixinNEIActions {

    @Inject(method = "init", at = @At("TAIL"))
    private static void init(CallbackInfo ci) {
        NEIActions.addAction("journeymode");
        NEIActions.smpRequired("journeymode");
    }

}
