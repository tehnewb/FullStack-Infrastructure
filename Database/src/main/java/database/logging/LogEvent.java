package database.logging;

import event.Event;

import java.time.LocalDateTime;

public class LogEvent extends Event {

    private final LocalDateTime time;
    private final Object object;

    public LogEvent(LocalDateTime time, Object object) {
        this.time = time;
        this.object = object;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Object getObject() {
        return object;
    }
}
