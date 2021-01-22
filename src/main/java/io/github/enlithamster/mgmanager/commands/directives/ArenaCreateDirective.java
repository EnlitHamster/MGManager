package io.github.enlithamster.mgmanager.commands.directives;

import io.github.enlithamster.mgmanager.MGManager;
import io.github.enlithamster.mgmanager.commands.MGMDirective;
import io.github.enlithamster.mgmanager.tool.Delimiter;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ArenaCreateDirective extends MGMDirective {

    private final MGManager mgm;

    public ArenaCreateDirective(MGManager mgm) {
        super("MGManager");
        this.mgm = mgm;
    }

    @Override
    protected boolean execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player && args.length == 1) {
            Player user = (Player) sender;
            if (user.hasPermission("mgm.arena.create")) {
                String name = args[0];
                Delimiter area = this.mgm.getToolManager().getAreaDelimiter(user);
                if (this.mgm.getArenaManager().createArena(name, area))
                    user.sendMessage(ChatColor.GREEN + "Arena " + name + " created.");
                else
                    user.sendMessage(ChatColor.RED + "Arena " + name + " already existing.");
                return true;
            }
        }

        return false;
    }

    @Override
    protected void usage(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage(ChatColor.BLUE + "Creates a new arena with the given name using the MGM Tool selection.");
    }

    @Override
    protected String prototype() {
        return "/mgmarena create <name>";
    }

}
