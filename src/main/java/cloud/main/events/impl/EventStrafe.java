package cloud.main.events.impl;

import cloud.main.events.Event;

public class EventStrafe extends Event {
    public float yaw;

    public EventStrafe(float yaw) {
        this.yaw = yaw;
    }


    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }
}
