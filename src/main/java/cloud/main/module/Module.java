package cloud.main.module;

import cloud.main.events.Event;
import cloud.main.utils.settings.Setting;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

public class Module {
    protected static Minecraft mc = Minecraft.getMinecraft(); // get the minecraft instance
    public String name, description;
    public int key;
    public Category category;
    public boolean toggled;
    public List<Setting> settings = new ArrayList<>();

    public Module(String name, String description, int key, Category category) {
        this.name = name;
        this.description = description;
        this.key = key;
        this.category = category;
        toggled = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    public void onEnable() {}
    public void onDisable() {}
    public void onEvent(Event e) {}
    public void toggle() {
        toggled = !toggled;
        if (toggled) onEnable();
        else onDisable();
    }

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

    public List<Setting> getSettings() {
        return settings;
    }
    public void addSetting(Setting setting) {
        settings.add(setting);
    }
    public void addSettings(Setting... settings) {
        for (Setting setting : settings) addSetting(setting);
    }
}
