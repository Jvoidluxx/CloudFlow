package cloud.main.module.impl.render;

import cloud.main.events.Event;
import cloud.main.events.impl.EventUpdate;
import cloud.main.module.Category;
import cloud.main.module.Module;
import cloud.main.utils.settings.NumberSetting;
import org.lwjgl.input.Keyboard;

public class FullBright extends Module {
    // made by me
    public NumberSetting bright = new NumberSetting("Brightness", 1.0, 1000.0, 100.0, 0.01);
    public FullBright() {
        super("FullBright", "Makes the world as bright as you want", 0, Category.RENDER);
        addSetting(bright);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.gameSettings.gammaSetting = 1.0f;
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            mc.gameSettings.gammaSetting = bright.getValueFloat();
        }
    }
}
