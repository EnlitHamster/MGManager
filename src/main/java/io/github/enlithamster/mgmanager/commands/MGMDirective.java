package io.github.enlithamster.mgmanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * This interface represents a listener to the execution of a command directive.
 * It is used to register directives modularly as the framework grows and allows users of the
 * framework to interact directly with the command. It is also used to keep classes relevant
 * to a specific usage of the tool to avoid monoliths. Directives can be overloaded, but
 * registering order defines priority. MGManager directives take priority.
 */
public abstract class MGMDirective {

    public final String plugin;

    /**
     * The directive must define which plugin it refers to.
     *
     * @param plugin Plugin name
     */
    protected MGMDirective(String plugin) {
        this.plugin = plugin;
    }

    /**
     * The implementation of the directive. It fits the <code>onCommand</code> signature. Return
     * true even if the command is not applicable and define your own usage for the particular directive.
     * Return false <strong>only</strong> if the command does not match the particular usage of the executor
     * (e.g. number of arguments, sender is not a player, ...).
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    protected abstract boolean execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args);

    /**
     * The help entry of the directive. This is called whenever a user types <code>/[command] help [directive]</code>
     * in order to inform the user of proper usage.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     */
    protected abstract void usage(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args);

    /**
     * The function generates a String version of the command prototype to be shown when multiple executors are
     * registered for one directive in the <code>help</code> section of the command.
     *
     * @return command prototype
     */
    protected abstract String prototype();

}
