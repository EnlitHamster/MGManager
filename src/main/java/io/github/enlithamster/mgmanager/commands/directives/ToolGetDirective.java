package io.github.enlithamster.mgmanager.commands.directives;

import io.github.enlithamster.mgmanager.Utils;
import io.github.enlithamster.mgmanager.commands.MGMDirective;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ToolGetDirective extends MGMDirective {

    public ToolGetDirective() {
        super("MGManager");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player && args.length == 0) {
            Player user = (Player) sender;
            if (user.hasPermission("mgm.tool.get")) {
                if (Utils.hasAvaliableSlot(user, 1)) {
                    ItemStack tool = new ItemStack(Material.IRON_HOE);
                    ItemMeta toolMeta = tool.getItemMeta();
                    Objects.requireNonNull(toolMeta).setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "MGM Arena-tool");
                    tool.setItemMeta(toolMeta);
                    user.getInventory().addItem(tool);
                    user.sendMessage(ChatColor.GREEN + "Tool added to your inventory.");
                } else
                    user.sendMessage(ChatColor.RED + "Free some space in your inventory first.");
                return true;
            }
        }

        return false;
    }

    @Override
    protected void usage(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage(ChatColor.BLUE + "Gives you the MGMTool.");
    }

    @Override
    protected String prototype() {
        return "/mgmtool get";
    }

}
