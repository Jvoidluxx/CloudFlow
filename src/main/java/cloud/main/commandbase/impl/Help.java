package cloud.main.commandbase.impl;

import cloud.main.commandbase.Command;
import cloud.main.commandbase.CommandManager;
import cloud.main.module.Manager;

import java.util.List;

public class Help extends Command {
    public Help() {
        super("help", "Shows all commands", ".help (optional) {commandName}", "h", "cmds", "?");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length == 0) {
            List<Command> commands = CommandManager.getCommands();
            for (Command c : commands) {
                Manager.addClientMessage(formatCommand(c));
            }
        } else if (args.length == 1) {
            String commandName = args[0];
            List<Command> commands = CommandManager.getCommands();
            for (Command c : commands) {
                if (c.name.equalsIgnoreCase(commandName) || c.aliases.contains(commandName)) {
                    Manager.addClientMessage(formatCommand(c));
                }
            }
        }
    }

    private String formatCommand(Command c) {
        return String.format("%s - %s - %s - %s", c.getName(), c.getDesc(), c.getSyntax(), c.getAliases());
    }
}
