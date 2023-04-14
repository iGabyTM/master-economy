package me.gabytm.minecraft.mastereconomy.spigot.commands;

import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.SubCommand;
import me.gabytm.minecraft.mastereconomy.common.redis.Method;
import me.gabytm.minecraft.mastereconomy.common.redis.ServerMessenger;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command("meco")
public class BalanceCommand extends BaseCommand {

    private final ServerMessenger serverMessenger;

    public BalanceCommand(ServerMessenger serverMessenger) {
        this.serverMessenger = serverMessenger;
    }

    @SubCommand("add")
    public void balanceAddCommand(CommandSender sender, Player player, String economy, double amount) {
        serverMessenger.modifyBalance(player.getUniqueId(), Method.ADD, economy, amount);
    }

    @SubCommand("set")
    public void balanceSetCommand(CommandSender sender, Player player, String economy, double amount) {
        serverMessenger.modifyBalance(player.getUniqueId(), Method.SET, economy, amount);
    }

    @SubCommand("remove")
    public void balanceRemoveCommand(CommandSender sender, Player player, String economy, double amount) {
        serverMessenger.modifyBalance(player.getUniqueId(), Method.SUBTRACT, economy, amount);
    }

}
