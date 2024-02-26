package database.logging;

import event.EventBus;

import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatabaseLogger extends PrintStream {

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("MMMM d', 'yyyy' 'h:mma");
    private final ConsoleColor defaultColor;

    private EventBus events = new EventBus();

    public DatabaseLogger(ConsoleColor defaultColor, OutputStream out) {
        super(out);

        this.defaultColor = defaultColor;
    }

    private static String printFormat(ConsoleColor color, Object object) {
        return "%s [%s] %s %s".formatted(color, LocalDateTime.now().format(TIME_FORMAT), String.valueOf(object), ConsoleColor.RESET);
    }

    public void addListener(LogListener listener) {
        synchronized (events) {
            this.events.subscribe(LogEvent.class, listener);
        }
    }

    private void publish(Object object) {
        synchronized (events) {
            this.events.publish(new LogEvent(LocalDateTime.now(), object));
        }
    }


    @Override
    public void print(String b) {
        super.print(printFormat(defaultColor, b));
        publish(b);
    }

    @Override
    public void print(int b) {
        super.print(printFormat(defaultColor, b));
        publish(b);
    }

    @Override
    public void print(long b) {
        super.print(printFormat(defaultColor, b));
        publish(b);
    }

    @Override
    public void print(float b) {
        super.print(printFormat(defaultColor, b));
        publish(b);
    }

    @Override
    public void print(double b) {
        super.print(printFormat(defaultColor, b));
        publish(b);
    }

    @Override
    public void print(char b) {
        super.print(printFormat(defaultColor, b));
        publish(b);
    }

    @Override
    public void print(char[] b) {
        super.print(printFormat(defaultColor, b));
        publish(b);
    }

    @Override
    public void print(boolean b) {
        super.print(printFormat(defaultColor, b));
        publish(b);
    }

}
