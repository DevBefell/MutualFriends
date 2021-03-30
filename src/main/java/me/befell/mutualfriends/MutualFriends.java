package me.befell.mutualfriends;

import me.befell.mutualfriends.commands.MutualFriendsCommand;
import me.befell.mutualfriends.utils.Utils;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = MutualFriends.MODID, version = MutualFriends.VERSION, clientSideOnly = true)
public class MutualFriends {
    public static final String MODID = "mutualfriends";
    public static final String VERSION = "1.0";
    public Utils utils;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        this.utils = new Utils();
        ClientCommandHandler.instance.registerCommand(new MutualFriendsCommand(this));
    }

}
