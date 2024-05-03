package cloud.main.events.impl;

import cloud.main.events.Event;
import net.minecraft.network.Packet;

public class EventPacket extends Event {
    public Packet<?> packet;

    public EventPacket(Packet<?> packetIn) {
        this.packet = packetIn;
    }

    public Packet<?> getPacket() {
        return packet;
    }
}
