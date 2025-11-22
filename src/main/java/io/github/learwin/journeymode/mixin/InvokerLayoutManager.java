package io.github.learwin.journeymode.mixin;

import codechicken.nei.LayoutManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = LayoutManager.class, remap = false)
public interface InvokerLayoutManager {

    @Invoker("getStateTip")
    static String invoke_getStateTip(String name, int state) {
        throw new AssertionError();
    }

}
