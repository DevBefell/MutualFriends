package me.befell.mutualfriends.gui;

import me.befell.mutualfriends.MutualFriends;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;

public class GuiMutualFriends extends GuiScreen {
    private MutualFriends mod;
    private GuiTextField firstPlayer;
    private GuiTextField secondPlayer;
    private List<String> players;

    public GuiMutualFriends(MutualFriends mod) {
        this.mod = mod;
    }

    @Override
    public void initGui() {
        this.firstPlayer = new GuiTextField(0, this.fontRendererObj, this.width / 2 - 50, 15, 100, 17);
        this.firstPlayer.setFocused(true);
        this.firstPlayer.setText("Test");
        this.secondPlayer = new GuiTextField(1, this.fontRendererObj, this.width / 2 - 50, 42, 100, 17);
        this.secondPlayer.setText("Test");
    }

    @Override
    public void updateScreen() {
        this.firstPlayer.updateCursorCounter();
        this.secondPlayer.updateCursorCounter();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.firstPlayer.drawTextBox();
        this.secondPlayer.drawTextBox();
        drawArrow();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        this.firstPlayer.mouseClicked(mouseX, mouseY, mouseButton);
        this.secondPlayer.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        this.firstPlayer.textboxKeyTyped(typedChar, keyCode);
        this.secondPlayer.textboxKeyTyped(typedChar, keyCode);
        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);

            if (this.mc.currentScreen == null) {
                this.mc.setIngameFocus();
            }
        }

    }

    private void drawArrow() {
        final int x = this.width / 2;
        final double y = (double) this.height / 2;
        final int color = Color.BLUE.getRGB();
        float f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(4, DefaultVertexFormats.POSITION);
        worldrenderer.pos(x, y - 5, 0.0D).endVertex();
        worldrenderer.pos(x, y + 5, 0.0D).endVertex();
        worldrenderer.pos(x + 10, y, 0.0D).endVertex();
        tessellator.draw();
        Tessellator tessellator1 = Tessellator.getInstance();
        WorldRenderer worldrenderer1 = tessellator.getWorldRenderer();
        worldrenderer1.begin(7, DefaultVertexFormats.POSITION);

        worldrenderer1.pos(x - 20, y +3, 0.0D).endVertex();
        worldrenderer1.pos(x, y +3, 0.0D).endVertex();
        worldrenderer1.pos(x, y -3, 0.0D).endVertex();
        worldrenderer1.pos(x - 20, y-3, 0.0D).endVertex();
        tessellator1.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

}
