package cloud.main;

import cloud.main.module.Manager;
import cloud.main.module.Module;
import cloud.main.utils.JsonUtils;
import cloud.main.utils.settings.BooleanSetting;
import cloud.main.utils.settings.ModeSetting;
import cloud.main.utils.settings.NumberSetting;
import cloud.main.utils.settings.Setting;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.*;
import java.util.Iterator;
import java.util.Map;

public class ConfigManager {
    public static File ROOT_DIR = new File("Cloud");
    public static File modules = new File(ROOT_DIR, "config.json");

    public void init() {
        if (!ROOT_DIR.exists()) {
            ROOT_DIR.mkdirs();
        }
        if (!modules.exists())
            saveConfig();
        else
            loadConfig();
    }

    public void saveConfig() {
        try {
            JsonObject json = new JsonObject();
            for (Module m : Manager.modules) {
                JsonObject jsonMod = new JsonObject();
                jsonMod.addProperty("toggled", m.isToggled());
                jsonMod.addProperty("key", m.getKey());
                for (Setting s : m.getSettings()) {
                    if (s instanceof BooleanSetting) {
                        jsonMod.addProperty(s.getName(), ((BooleanSetting) s).isEnabled());
                    } else if (s instanceof ModeSetting) {
                        jsonMod.addProperty(s.getName(), ((ModeSetting) s).getMode());
                    } else if (s instanceof NumberSetting) {
                        jsonMod.addProperty(s.getName(), ((NumberSetting) s).getValue());
                    }
                }
                json.add(m.getName(), jsonMod);
            }
            PrintWriter save = new PrintWriter(new FileWriter(modules));
            save.println(JsonUtils.prettyNikker.toJson(json));
            save.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadConfig() {
        try {
            BufferedReader load = new BufferedReader(new FileReader(modules));
            JsonObject json = (JsonObject) JsonUtils.jsonParser.parse(load);
            load.close();
            Iterator<Map.Entry<String, JsonElement>> itr = json.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry<String, JsonElement> entry = itr.next();
                Module mod = Manager.getModule(entry.getKey());
                JsonObject jsonModule = entry.getValue().getAsJsonObject();
                boolean enabled = jsonModule.get("toggled").getAsBoolean();
                for (Setting s : mod.getSettings()) {
                    if (s instanceof BooleanSetting) {
                        boolean boolSetEnabled = jsonModule.get(s.getName()).getAsBoolean();
                        ((BooleanSetting) s).setEnabled(boolSetEnabled);
                    } else if (s instanceof ModeSetting) {
                        String mode = jsonModule.get(s.getName()).getAsString();
                        ((ModeSetting) s).setMode(mode);
                    } else if (s instanceof NumberSetting) {
                        float num = jsonModule.get(s.getName()).getAsFloat();
                        ((NumberSetting) s).setValue(num);
                    }
                }
                mod.setToggled(enabled);
                if (jsonModule.has("key")) {
                    int keybind = jsonModule.get("key").getAsInt();
                    mod.setKey(keybind);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}