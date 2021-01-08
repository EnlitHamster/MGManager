package io.github.enlithamster.mgmanager.arena;

public abstract class MGMArena {

    private boolean running;

    public final String name;

    protected MGMArena(String name) {
        this.running = false;
        this.name = name;
    }

    public void start() {
        this.running = true;
    }

    public void stop() {
        this.running = false;
    }

    public boolean isRunning() {
        return this.running;
    }

}
