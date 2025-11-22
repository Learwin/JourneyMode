package io.github.learwin.journeymode.mixin;

import codechicken.nei.Button;
import codechicken.nei.LayoutStyleMinecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = LayoutStyleMinecraft.class, remap = false)
public interface InvokerLayoutStyleMinecraft {

    @Invoker("layoutButton")
    void invoke_layoutButton(Button button);

}
