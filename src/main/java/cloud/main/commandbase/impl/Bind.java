package cloud.main.commandbase.impl;

import cloud.main.commandbase.Command;
import cloud.main.module.Manager;
import cloud.main.module.Module;
import org.lwjgl.input.Keyboard;

public class Bind extends Command {
    public Bind() {
        super("bind", "", "bind {module name} {key}", "b");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length == 2) {
            String module = args[0];
            String key = args[1];
            boolean found = false;
            for (Module m : Manager.modules) {
                if (m.name.equalsIgnoreCase(module)) {
                    m.setKey(Keyboard.getKeyIndex(key.toUpperCase()));
                    Manager.addClientMessage("Bound " + m.name + " to " + key);
                    found = true;
                    break;
                }
            }
            if (!found) {
                Manager.addClientMessage("Module not found");
            }
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("clear")) {
                for (Module m : Manager.modules) {
                    m.setKey(0);
                }
                Manager.addClientMessage("Cleared all binds");
            }
        }
    }
}
