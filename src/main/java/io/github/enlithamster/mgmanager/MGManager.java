package io.github.enlithamster.mgmanager;

import io.github.enlithamster.mgmanager.managers.ArenaManager;
import io.github.enlithamster.mgmanager.managers.ToolManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MGManager extends JavaPlugin {

    private ToolManager toolMng;
    private ArenaManager arenaMng;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.toolMng = new ToolManager(this);
        this.arenaMng = new ArenaManager(this);

        // --- Logging
        this.getLogger().info("mini-games manager enabled.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.getLogger().info("mini-games manager disabled.");
    }

    public ToolManager getToolManager() {
        return this.toolMng;
    }

    public ArenaManager getArenaManager() {
        return this.arenaMng;
    }

}
