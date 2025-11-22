package io.github.learwin.journeymode.mixin;

import codechicken.lib.packet.PacketCustom;
import codechicken.nei.NEISPH;
import codechicken.nei.NEIServerConfig;
import codechicken.nei.PlayerSave;
import io.github.learwin.journeymode.Constants;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.INetHandlerPlayServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = NEISPH.class, remap = false)
public class MixinNEISPH {

    @Inject(method = "handlePacket", at = @At("TAIL"), cancellable = true)
    public void handlePacket(PacketCustom packet, EntityPlayerMP sender, INetHandlerPlayServer netHandler, CallbackInfo ci) {
        int packetId = packet.getType();
        if (packetId == Constants.SEND_JOURNEY_MODE) {
            journeyMode$toggleJourneyMode(sender);
            ci.cancel();
        }
    }

    @Unique
    private static void journeyMode$toggleJourneyMode(EntityPlayerMP player){
        PlayerSave playerSave = NEIServerConfig.forPlayer(player.getCommandSenderName());
        playerSave.enableAction("journeymode", !playerSave.isActionEnabled("journeymode"));
    }

}
