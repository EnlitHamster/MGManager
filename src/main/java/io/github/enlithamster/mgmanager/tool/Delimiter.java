package io.github.enlithamster.mgmanager.tool;

import io.github.enlithamster.mgmanager.exceptions.DifferentWorldsException;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Delimiter {

    private Location loc1;
    private Location loc2;

    public Delimiter() {
        this.loc1 = null;
        this.loc2 = null;
    }

    public Delimiter(@NotNull Player user, Location loc1, Location loc2) throws DifferentWorldsException {
        if (loc1 != null && loc2 != null && !Objects.equals(loc1.getWorld(), loc2.getWorld()))
            throw new DifferentWorldsException(user, Objects.requireNonNull(loc1.getWorld()), Objects.requireNonNull(loc2.getWorld()));

        this.loc1 = loc1;
        this.loc2 = loc2;
    }

    public void setLoc1(@NotNull Player user, @NotNull Location loc1) throws DifferentWorldsException {
        if (this.loc2 != null && !Objects.equals(loc1.getWorld(), this.loc2.getWorld()))
            throw new DifferentWorldsException(user, Objects.requireNonNull(loc1.getWorld()), Objects.requireNonNull(this.loc2.getWorld()));
        this.loc1 = loc1;
    }

    public void setLoc2(@NotNull Player user, @NotNull Location loc2) throws DifferentWorldsException {
        if (this.loc1 != null && !Objects.equals(this.loc1.getWorld(), loc2.getWorld()))
            throw new DifferentWorldsException(user, Objects.requireNonNull(loc2.getWorld()), Objects.requireNonNull(this.loc1.getWorld()));
        this.loc2 = loc2;
    }

    public boolean isDefined() {
        return loc1 != null && loc2 != null;
    }

    public Location getLoc1() {
        return loc1;
    }

    public Location getLoc2() {
        return loc2;
    }
}
