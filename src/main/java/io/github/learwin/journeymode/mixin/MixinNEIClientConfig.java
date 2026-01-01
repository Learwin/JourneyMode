package io.github.learwin.journeymode.mixin;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import codechicken.lib.config.ConfigTag;
import codechicken.nei.ItemStackSet;
import codechicken.nei.NEIClientConfig;
import io.github.learwin.journeymode.client.data.ClientJourneyData;

@Mixin(value = NEIClientConfig.class, remap = false)
public class MixinNEIClientConfig {

    @Shadow(remap = false)
    public static ItemStackSet bannedBlocks = new ItemStackSet();

    @Invoker("canPerformAction")
    public static boolean canPerformAction(String name) {
        throw new AssertionError();
    }

    @Invoker("getSetting")
    public static ConfigTag getSetting(String s) {
        throw new AssertionError();
    }

    @Inject(method = "canCheatItem", at = @At("RETURN"), cancellable = true)
    private static void canCheatItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        boolean originalReturn = cir.getReturnValue();

        if (originalReturn) return;

        // Check if action is enabled in settings
        if (!canPerformAction("journeymode")) return;

        // Check if mode is turned on
        if (!ClientJourneyData.getJourneyModeEnabled()) return;

        // Check if item is not banned
        if (bannedBlocks.contains(stack)) return;

        // Check if the Item is actually unlocked
        if (!ClientJourneyData.isUnlocked(stack)) return;

        cir.setReturnValue(true);
    }

    @Inject(method = "modePermitsAction", at = @At("HEAD"), cancellable = true)
    private static void modePermitsAction(String name, CallbackInfoReturnable<Boolean> cir) {
        if (!name.equals("journeymode")) return;

        if (getSetting("inventory.journeymode").getBooleanValue()) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }

}
