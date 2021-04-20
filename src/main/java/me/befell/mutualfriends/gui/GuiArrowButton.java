package me.befell.mutualfriends.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiArrowButton extends GuiButton {
    private static final ResourceLocation SERVER_SELECTION_BUTTONS = new ResourceLocation("textures/gui/server_selection.png");
    private final boolean reversed;
    public boolean disabled = false;

    public GuiArrowButton(int buttonId, int x, int y, String buttonText, boolean reversed) {
        super(buttonId, x, y, buttonText);
        this.reversed = reversed;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            mc.getTextureManager().bindTexture(SERVER_SELECTION_BUTTONS);
            this.hovered = mouseX >= this.xPosition + (reversed ? 1 : 10) && mouseY >= this.yPosition && mouseX < this.xPosition + (reversed ? 14 : 28) && mouseY < this.yPosition + 28;
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            if (disabled) {
                GlStateManager.color(59.0F, 59.0F, 59.0F, 255.0F);
            } else {
                GlStateManager.color(255.0F, 255.0F, 255.0F, 255.0F);
            }
            if (this.hovered && !disabled) {
                drawModalRectWithCustomSizedTexture(xPosition, yPosition, reversed ? 32.0F : 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
            } else {
                drawModalRectWithCustomSizedTexture(xPosition, yPosition, reversed ? 32.0F : 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
            }
            this.mouseDragged(mc, mouseX, mouseY);
            GlStateManager.disableBlend();
        }
    }

    public void setDisabled(boolean yesNo) {
        this.disabled = yesNo;

    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return this.enabled && this.visible && mouseX >= this.xPosition + (reversed ? 1 : 10) && mouseY >= this.yPosition && mouseX < this.xPosition + (reversed ? 14 : 28) && mouseY < this.yPosition + 28;
    }
}