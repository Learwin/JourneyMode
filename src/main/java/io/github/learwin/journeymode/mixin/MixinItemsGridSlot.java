package io.github.learwin.journeymode.mixin;

import codechicken.lib.vec.Rectangle4i;
import codechicken.nei.ItemsGrid;
import io.github.learwin.journeymode.client.ClientProxy;
import io.github.learwin.journeymode.client.data.ClientJourneyData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ItemsGrid.ItemsGridSlot.class, remap = false)
public class MixinItemsGridSlot {

    @Final
    @Shadow(remap = false)
    public ItemStack item;

    @Inject(method = "afterDraw", at = @At("TAIL"))
    public <M extends ItemsGrid.MouseContext> void afterDraw(Rectangle4i rect, M mouseContext, CallbackInfo ci) {
        if (item == null)
            return;

        if (ClientJourneyData.isUnlocked(item)) {
            Minecraft mc = Minecraft.getMinecraft();
            mc.getTextureManager().bindTexture(ClientProxy.ICON);

            float uMin = 0f;
            float uMax = 1f;
            float vMin = 0f;
            float vMax = 1f;

            Tessellator tess = Tessellator.instance;
            tess.startDrawingQuads();
            tess.addVertexWithUV(rect.x, rect.y + 8, 300, uMin, vMax);
            tess.addVertexWithUV(rect.x + 8, rect.y + 8, 300, uMax, vMax);
            tess.addVertexWithUV(rect.x + 8, rect.y, 300, uMax, vMin);
            tess.addVertexWithUV(rect.x, rect.y, 300, uMin, vMin);
            tess.draw();
        }
    }

}
