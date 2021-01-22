package io.github.enlithamster.mgmanager.managers;

import io.github.enlithamster.mgmanager.MGManager;
import io.github.enlithamster.mgmanager.commands.ToolCommands;
import io.github.enlithamster.mgmanager.commands.directives.ToolClearDirective;
import io.github.enlithamster.mgmanager.commands.directives.ToolGetDirective;
import io.github.enlithamster.mgmanager.commands.directives.ToolHighlightDirective;
import io.github.enlithamster.mgmanager.handlers.ToolHandler;
import io.github.enlithamster.mgmanager.exceptions.DifferentWorldsException;
import io.github.enlithamster.mgmanager.tool.Delimiter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

public class ToolManager {

    // Logic components
    private final HashMap<Player, Delimiter> playerAreaDelimiters;
    private final MGManager mgm;

    // Commands
    private final ToolCommands toolCmd;

    // Handlers
    private final ToolHandler toolHandler;

    public ToolManager(@NotNull MGManager mgm) {
        this.mgm = mgm;

        // --- Initializing registers
        this.playerAreaDelimiters = new HashMap<>();

        // --- Initializing components
        this.toolCmd = new ToolCommands();
        this.toolHandler = new ToolHandler(this);

        // --- Registering command directives
        this.toolCmd.registerDirectiveExecutor("get", new ToolGetDirective());
        this.toolCmd.registerDirectiveExecutor("clear", new ToolClearDirective(this));
        this.toolCmd.registerDirectiveExecutor("highlight", new ToolHighlightDirective(this));

        // --- Registering handlers & executors
        Objects.requireNonNull(this.mgm.getCommand("mgmtool")).setExecutor(this.toolCmd);
        this.mgm.getServer().getPluginManager().registerEvents(this.toolHandler, this.mgm);
    }

    public void setPlayerDelimiterLocation1(@NotNull Player user, @NotNull Location loc1) throws DifferentWorldsException {
        if (!this.playerAreaDelimiters.containsKey(user))
            this.playerAreaDelimiters.put(user, new Delimiter(user, loc1, null));
        else
            this.playerAreaDelimiters.get(user).setLoc1(user, loc1);
    }

    public void setPlayerDelimiterLocation2(@NotNull Player user, @NotNull Location loc2) throws DifferentWorldsException {
        if (!this.playerAreaDelimiters.containsKey(user))
            this.playerAreaDelimiters.put(user, new Delimiter(user, null, loc2));
        else
            this.playerAreaDelimiters.get(user).setLoc2(user, loc2);
    }

    public Delimiter getAreaDelimiter(@NotNull Player user) {
        try {
            return this.playerAreaDelimiters.get(user);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public void clearAreaDelimiter(@NotNull Player user) {
        this.playerAreaDelimiters.put(user, new Delimiter());
    }
}
