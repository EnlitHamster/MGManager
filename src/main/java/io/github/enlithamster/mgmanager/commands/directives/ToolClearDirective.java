package io.github.enlithamster.mgmanager.commands.directives;

import io.github.enlithamster.mgmanager.MGManager;
import io.github.enlithamster.mgmanager.commands.MGMDirective;
import io.github.enlithamster.mgmanager.managers.ToolManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ToolClearDirective implements MGMDirective {

    private final MGManager mgm;

    public ToolClearDirective(MGManager mgm) {
        this.mgm = mgm;
    }

    @Override @NotNull
    public String getPlugin() {
        return this.mgm.getName();
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player && args.length == 0) {
            Player user = (Player) sender;
            if (user.hasPermission("mgm.tool.clear")) {
                this.mgm.getToolManager().clearAreaDelimiter(user);
                user.sendMessage(ChatColor.GREEN + "Area cleared.");
                return true;
            }
        }

        return false;
    }

    @Override
    public void usage(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage(ChatColor.BLUE + "Clears the selection made with the MGM Tool.");
    }

    @Override
    public String prototype() {
        return "/mgmtool clear";
    }

}
