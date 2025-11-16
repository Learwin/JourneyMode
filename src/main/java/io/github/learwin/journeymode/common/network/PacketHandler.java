package io.github.learwin.journeymode.common.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE =
        NetworkRegistry.INSTANCE.newSimpleChannel("journeymode");

    private static int discriminator = 0;

    public static void init() {
        INSTANCE.registerMessage(S2CAddUnlock.Handler.class,
            S2CAddUnlock.class, discriminator++, Side.CLIENT);
        INSTANCE.registerMessage(S2CFullSyncUnlocks.Handler.class,
            S2CFullSyncUnlocks.class, discriminator++, Side.CLIENT);
    }

    public static void sendToPlayer(IMessage message, EntityPlayerMP player) {
        INSTANCE.sendTo(message, player);
    }

    public static void sendToServer(IMessage message) {
        INSTANCE.sendToServer(message);
    }

    public static void sendToAll(IMessage message) {
        INSTANCE.sendToAll(message);
    }

}
