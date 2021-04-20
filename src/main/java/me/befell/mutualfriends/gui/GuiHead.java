package me.befell.mutualfriends.gui;

import sun.misc.BASE64Decoder;

import net.minecraft.client.gui.GuiButton;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class GuiCustomButton extends GuiButton {
    public GuiCustomButton(int buttonId, int x, int y, String buttonText, String imageString) {
        super(buttonId, x, y, buttonText);
        try {
            BufferedImage image = null;
            byte[] imageByte;

            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (IOException ignored){}

    }
}