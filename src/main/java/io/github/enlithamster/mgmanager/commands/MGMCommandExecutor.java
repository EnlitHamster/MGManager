package io.github.enlithamster.mgmanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public abstract class MGMCommandExecutor implements CommandExecutor {

    /**
     * Registered directive executors for the command
     */
    private final HashMap<String, ArrayList<MGMDirective>> directives;

    /**
     * This interface represents a listener to the execution of a command directive.
     * It is used to register directives modularly as the framework grows and allows users of the
     * framework to interact directly with the command. It is also used to keep classes relevant
     * to a specific usage of the tool to avoid monoliths. Directives can be overloaded, but
     * registering order defines priority. MGManager directives take priority.
     */
    public interface MGMDirective {

        /**
         * The implementation of the directive. It fits the <code>onCommand</code> signature. Return
         * true even if the command is not applicable and define your own usage for the particular directive.
         * Return false <strong>only</strong> if the command does not match the particular usage of the executor
         * (e.g. number of arguments, sender is not a player, ...).
         *
         * @param sender Source of the command
         * @param command Command which was executed
         * @param label Alias of the command which was used
         * @param args Passed command arguments
         *
         * @return true if a valid command, otherwise false
         */
        boolean execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args);

    }

    protected MGMCommandExecutor() {
        this.directives = new HashMap<>();
    }

    /**
     * Adds a new directive executor at the lowest priority in the list of executors
     * for the given directive. The directive is case insensitive.
     *
     * @param strDirective registering directive
     * @param executor directive listener
     */
    public void registerDirectiveExecutor(@NotNull String strDirective, @NotNull MGMDirective executor) {
        String directive = strDirective.toLowerCase();
        if (!directives.containsKey(directive))
            directives.put(directive, new ArrayList<>());
        directives.get(directive).add(executor);
    }

    /**
     * Removes a directive executor from the executors list. The directive is case insensitive.
     *
     * @param strDirective unregistering directive
     * @param executor directive listener
     *
     * @return true if executor was present and removed, false otherwise
     */
    public boolean unregisterDirectiveExecutor(@NotNull String strDirective, @NotNull MGMDirective executor) {
        String directive = strDirective.toLowerCase();
        if (directives.containsKey(directive))
            return directives.get(directive).remove(executor);
        return false;
    }

    /**
     * Acts as de facto dispatcher for the directives.
     *
     * @param sender Source of the command
     * @param command Command which was executed
     * @param label Alias of the command which was used
     * @param args Passed command arguments
     *
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return this.dispatch(sender, command, label, args);
    }

    /**
     * Dispatch logic of the Command executor. <code>args</code> is passed without the first element (the directive).
     *
     * @param sender Source of the command
     * @param command Command which was executed
     * @param label Alias of the command which was used
     * @param args Passed command arguments
     *
     * @return true if a valid command, otherwise false
     */
    protected final boolean dispatch(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0)
            return this.usage();
        else {
            String directive = args[0].toLowerCase();
            if (directives.containsKey(directive)) {
                for (MGMDirective executor : directives.get(directive))
                    if (executor.execute(sender, command, label, Arrays.copyOfRange(args, 1, args.length)))
                        return true;
                return false;
            }
        }

        return false;
    }

    /**
     * Definition of the normal call of the command.
     *
     * @return true if a valid command, otherwise false
     */
    protected abstract boolean usage();

}
