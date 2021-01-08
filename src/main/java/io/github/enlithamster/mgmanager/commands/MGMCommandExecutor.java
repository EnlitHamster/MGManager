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
