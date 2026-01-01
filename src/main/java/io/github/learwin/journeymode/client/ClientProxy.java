package io.github.learwin.journeymode.client;

import net.minecraft.util.ResourceLocation;

import io.github.learwin.journeymode.JourneyMode;
import io.github.learwin.journeymode.common.CommonProxy;

public class ClientProxy extends CommonProxy {

    public static final ResourceLocation ICON = new ResourceLocation(JourneyMode.MODID, "textures/ui/discovered.png");
    public static final ResourceLocation ICON_JOURNEY_BUTTON = new ResourceLocation(
        JourneyMode.MODID,
        "textures/ui/journeymodebutton.png");
}
