package io.github.learwin.journeymode.common.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.github.learwin.journeymode.client.data.ClientJourneyData;
import io.netty.buffer.ByteBuf;

public class S2CAddUnlock implements IMessage {

    public String key;

    public S2CAddUnlock() {}

    public S2CAddUnlock(String key) {
        this.key = key;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        key = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, key);
    }

    public static class Handler implements IMessageHandler<S2CAddUnlock, IMessage> {
        @Override
        public IMessage onMessage(S2CAddUnlock message, MessageContext ctx) {
            ClientJourneyData.addUnlock(message.key);
            return null;
        }
    }
}
