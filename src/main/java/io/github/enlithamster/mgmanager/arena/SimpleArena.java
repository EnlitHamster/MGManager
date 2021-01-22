package io.github.enlithamster.mgmanager.arena;

import io.github.enlithamster.mgmanager.tool.Delimiter;

public class SimpleArena extends MGMArena {

    private Delimiter area;

    public SimpleArena(String name, Delimiter area) {
        super(name);
        this.area = area;
    }

}
