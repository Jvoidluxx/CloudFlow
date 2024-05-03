package cloud.main.module.impl.render;

import cloud.main.events.Event;
import cloud.main.events.impl.EventPacket;
import cloud.main.module.Category;
import cloud.main.module.Module;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class NoRotate extends Module {
    public NoRotate() {
        super("No Rotate", "Prevents the server from rotating you", 0, Category.RENDER);
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventPacket) {
            if (((EventPacket) e).getPacket() instanceof S08PacketPlayerPosLook) {
                System.out.println("no rotate");
                e.setCanceled(true);
            }
        }
    }
}
