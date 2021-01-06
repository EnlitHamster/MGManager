package io.github.enlithamster.mgmanager.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

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
    public static abstract class MGMDirective {

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
         * @param sender Source of the command
         * @param command Command which was executed
         * @param label Alias of the command which was used
         * @param args Passed command arguments
         *
         * @return true if a valid command, otherwise false
         */
        protected abstract boolean execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args);

        /**
         * The help entry of the directive. This is called whenever a user types <code>/[command] help [directive]</code>
         * in order to inform the user of proper usage.
         *
         * @param sender Source of the command
         * @param command Command which was executed
         * @param label Alias of the command which was used
         * @param args Passed command arguments
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
        if (!this.directives.containsKey(directive))
            this.directives.put(directive, new ArrayList<>());
        this.directives.get(directive).add(executor);
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
        if (this.directives.containsKey(directive)) {
            boolean ret = this.directives.get(directive).remove(executor);
            if (this.directives.get(directive).size() == 0)
                this.directives.remove(directive);
            return ret;
        }
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
     * It also implements the help functionality.
     *
     * @param sender Source of the command
     * @param command Command which was executed
     * @param label Alias of the command which was used
     * @param args Passed command arguments
     *
     * @return true if a valid command, otherwise false
     */
    protected final boolean dispatch(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("help")))
            return this.usage(sender, command, label, args);
        else if (args[0].equalsIgnoreCase("help")) {
            String directive = args[1].toLowerCase();
            if (this.directives.containsKey(directive)) {
                ArrayList<MGMDirective> executors = this.directives.get(directive);
                int nExecutors = executors.size();
                if (nExecutors == 1) {
                    executors.get(0).usage(sender, command, label, Arrays.copyOfRange(args, 2, args.length));
                } else if (args.length == 3) {
                    try {
                        executors.get(Integer.parseInt(args[2])).usage(sender, command, label, args);
                    } catch (NumberFormatException e) {
                        sender.sendMessage(ChatColor.RED + "Usage: /" + command.getName() + " help " + directive + " [number].");
                    }
                } else {
                    sender.sendMessage(ChatColor.BLUE.toString() + nExecutors + " usages found.");
                    for (int i = 0; i < nExecutors; i++) {
                        MGMDirective exec = executors.get(i);
                        String message = "[" + i + "] " + exec.plugin + ": " + exec.prototype();
                        sender.sendMessage(ChatColor.GREEN + message);
                    }
                }
            } else
                sender.sendMessage(ChatColor.RED + "No \"" + directive + "\" directive found.");
        } else {
            String directive = args[0].toLowerCase();
            if (this.directives.containsKey(directive)) {
                Iterator<MGMDirective> executor = this.directives.get(directive).iterator();
                boolean correctDirective = false;
                // Base case: iterator = the first element of the ArrayList.
                // The iterator calls the next() function at every cycle, which moves the
                // iterator forward, guaranteeing the termination of the loop.
                while (executor.hasNext() && !correctDirective)
                    correctDirective = executor.next().execute(sender, command, label, Arrays.copyOfRange(args, 1, args.length));

                if (!correctDirective)
                    sender.sendMessage(ChatColor.RED + "No directive found that could handle this commnad");
            }
        }

        // The false case is handled through the usage methods and is not left to the Spigot framework
        return true;
    }

    /**
     * Definition of the normal call of the command.
     *
     * @param sender Source of the command
     * @param command Command which was executed
     * @param label Alias of the command which was used
     * @param args Passed command arguments
     *
     * @return true if a valid command, otherwise false
     */
    protected boolean usage(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage(ChatColor.BLUE + "Registered directives: " + String.join(", ", this.listDirectives()));
        return true;
    }

    /**
     * Returns the list of directives registered on this command.
     *
     * @return directives list
     */
    protected ArrayList<String> listDirectives() {
        return new ArrayList<>(this.directives.keySet());
    }

}
