package io.github.enlithamster.mgmanager.commands.directives;

import io.github.enlithamster.mgmanager.commands.MGMCommandExecutor;
import io.github.enlithamster.mgmanager.managers.ToolManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ToolClearDirective implements MGMCommandExecutor.MGMDirective {

    private final ToolManager toolManager;

    public ToolClearDirective(ToolManager tm) {
        this.toolManager = tm;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player && args.length == 0) {
            Player user = (Player) sender;
            if (user.hasPermission("mgm.tool.clear")) {
                if (user.getInventory().getItemInMainHand().getType().equals(Material.IRON_HOE)) {
                    this.toolManager.clearAreaDelimiter(user);
                    user.sendMessage(ChatColor.GREEN + "Area cleared.");
                    return true;
                } else
                    user.sendMessage(ChatColor.RED + "You must hold the MGM tool for which you want the area highlight.");
            }
        }

        return false;
    }

}
