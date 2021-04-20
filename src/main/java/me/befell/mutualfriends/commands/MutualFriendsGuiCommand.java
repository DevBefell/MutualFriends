package me.befell.mutualfriends.commands;

import me.befell.mutualfriends.MutualFriends;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class MutualFriendsGuiCommand extends CommandBase {
    private MutualFriends mod;

    public MutualFriendsGuiCommand(MutualFriends mod) {
        this.mod = mod;
    }

    @Override
    public String getCommandName() {
        return "mfrgui";
    }

    public void processCommand(ICommandSender sender, String[] args) {
        this.mod.openGui();
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + getCommandName();
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
