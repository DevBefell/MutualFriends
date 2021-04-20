package me.befell.mutualfriends.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

public class GuiHead extends Gui {
    private final boolean dontShow;
    private final boolean showAlways;
    private final FontRenderer fontRenderer;
    private final String name;
    public boolean reset;
    public String imageString;
    private int textureId = 458375;

    public GuiHead(FontRenderer fontRenderer, String imageString, String name, boolean showAlways, boolean dontShow) {
        this.reset = true;
        this.imageString = imageString;
        this.fontRenderer = fontRenderer;
        this.dontShow = dontShow;
        this.showAlways = showAlways;
        this.name = name;

    }

    public void drawHead(int x, int y, int mouseX, int mouseY) {
        GlStateManager.pushMatrix();
        GlStateManager.color(255.0F, 255.0F, 255.0F, 255.0F);
        GlStateManager.translate(x, y, 0);
        GlStateManager.scale(3.0F, 3.0F, 0);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        final boolean hovered = mouseX >= x && mouseY >= y && mouseX <= x + 24 && mouseY <= y + 24;
        if (imageString != null) {
            if (reset) {
                BufferedImage image;
                byte[] imageByte;
                try {
                    imageByte = Base64.getDecoder().decode(imageString);
                    ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
                    image = ImageIO.read(bis);
                    bis.close();
                    final DynamicTexture skin = new DynamicTexture(image);
                    textureId = skin.getGlTextureId();
                } catch (IOException ignored) {
                    imageString = null;
                }
                reset = false;
            }
            if (imageString != null) {
                GlStateManager.bindTexture(textureId);
                drawModalRectWithCustomSizedTexture(0, 0, 8.0F, 8.0F, 8, 8, 64.0F, 64.0F);
                drawModalRectWithCustomSizedTexture(0, 0, 40.0F, 8.0F, 8, 8, 64.0F, 64.0F);
            }
        } else {
            GlStateManager.disableTexture2D();

            drawRect(0, 0, 8, 8, -9868951);
            GlStateManager.enableTexture2D();
        }
        if (!dontShow && (hovered || showAlways)) {
            drawRect(0, 0, 8, 8, -1386390179);
            GlStateManager.scale(1, 1, 0);
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 12, y + 8, 0);
            if (hovered) {
                GlStateManager.scale(.8, .8, 0);
            } else {
                GlStateManager.scale(.65, .65, 0);
            }
            drawCenteredString(fontRenderer, name, 0, 0, -722);
            GlStateManager.popMatrix();
            return;
        }
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}