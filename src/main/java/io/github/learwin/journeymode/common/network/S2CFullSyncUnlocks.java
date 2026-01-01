package io.github.learwin.journeymode.common.network;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.github.learwin.journeymode.client.data.ClientJourneyData;
import io.netty.buffer.ByteBuf;

public class S2CFullSyncUnlocks implements IMessage {

    public List<String> unlocks;

    public S2CFullSyncUnlocks() {}

    public S2CFullSyncUnlocks(Collection<String> unlocks) {
        this.unlocks = new ArrayList<>(unlocks);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int size = buf.readInt();
        unlocks = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            unlocks.add(ByteBufUtils.readUTF8String(buf));
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(unlocks.size());
        for (String s : unlocks) {
            ByteBufUtils.writeUTF8String(buf, s);
        }
    }

    public static class Handler implements IMessageHandler<S2CFullSyncUnlocks, IMessage> {

        @Override
        public IMessage onMessage(S2CFullSyncUnlocks message, MessageContext ctx) {
            ClientJourneyData.setUnlocks(message.unlocks);
            return null;
        }
    }
}
