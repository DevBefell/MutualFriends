package me.befell.mutualfriends.gui.screens;

import com.google.gson.JsonObject;
import me.befell.mutualfriends.MutualFriends;
import me.befell.mutualfriends.gui.GuiArrowButton;
import me.befell.mutualfriends.gui.GuiHead;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class GuiMutualFriends extends GuiScreen {
    private final MutualFriends mod;
    private int currentPage = 0;
    private ArrayList<GuiHead> heads = new ArrayList<>();
    private GuiTextField firstPlayer;
    private GuiTextField secondPlayer;
    private GuiHead firstHead;
    private GuiHead secondHead;
    private GuiArrowButton arrowButton;
    private GuiArrowButton nextPage;
    private GuiArrowButton prevPage;
    private String errorMessage1 = "";
    private String errorMessage2 = "";
    private String errorMessage3 = "Press the arrow to begin";

    public GuiMutualFriends(MutualFriends mod) {
        this.mod = mod;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        currentPage = 0;
        this.buttonList.add(arrowButton = new GuiArrowButton(0, this.width / 2 - 32, this.height / 30 + 5, "", false));
        this.buttonList.add(nextPage = new GuiArrowButton(1, this.width - 34, this.height / 2, "", false));
        this.buttonList.add(prevPage = new GuiArrowButton(2, 2, this.height / 2, "", true));
        if (heads.isEmpty()) {
            nextPage.visible = false;
            prevPage.visible = false;
        }

        String a = null;
        String b = null;
        if (firstPlayer != null) {
            a = firstPlayer.getText();
            b = secondPlayer.getText();
        }
        this.firstPlayer = new GuiTextField(3, this.fontRendererObj, this.width / 2 - 170, this.height / 30 + 13, 90, 16);
        this.firstPlayer.setFocused(true);
        this.secondPlayer = new GuiTextField(4, this.fontRendererObj, this.width / 2 + 40, this.height / 30 + 13, 90, 16);
        if (a != null) {
            firstPlayer.setText(a);
            secondPlayer.setText(b);
        }
        String c = null;
        String d = null;
        if (firstHead != null) {
            c = firstHead.imageString;
            d = secondHead.imageString;
        }
        this.firstHead = new GuiHead(fontRendererObj, c, "", false, true);
        this.secondHead = new GuiHead(fontRendererObj, d, "", false, true);
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
        GlStateManager.color(255.0F, 255.0F, 255.0F, 255.0F);

        firstHead.drawHead(this.width / 2 - 70, this.height / 30 + 9, mouseX, mouseY);
        secondHead.drawHead(this.width / 2 + 140, this.height / 30 + 9, mouseX, mouseY);

        drawString(fontRendererObj, errorMessage1, this.width / 2 - 170, this.height / 30, -4321007);
        drawString(fontRendererObj, errorMessage2, this.width / 2 + 40, this.height / 30, -4321007);
        drawCenteredString(fontRendererObj, errorMessage3, this.width / 2, this.height / 30 + 40, -4321007);
        waitToDraw(mouseX, mouseY);


    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.firstPlayer.mouseClicked(mouseX, mouseY, mouseButton);
        this.secondPlayer.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        this.firstPlayer.textboxKeyTyped(typedChar, keyCode);
        this.secondPlayer.textboxKeyTyped(typedChar, keyCode);

        switch (keyCode) {
            case 1:
                this.mc.displayGuiScreen(null);

                if (this.mc.currentScreen == null) {
                    this.mc.setIngameFocus();
                }
                break;
            case 15:
                if (firstPlayer.isFocused()) {
                    firstPlayer.setFocused(false);
                    secondPlayer.setFocused(true);

                } else if (secondPlayer.isFocused()) {
                    secondPlayer.setFocused(false);
                    firstPlayer.setFocused(true);
                } else {
                    firstPlayer.setFocused(true);
                }

                break;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                currentPage = 0;
                errorMessage1 = "";
                errorMessage2 = "";
                errorMessage3 = "";
                prevPage.visible = false;
                nextPage.visible = false;
                firstHead.imageString = null;
                secondHead.imageString = null;
                firstHead.reset = true;
                secondHead.reset = true;

                arrowButton.setDisabled(true);

                if (!heads.isEmpty()) {
                    heads.clear();
                }
                boolean error = false;
                final int a = firstPlayer.getText().length();
                final int b = secondPlayer.getText().length();
                if (a == 0) {
                    error = true;
                    errorMessage1 = "Empty";
                }
                if (b == 0) {
                    error = true;
                    errorMessage2 = "Empty";
                }
                if (error) {
                    arrowButton.setDisabled(false);
                    return;
                }

                check(firstPlayer.getText(), secondPlayer.getText());
                break;
            case 1:
                if (!(((this.width / 70) * 4) * (currentPage + 1) >= heads.size())) {
                    currentPage++;
                }
                break;
            case 2:
                if (currentPage != 0) {
                    currentPage--;
                }
                break;
        }

    }

    private void waitToDraw(int mouseX, int mouseY) {
        if (!heads.isEmpty()) {
            int step = 40;
            int step2 = 0;
            int i2 = 0;
            final int amount = (this.width / 70) * 4;
            for (int i = currentPage * amount; i < (currentPage + 1) * amount; i++) {
                if (i >= heads.size()) {
                    return;
                }

                if (i2 == 4) {
                    step2 = step2 + 50;
                    i2 = 0;
                }
                int x = (this.width / 6 + 5) + step2;
                int y = this.height / 2 - 10;
                if (i2 == 1) {
                    y = y + step;
                } else if (i2 == 2) {
                    y = y - step;
                } else if (i2 == 3) {
                    y = y + (step * 2);
                }
                i2++;

                heads.get(i).drawHead(x, y, mouseX, mouseY);
            }

        }
    }

    private void check(String user1, String user2) {

        this.mod.utils.poolExecutor.execute(() -> {

            TreeMap<String, String> mutualFriends = new TreeMap<>();
            ArrayList<String> targetFriends = this.mod.utils.fetchFriends(user1);
            boolean error = false;
            if (targetFriends == null) {
                error = true;
                errorMessage1 = "Invalid player";
            }
            final JsonObject jsonObject = mod.utils.fetchAshcon(user1);
            if (jsonObject.has("code")) {
                error = true;
                errorMessage1 = jsonObject.get("Error").getAsString();
            } else {
                firstHead.imageString = jsonObject.getAsJsonObject("textures").getAsJsonObject("skin").get("data").getAsString();
            }

            ArrayList<String> secondTargetFriends = this.mod.utils.fetchFriends(user2);
            if (secondTargetFriends == null) {
                error = true;
                errorMessage2 = "Invalid player";
            }
            final JsonObject jsonObject2 = mod.utils.fetchAshcon(user2);
            if (jsonObject2.has("code")) {
                error = true;
                errorMessage2 = jsonObject2.get("Error").getAsString();
            } else {
                secondHead.imageString = jsonObject2.getAsJsonObject("textures").getAsJsonObject("skin").get("data").getAsString();
            }
            if (error) {
                arrowButton.setDisabled(false);
                return;
            }

            List<Future> tasks = new ArrayList<>();
            targetFriends.forEach(friend -> {
                        if (secondTargetFriends.contains(friend)) {
                            tasks.add(this.mod.utils.poolExecutor.submit(() -> {
                                JsonObject jsonObjectData = this.mod.utils.fetchAshcon(friend);
                                if (!jsonObjectData.has("code")) {
                                    mutualFriends.put(jsonObjectData.get("username").getAsString(), jsonObjectData.getAsJsonObject("textures").getAsJsonObject("skin").get("data").getAsString());
                                }
                            }));
                        }
                    }
            );
            for (Future i : tasks) {
                try {
                    i.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            if (mutualFriends.isEmpty()) {
                errorMessage3 = "No mutual friends";
                arrowButton.setDisabled(false);
                return;
            }

            mutualFriends.forEach((username, skin) -> {
                heads.add(new GuiHead(fontRendererObj, skin, username, true, false));
            });
            arrowButton.setDisabled(false);
            prevPage.visible = true;
            nextPage.visible = true;
        });
    }

}
