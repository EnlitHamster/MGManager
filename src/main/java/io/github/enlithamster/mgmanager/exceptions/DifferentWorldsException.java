package io.github.enlithamster.mgmanager.exceptions;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DifferentWorldsException extends Exception {

    private final Player user;
    private final World wld1;
    private final World wld2;
    private String cmd;

    public DifferentWorldsException(@NotNull Player user, @NotNull World wld1, @NotNull World wld2) {
        this.user = user;
        this.wld1 = wld1;
        this.wld2 = wld2;
        this.cmd = "mgm tool";
    }

    @Override
    public String toString() {
        return "Player " + user.getDisplayName() +
                " tried to set locations in different worlds (" + wld1.getName() +
                ", " + wld2.getName() +
                ") using " + cmd + ".";
    }

    public boolean setCommand(String cmd) {
        if (cmd != null && !cmd.isEmpty()) {
            this.cmd = cmd;
            return true;
        }

        return false;
    }

}
