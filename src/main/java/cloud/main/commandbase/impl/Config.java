package cloud.main.commandbase.impl;

import cloud.Cloud;
import cloud.main.ConfigManager;
import cloud.main.commandbase.Command;
import cloud.main.module.Manager;

import java.io.File;

public class Config extends Command {
    public Config() {
        super("config", "Add your own configs", ".config {load | save} {configName}", "cfg" );
    }

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length == 2) {
            String option = args[0];
            String configName = args[1];
            File file = new File(ConfigManager.ROOT_DIR, configName + ".json");
            if (option.equalsIgnoreCase("save") && !file.exists()) {
                Cloud.configManager.saveCustomConfig(file);
                Manager.addClientMessage("Saved config " + configName);
            } else if (option.equalsIgnoreCase("load") && file.exists()) {
                Cloud.configManager.loadCustomConfig(file);
                Manager.addClientMessage("Loaded config " + configName);
            }else{
                Manager.addClientMessage("Invalid option");
            }
        }
    }
}
