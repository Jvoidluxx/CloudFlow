package cloud.main.commandbase;

import cloud.main.commandbase.impl.Adjust;
import cloud.main.commandbase.impl.Bind;
import cloud.main.commandbase.impl.Config;
import cloud.main.commandbase.impl.Help;
import cloud.main.events.impl.EventChat;
import cloud.main.module.Manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {
    public static List<Command> commands = new ArrayList<Command>();
    public String prefix = ".";
    public CommandManager() {
        setup();
    }
    public void setup() {
        commands.add(new Bind());
        commands.add(new Adjust());
        commands.add(new Config());
        commands.add(new Help());
    }
    public static List<Command> getCommands() {
        return commands;
    }
    public void handleChat(EventChat e) {
        String msg = e.getMessage();
        if (!msg.startsWith(prefix)) return;
        e.setCanceled(true);
        msg = msg.substring(prefix.length());
        boolean foundCommand = false;
        if (msg.split((" ")).length > 0) {
            String commandName = msg.split((" "))[0];
            for (Command command : commands) {
                if (command.aliases.contains(commandName) || command.name.equalsIgnoreCase(commandName)) {
                    command.onCommand(Arrays.copyOfRange(msg.split((" ")), 1, msg.split((" ")).length), commandName);
                    foundCommand = true;
                    break;
                }
            }
        }
        if (!foundCommand) {
            Manager.addClientMessage("Command Not Found");
        }

    }
}
