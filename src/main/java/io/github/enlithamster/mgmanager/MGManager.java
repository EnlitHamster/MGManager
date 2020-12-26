package io.github.enlithamster.mgmanager;

import io.github.enlithamster.mgmanager.managers.ToolManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MGManager extends JavaPlugin {

    private ToolManager toolMng;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.toolMng = new ToolManager(this);

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

}
