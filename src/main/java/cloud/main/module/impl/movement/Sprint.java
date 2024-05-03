package cloud.main.module.impl.movement;

import cloud.main.events.Event;
import cloud.main.events.impl.EventUpdate;
import cloud.main.module.Category;
import cloud.main.module.Module;
import cloud.main.utils.settings.BooleanSetting;
import cloud.main.utils.settings.ModeSetting;
import org.lwjgl.input.Keyboard;

public class Sprint extends Module {
    // made by me
    public Sprint() {
        super("Sprint", "Sprints for you", Keyboard.KEY_F, Category.MOVEMENT);
    }

    @Override
    public void onDisable() {
        mc.gameSettings.keyBindSprint.pressed = false;
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            mc.gameSettings.keyBindSprint.pressed = true;
        }
    }
}
