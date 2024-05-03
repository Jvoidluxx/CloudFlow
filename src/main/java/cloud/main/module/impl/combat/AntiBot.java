package cloud.main.module.impl.combat;

import cloud.main.events.Event;
import cloud.main.events.impl.EventUpdate;
import cloud.main.module.Category;
import cloud.main.module.Module;
import org.lwjgl.input.Keyboard;

public class AntiBot extends Module {
    public AntiBot() {
        super("AntiBot", "Prevents modules from hitting bots", 0, Category.COMBAT);
    }

    @Override
    public void onEvent(Event e) { // doesnt work mostly
        if (e instanceof EventUpdate) {

        }
    }
}
