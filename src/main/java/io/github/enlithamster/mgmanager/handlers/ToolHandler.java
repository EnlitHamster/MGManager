package io.github.enlithamster.mgmanager.handlers;

import io.github.enlithamster.mgmanager.exceptions.DifferentWorldsException;
import io.github.enlithamster.mgmanager.managers.ToolManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class ToolHandler implements Listener {

    private final ToolManager toolManager;

    public ToolHandler(ToolManager tm) {
        this.toolManager = tm;
    }

    @EventHandler
    public void onPlayerBlockBreak(BlockBreakEvent event) {
        Player user = event.getPlayer();
        if (user.getInventory().getItemInMainHand().getType().equals(Material.IRON_HOE)
            && user.hasPermission("mgm.tool.use")) {
            try {
                Location loc = event.getBlock().getLocation();
                this.toolManager.setPlayerDelimiterLocation1(user, loc);
                user.sendMessage(ChatColor.GREEN +
                        "Location 1 set at (" + loc.getBlockX() +
                        ", " + loc.getBlockY() +
                        ", " + loc.getBlockZ() +
                        ") in " + Objects.requireNonNull(loc.getWorld()).getName() + ".");
            } catch (DifferentWorldsException dwe) {
                user.sendMessage(ChatColor.RED + dwe.toString());
            } finally {
                // Do not destroy block
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player user = event.getPlayer();
        if (user.getInventory().getItemInMainHand().getType().equals(Material.IRON_HOE)
            && user.hasPermission("mgm.tool.use")) {
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                try {
                    Location loc = Objects.requireNonNull(event.getClickedBlock()).getLocation();
                    this.toolManager.setPlayerDelimiterLocation2(user, loc);
                    user.sendMessage(ChatColor.GREEN +
                            "Location 2 set at (" + loc.getBlockX() +
                            ", " + loc.getBlockY() +
                            ", " + loc.getBlockZ() +
                            ") in " + Objects.requireNonNull(loc.getWorld()).getName() + ".");
                } catch (DifferentWorldsException dwe) {
                    user.sendMessage(ChatColor.RED + dwe.toString());
                } finally {
                    // Do not hoe, Sniffany
                    event.setCancelled(true);
                }
            }
        }
    }

}
