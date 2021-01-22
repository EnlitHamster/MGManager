package io.github.enlithamster.mgmanager.commands.directives;

import io.github.enlithamster.mgmanager.arena.MGMArena;
import io.github.enlithamster.mgmanager.commands.MGMDirective;
import io.github.enlithamster.mgmanager.managers.ArenaManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ArenaListDirective extends MGMDirective {

    private final ArenaManager arenaMng;

    public ArenaListDirective(ArenaManager arenaMng) {
        super("MGManager");
        this.arenaMng = arenaMng;
    }

    @Override
    protected boolean execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.BLUE.toString() + ChatColor.BOLD.toString() + "Registered arenas");
            this.arenaMng.forEach((String name, MGMArena arena) ->
                    sender.sendMessage(ChatColor.BLUE + "- " + name + (arena.isRunning() ? ": running" : "")));

            return true;
        }

        return false;
    }

    @Override
    protected void usage(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

    }

    @Override
    protected String prototype() {
        return null;
    }

}
