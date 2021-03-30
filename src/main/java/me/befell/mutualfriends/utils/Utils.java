package me.befell.mutualfriends.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Utils {
    private final String slothURL = "https://api.slothpixel.me/api/players/%s/friends";
    private final String ashconURL = "https://api.ashcon.app/mojang/v2/user/%s";
    private final String modMessage = "\u00A7b[\u00A73MutualFriends\u00A7b] ";
    public Minecraft minecraft;
    public ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(50);

    public Utils() {
        this.minecraft = Minecraft.getMinecraft();
    }

    public List<String> getColorCodes(){
        List<String> colorCodes = new ArrayList<>();
        colorCodes.add("\u00A74");
        colorCodes.add("\u00A7c");
        colorCodes.add("\u00A76");
        colorCodes.add("\u00A7e");
        colorCodes.add("\u00A72");
        colorCodes.add("\u00A7a");
        colorCodes.add("\u00A7b");
        colorCodes.add("\u00A73");
        colorCodes.add("\u00A71");
        colorCodes.add("\u00A79");
        colorCodes.add("\u00A7d");
        colorCodes.add("\u00A75");
        colorCodes.add("\u00A77");
        colorCodes.add("\u00A78");
        return colorCodes;
    }
    public void send(String msg, boolean title) {
        this.minecraft.thePlayer.addChatMessage(new ChatComponentText((title ? modMessage : "") + msg));
    }

    public void send(String msg) {
        this.send(msg, false);
    }

    public void sendBreakline() {
        StringBuilder dashes = new StringBuilder();
        for (int i = 0; i <= 18; i++) {
            dashes.append("-");
        }
        this.send("\u00A73\u00a7m" + dashes.toString());
    }

    public String fetchString(String url) {
        StringBuilder content = new StringBuilder();
        try {
            URL urlData = new URL(url);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlData.openStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();

        } catch (IOException ignored) {

        }
        if (content.length() == 0) {
            return null;
        }
        return content.toString();
    }

    public String fetchUsername(String uuid) {
        String content = fetchString(String.format(this.ashconURL, uuid));
        if (content == null) {
            return null;
        }
        JsonObject jsonData = new Gson().fromJson(content, JsonObject.class);
        if (jsonData.has("code")) {
            return null;
        }
        return jsonData.get("username").getAsString();
    }

    public ArrayList<String> fetchFriends(String user) {
        String content = fetchString(String.format(this.slothURL, user));
        if (content == null) {
            return null;
        }
        JsonArray jsonData = new Gson().fromJson(content, JsonArray.class);
        ArrayList<String> friends = new ArrayList<>();
        jsonData.forEach(e -> friends.add(e.getAsJsonObject().get("uuid").getAsString()));
        return friends;
    }

    public void run(Runnable runnable) {
        poolExecutor.execute(runnable);
    }
}
