package io.github.enlithamster.mgmanager.commands;

import io.github.enlithamster.mgmanager.commands.directives.ToolClearDirective;
import io.github.enlithamster.mgmanager.commands.directives.ToolGetDirective;
import io.github.enlithamster.mgmanager.commands.directives.ToolHighlightDirective;
import io.github.enlithamster.mgmanager.managers.ToolManager;

/**
 * These commands are only callable by a player. They are used to manipualte
 * the world and require them to be used by someone. Other command senders are
 * unable to use them as these would not be able to be applied.
 */
public class ToolCommands extends MGMCommandExecutor {

    public ToolCommands(ToolManager tm) {
        super();

        this.registerDirectiveExecutor("get", new ToolGetDirective());
        this.registerDirectiveExecutor("clear", new ToolClearDirective(tm));
        this.registerDirectiveExecutor("highlight", new ToolHighlightDirective(tm));
    }

    @Override
    protected boolean usage() {
        return false;
    }

}
