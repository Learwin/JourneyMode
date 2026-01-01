package io.github.learwin.journeymode.client;

import static codechicken.nei.NEIClientConfig.hasSMPCounterPart;
import static io.github.learwin.journeymode.client.data.ClientJourneyData.setJourneyModeEnabled;

import codechicken.lib.packet.PacketCustom;
import codechicken.nei.Button;
import codechicken.nei.NEICPH;
import io.github.learwin.journeymode.Constants;
import io.github.learwin.journeymode.mixin.InvokerLayoutManager;

public class JourneyModeButton extends Button {

    @Override
    public boolean onButtonPress(boolean rightclick) {
        if (!rightclick) {
            toggleJourneyMode(this);
            return true;
        }
        return false;
    }

    public String getButtonTip() {
        return InvokerLayoutManager.invoke_getStateTip("journeymode", state);
    }

    private static void toggleJourneyMode(Button button) {
        if (hasSMPCounterPart()) {
            sendToggleJourneyMode();
            setJourneyModeEnabled(button.state == 4);
        }
    }

    private static void sendToggleJourneyMode() {
        PacketCustom packet = new PacketCustom(NEICPH.channel, Constants.SEND_JOURNEY_MODE);
        packet.sendToServer();
    }
}
