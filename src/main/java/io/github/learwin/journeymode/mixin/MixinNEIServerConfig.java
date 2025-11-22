package io.github.learwin.journeymode.mixin;

import codechicken.lib.packet.PacketCustom;
import codechicken.nei.NEIServerConfig;
import io.github.learwin.journeymode.Constants;
import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static codechicken.nei.NEIServerConfig.canPlayerPerformAction;

@Mixin(value = NEIServerConfig.class, remap = false)
public class MixinNEIServerConfig {

    @Invoker("setDefaultFeature")
    private static void setDefaultFeature(String featurename, String... names) {
        throw new AssertionError();
    }

    @Inject(method = "loadConfig", at = @At("TAIL"))
    private static void loadConfig(CallbackInfo ci) {
        setDefaultFeature("journeymode", "ALL");

    }

    @Inject(method = "authenticatePacket", at = @At("HEAD"), cancellable = true)
    private static void authenticatePacket(EntityPlayerMP sender, PacketCustom packet, CallbackInfoReturnable<Boolean> cir) {
        if (packet.getType() == Constants.SEND_JOURNEY_MODE) {
            cir.setReturnValue(canPlayerPerformAction(sender.getCommandSenderName(), "journeymode"));
            cir.cancel();
        }
    }

}
