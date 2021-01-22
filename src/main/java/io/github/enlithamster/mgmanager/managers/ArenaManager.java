package io.github.enlithamster.mgmanager.managers;

import io.github.enlithamster.mgmanager.MGManager;
import io.github.enlithamster.mgmanager.arena.MGMArena;
import io.github.enlithamster.mgmanager.arena.SimpleArena;
import io.github.enlithamster.mgmanager.commands.ArenaCommands;
import io.github.enlithamster.mgmanager.commands.directives.ArenaCreateDirective;
import io.github.enlithamster.mgmanager.commands.directives.ArenaListDirective;
import io.github.enlithamster.mgmanager.tool.Delimiter;
import io.github.enlithamster.mgmanager.tool.functors.ArenaFunctor;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

public class ArenaManager {

    // Logic components
    private final HashMap<String, MGMArena> arenas;
    private final MGManager mgm;

    // Commands
    private final ArenaCommands arenaCmd;

    public ArenaManager(@NotNull MGManager mgm) {
        this.mgm = mgm;

        // --- Initializing registers
        this.arenas = new HashMap<>();

        // --- Initializing components
        this.arenaCmd = new ArenaCommands();

        // --- Registering command directives
        this.arenaCmd.registerDirectiveExecutor("create", new ArenaCreateDirective(this.mgm));
        this.arenaCmd.registerDirectiveExecutor("list", new ArenaListDirective(this));

        // --- Registering handlers & executors
        Objects.requireNonNull(this.mgm.getCommand("mgmarena")).setExecutor(this.arenaCmd);
    }

    public boolean createArena(String name, Delimiter area) {
        if (area != null && !this.arenas.containsKey(name)) {
            this.arenas.put(name, new SimpleArena(area));
            return true;
        }

        return false;
    }

    public void forEach(ArenaFunctor functor) {
        for (String name : this.arenas.keySet())
            functor.apply(name, this.arenas.get(name));
    }
}
