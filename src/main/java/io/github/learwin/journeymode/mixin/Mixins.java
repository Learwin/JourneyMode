package io.github.learwin.journeymode.mixin;

import javax.annotation.Nonnull;

import com.gtnewhorizon.gtnhmixins.builders.IMixins;
import com.gtnewhorizon.gtnhmixins.builders.MixinBuilder;

public enum Mixins implements IMixins {
    // Accessor

    // Invoker
    NEILayoutStyleMinecraftInvoker(new MixinBuilder().setPhase(Phase.LATE)
        .addClientMixins("InvokerLayoutStyleMinecraft")),

    NEILayoutManagerInvoker(new MixinBuilder().setPhase(Phase.LATE)
        .addClientMixins("InvokerLayoutManager")),
    // Mixin
    // Clientside
    NEIItemsGridSlotMixin(new MixinBuilder().setPhase(Phase.LATE)
        .addClientMixins("MixinItemsGridSlot")),

    NEILayoutManagerMixin(new MixinBuilder().setPhase(Phase.LATE)
        .addClientMixins("MixinLayoutManager")),

    NEILayoutStyleMinecraftMixin(new MixinBuilder().setPhase(Phase.LATE)
        .addClientMixins("MixinLayoutStyleMinecraft")),

    NEICPHMixin(new MixinBuilder().setPhase(Phase.LATE)
        .addClientMixins("MixinNEICPH")),

    NEIClientConfigMixin(new MixinBuilder().setPhase(Phase.LATE)
        .addClientMixins("MixinNEIClientConfig")),

    // Serverside
    NEISPHMixin(new MixinBuilder().setPhase(Phase.LATE)
        .addServerMixins("MixinNEISPH")),

    NEIServerConfigMixin(new MixinBuilder().setPhase(Phase.LATE)
        .addServerMixins("MixinNEIServerConfig")),

    NEIActionsMixin(new MixinBuilder().setPhase(Phase.LATE)
        .addServerMixins("MixinNEIActions")),

    DefaultOverlayHandlerMixin(new MixinBuilder().setPhase(Phase.LATE)
        .addClientMixins("MixinDefaultOverlayHandler"));

    private final MixinBuilder builder;

    Mixins(MixinBuilder builder) {
        this.builder = builder;
    }

    @Nonnull
    @Override
    public MixinBuilder getBuilder() {
        return builder;
    }
}
