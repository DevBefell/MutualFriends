package me.befell.mutualfriends.commands;

import me.befell.mutualfriends.MutualFriends;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MutualFriendsCommand extends CommandBase {

    private MutualFriends mod;

    public MutualFriendsCommand(MutualFriends mod) {
        this.mod = mod;
    }

    @Override
    public String getCommandName() {
        return "mfr";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + getCommandName();
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0 || args.length > 2) {
            this.mod.utils.send("\u00A7cSpecify a user or two users. \u00A76(/mfr [user | (user user)]", true);
            return;
        }
        ArrayList<String> message = new ArrayList<>();
        this.mod.utils.poolExecutor.execute(() -> {
            boolean typeLength = args.length == 1;
            String player1 = typeLength ? this.mod.utils.minecraft.thePlayer.getName() : args[0];
            ArrayList<String> targetFriends = this.mod.utils.fetchFriends(player1);
            if (targetFriends == null) {
                this.mod.utils.send("\u00A7c" + player1 + " is an invalid player", true);
                return;
            }
            String player2 = typeLength ? args[0] : args[1];
            ArrayList<String> secondTargetFriends = this.mod.utils.fetchFriends(player2);
            if (secondTargetFriends == null) {
                this.mod.utils.send("\u00A7c" + player2 + " is an invalid player", true);
                return;
            }

            List<Future> tasks = new ArrayList<>();
            targetFriends.forEach(friend -> {
                        if (secondTargetFriends.contains(friend)) {
                            tasks.add(this.mod.utils.poolExecutor.submit(() -> message.add(this.mod.utils.fetchUsername(friend))));
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
            if (message.isEmpty()) {
                this.mod.utils.send("No mutual friends", true);
                return;
            }
            List<String> colorCodes = this.mod.utils.getColorCodes();
            this.mod.utils.sendBreakline();
            message.forEach(same -> {
                String color = colorCodes.get(0);
                this.mod.utils.send(color + same);
                colorCodes.remove(0);
                colorCodes.add(color);
            });
            this.mod.utils.sendBreakline();
        });

    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
