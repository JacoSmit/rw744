/**
 * Created by jaco on 2016/08/19.
 *
 * Container for a log entry.
 */
public class LogEntry implements Comparable<LogEntry>{

    /**
     * The timestamp of the event relative to the start of code..
     */
    public Long timeStamp;

    /**
     * The id of the thread in which the event occurred.
     */
    public String id;

    /**
     * The event that occurred.
     */
    public String event;

    /**
     * The Address of the lock.
     */
    public String address;

    /**
     * The constructor for the Entry
     * @param timeStamp: the timestamp relative tot the start of the program the event occurred.
     * @param id: The Id of the thread.
     * @param event: The event that occurred.
     * @param  address: The address of the lock.
     */
    public LogEntry(long timeStamp, String id, String event, String address) {
        this.timeStamp = timeStamp;
        this.id = id;
        this.event = event;
        this.address = address;
    }

    /**
     * Compare to method for ordering the events.
     * @param other: The event to be compared to.
     * @return : -1 if less, 0 if equal, 1 if greater
     */
    public int compareTo(LogEntry other) {
        return timeStamp.compareTo(other.timeStamp);
    }
}
