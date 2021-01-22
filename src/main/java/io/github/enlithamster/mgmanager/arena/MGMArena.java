package io.github.enlithamster.mgmanager.arena;

import io.github.enlithamster.mgmanager.exceptions.PlayerDuplicateException;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public abstract class MGMArena {

    // Can be made modular through components system?
    // -> Adding modules through in-game commands/Chest GUI
    // -> Modules represent actual components of the arena, e.g. Lobby, Spawn, ...
    // -> Each module can be handled separately
    // -> Arenas define (in)compatible modules or modules define compatibility? Is compatibility necessary?
    //    Compatibility between modules?

    // => Bi-modular:
    // 1 - modules identify stages of the arena life cycle
    // 2 - each stage has a referring behaviour

    // Each stage-behaviour determines the arena parameters -> How?

    private final String name;
    private final ArrayList<Player> players;

    private boolean running;

    public MGMArena(String name) {
        this.name = name;
        this.running = false;
        this.players = new ArrayList<>();
    }

    public void start() {
        this.running = true;
    }

    public void stop() {
        this.running = false;
        this.players.clear();
    }

    public void unsubscribePlayer(Player player) {
        this.players.remove(player);
    }

    public void subscribePlayer(Player player) throws PlayerDuplicateException {
        if (this.players.contains(player))
            throw new PlayerDuplicateException(this, player);
        this.players.add(player);
    }

    public boolean isRunning() {
        return this.running;
    }

    public String getName() {
        return this.name;
    }
}
