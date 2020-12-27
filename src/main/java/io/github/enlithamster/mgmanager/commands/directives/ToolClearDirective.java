package io.github.enlithamster.mgmanager.commands.directives;

import io.github.enlithamster.mgmanager.commands.MGMCommandExecutor;
import io.github.enlithamster.mgmanager.managers.ToolManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ToolClearDirective extends MGMCommandExecutor.MGMDirective {

    private final ToolManager toolManager;

    public ToolClearDirective(ToolManager tm) {
        super("MGManager");
        this.toolManager = tm;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player && args.length == 0) {
            Player user = (Player) sender;
            if (user.hasPermission("mgm.tool.clear")) {
                this.toolManager.clearAreaDelimiter(user);
                user.sendMessage(ChatColor.GREEN + "Area cleared.");
                return true;
            }
        }

        return false;
    }

    @Override
    protected void usage(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage(ChatColor.BLUE + "Clears the selection made with the MGM Tool.");
    }

    @Override
    protected String prototype() {
        return "/mgmtool clear";
    }

}
