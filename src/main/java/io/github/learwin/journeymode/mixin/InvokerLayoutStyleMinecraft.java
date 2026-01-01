package io.github.learwin.journeymode.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import codechicken.nei.Button;
import codechicken.nei.LayoutStyleMinecraft;

@Mixin(value = LayoutStyleMinecraft.class, remap = false)
public interface InvokerLayoutStyleMinecraft {

    @Invoker("layoutButton")
    void invoke_layoutButton(Button button);

}
