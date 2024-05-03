package cloud.main.module.impl.misc;

import cloud.main.events.Event;
import cloud.main.events.impl.EventUpdate;
import cloud.main.module.Category;
import cloud.main.module.Module;

public class AutoRespawn extends Module { // made by me
    public AutoRespawn() {
        super("AutoRespawn", "Respawns you when you die", 0, Category.MISC);
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            if (mc.thePlayer.getHealth() <= 0) {
                mc.thePlayer.respawnPlayer();
            }
        }
    }
}
