package io.github.enlithamster.mgmanager.exceptions;

import io.github.enlithamster.mgmanager.arena.MGMArena;
import org.bukkit.entity.Player;

public class PlayerDuplicateException extends Exception {

    public PlayerDuplicateException(MGMArena arena, Player player) {
        super("Duplicate player subscription for " + player.getDisplayName() + " in arena " + arena.getName());
    }

}
