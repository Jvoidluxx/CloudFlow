package cloud.main.events;

public class Event { // to use the events :
    /*
    Make a class in impl folder
    extend the class with Event / this class
    Use the event class wherever you want to send a event on the update
    to register the event do :
    {EventName} e = new {EventName}();
    Manager.onEvent(e);
     */
    public EventDirection dir;
    public EventType type;
    public boolean canceled;

    public EventDirection getDir() {
        return dir;
    }

    public void setDir(EventDirection dir) {
        this.dir = dir;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
    public boolean isPre() {
        if (type == null) return false;
        return type == EventType.PRE;
    }
    public boolean isPost() {
        if (type == null) return false;
        return type == EventType.POST;
    }
    public boolean isIncoming() {
        if (dir == null) return false;
        return dir == EventDirection.INCOMING;
    }
    public boolean isOutgoing() {
        if (dir == null) return false;
        return dir == EventDirection.OUTGOING;
    }
}
