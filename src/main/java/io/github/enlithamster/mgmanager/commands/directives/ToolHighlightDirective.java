package io.github.enlithamster.mgmanager.commands.directives;

import io.github.enlithamster.mgmanager.Utils;
import io.github.enlithamster.mgmanager.commands.MGMCommandExecutor;
import io.github.enlithamster.mgmanager.managers.ToolManager;
import io.github.enlithamster.mgmanager.managers.tools.Delimiter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ToolHighlightDirective implements MGMCommandExecutor.MGMDirective {

    private final ToolManager toolManager;

    public ToolHighlightDirective(ToolManager tm) {
        this.toolManager = tm;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player && args.length <= 1) {
            Player user = (Player) sender;
            if (user.hasPermission("mgm.tool.highlight")) {
                if (user.getInventory().getItemInMainHand().getType().equals(Material.IRON_HOE)) {
                    Delimiter delimiter = this.toolManager.getAreaDelimiter(user);
                    if (delimiter.isDefined()) {
                        Location loc1 = delimiter.getLoc1();
                        Location loc2 = delimiter.getLoc2();
                        Material mat = (args.length == 1 ? Material.getMaterial(args[0]) : Material.BLUE_WOOL);

                        if (mat == null)
                            user.sendMessage(ChatColor.RED + "Material \"" + args[0] + "\" does not exist.");
                        else {
                            drawArena(loc1, loc2, Math.max(loc1.getBlockY(), loc2.getBlockY()), mat);
                            user.sendMessage(ChatColor.GREEN + "Area highlighted.");
                        }

                        return true;
                    } else
                        user.sendMessage(ChatColor.RED + "Define a region first.");
                } else
                    user.sendMessage(ChatColor.RED + "You must hold the MGM tool for which you want the area highlight.");
            }
        }

        return false;
    }

    private static void drawArena(Location loc1, Location loc2, int y, Material material) {
        World world = Objects.requireNonNull(loc1.getWorld());

        int x1 = loc1.getBlockX();
        int x2 = loc2.getBlockX();
        int z1 = loc1.getBlockZ();
        int z2 = loc2.getBlockZ();

        Utils.lineBetween(world, x1, y, z1, x2, y, z1, material);
        Utils.lineBetween(world, x2, y, z1, x2, y, z2, material);
        Utils.lineBetween(world, x2, y, z2, x1, y, z2, material);
        Utils.lineBetween(world, x1, y, z2, x1, y, z1, material);
    }

}
