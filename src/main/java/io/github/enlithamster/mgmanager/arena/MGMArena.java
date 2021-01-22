package io.github.enlithamster.mgmanager.arena;

public abstract class MGMArena {

    private boolean running;

    public MGMArena() {
        this.running = false;
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
