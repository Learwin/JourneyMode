package io.github.learwin.journeymode.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import codechicken.nei.LayoutManager;

@Mixin(value = LayoutManager.class, remap = false)
public interface InvokerLayoutManager {

    @Invoker("getStateTip")
    static String invoke_getStateTip(String name, int state) {
        throw new AssertionError();
    }

}
