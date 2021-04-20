package me.befell.mutualfriends;

import me.befell.mutualfriends.commands.MutualFriendsCommand;
import me.befell.mutualfriends.commands.MutualFriendsGuiCommand;
import me.befell.mutualfriends.gui.screens.GuiMutualFriends;
import me.befell.mutualfriends.utils.Utils;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod(modid = MutualFriends.MODID, version = MutualFriends.VERSION, clientSideOnly = true)
public class MutualFriends {
    public static final String MODID = "mutualfriends";
    public static final String VERSION = "1.1";
    public Utils utils;
    private boolean openGui;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        this.utils = new Utils();
        final ClientCommandHandler commandHandler = ClientCommandHandler.instance;
        commandHandler.registerCommand(new MutualFriendsCommand(this));
        commandHandler.registerCommand(new MutualFriendsGuiCommand(this));
        final EventBus bus = MinecraftForge.EVENT_BUS;
        bus.register(this);

    }

    @SubscribeEvent
    public void OnTick(TickEvent.ClientTickEvent e) {
        if (openGui) {
            this.utils.minecraft.displayGuiScreen(new GuiMutualFriends(this));
            openGui = false;
        }
    }

    public void openGui() {
        this.openGui = true;
    }
}
