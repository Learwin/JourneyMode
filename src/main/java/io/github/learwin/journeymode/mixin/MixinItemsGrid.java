package io.github.learwin.journeymode.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import codechicken.lib.config.ConfigTag;
import codechicken.lib.config.ConfigTagParent;
import codechicken.lib.vec.Rectangle4i;
import codechicken.nei.ItemsGrid;
import codechicken.nei.NEIClientConfig;
import io.github.learwin.journeymode.client.ClientProxy;
import io.github.learwin.journeymode.client.data.ClientJourneyData;

import java.util.List;

@Mixin(value = ItemsGrid.class, remap = false)
public abstract class MixinItemsGrid<T extends ItemsGrid.ItemsGridSlot, M extends ItemsGrid.MouseContext> {

    @Shadow
    public abstract List<T> getMask();

    @Shadow
    public abstract Rectangle4i getSlotRect(int slotIndex);

    @Inject(method = "afterDrawItems", at = @At("TAIL"))
    public <M extends ItemsGrid.MouseContext> void afterDraw(int mousex, int mousey, M mouseContext, CallbackInfo ci) {
        ConfigTagParent tag = NEIClientConfig.global.config;
        ConfigTag journeyModeEnabledTag = tag.getTag("inventory.journeymode");
        if (!journeyModeEnabledTag.getBooleanValue()) return;

        if (!ClientJourneyData.getJourneyModeEnabled()) return;

        Minecraft mc = Minecraft.getMinecraft();
        mc.getTextureManager()
            .bindTexture(ClientProxy.ICON);

        for (T slot : this.getMask()) {
            if (!ClientJourneyData.isUnlocked(slot.getItemStack()))
                continue;

            draw_discovered(this.getSlotRect(slot.slotIndex));
        }
    }

    private static void draw_discovered(Rectangle4i rect) {
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
