package cloud.main.commandbase.impl;

import cloud.main.commandbase.Command;
import cloud.main.module.Manager;
import cloud.main.module.Module;
import cloud.main.utils.settings.BooleanSetting;
import cloud.main.utils.settings.ModeSetting;
import cloud.main.utils.settings.NumberSetting;
import cloud.main.utils.settings.Setting;

public class Adjust extends Command {
    public Adjust() {
        super("adjust", "adjusts a module", "adjust {module name} {setting} {value}", "adj");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length == 3) {
            String module = args[0];
            String setting = args[1];
            String value = args[2];
            boolean found = false;
            for (Module m : Manager.modules) {
                if (m.name.equalsIgnoreCase(module)) {
                    for (Setting s : m.getSettings()) {
                        if (s.getName().equalsIgnoreCase(setting)) {
                            found = true;
                            if (s instanceof NumberSetting) {
                                ((NumberSetting) s).setValue(Double.parseDouble(value));
                            } else if (s instanceof ModeSetting) {
                                ((ModeSetting) s).setMode(value);
                            } else if (s instanceof BooleanSetting) {
                                ((BooleanSetting) s).setEnabled(Boolean.parseBoolean(value));
                            }
                        }
                    }
                }
            }
            if (!found) {
                Manager.addClientMessage("Module not found or setting");
            }
        }
    }
}
