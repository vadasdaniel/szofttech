package common;

public class Log {

    private String id;
    private String date;
    private String from;
    private String type;
    private String text;

    public Log(String id, String date, String type, String from, String text) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.from = from;
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public String getFrom() {
        return from;
    }

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Log(" + id + "):\n" + date + " " + type + " in " + from + "\nMessage: " + text;
    }

    public String toLogSave() {
        return id + "," + date + "," + type + "," + from + "," + text;
    }
}
