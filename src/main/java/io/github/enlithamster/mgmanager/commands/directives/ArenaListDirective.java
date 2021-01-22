package io.github.enlithamster.mgmanager.commands.directives;

import io.github.enlithamster.mgmanager.MGManager;
import io.github.enlithamster.mgmanager.arena.MGMArena;
import io.github.enlithamster.mgmanager.commands.MGMDirective;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ArenaListDirective implements MGMDirective {

    private final MGManager mgm;

    public ArenaListDirective(MGManager mgm) {
        this.mgm = mgm;
    }

    @Override @NotNull
    public String getPlugin() {
        return this.mgm.getName();
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.BLUE.toString() + ChatColor.BOLD.toString() + "Registered arenas");
            this.mgm.getArenaManager().forEach((String name, MGMArena arena) ->
                    sender.sendMessage(ChatColor.BLUE + "- " + name + (arena.isRunning() ? ": running" : "")));

            return true;
        }

        return false;
    }

    @Override
    public void usage(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

    }

    @Override
    public String prototype() {
        return null;
    }

}
