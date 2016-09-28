import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class used for logging the events.
 */
public class Logger {

    /**
     * The time the program started.
     */
    private long start;

    /**
     * The logging instance
     */
    private static Logger logger = null;

    /**
     * The List in which all the Logging entries are stored.
     */
    private List<LogEntry> logBook;

    /**
     * Provides a Singleton instance pattern.
     * @return : The Logger instance class.
     */
    public static Logger getInstance() {
        if (logger == null) {
            logger = new Logger();
        }
        return logger;
    }

    /**
     * Constructor initialising the Logger instance.
     */
    private Logger() {
        this.logBook = new ArrayList<LogEntry>();
    }

    /**
     * Adds the entry to the Log Book.
     * @param timeStamp: the time the event occurred.
     * @param threadId: The Thread's Id.
     * @param eventName: The event that occurred.
     * @param address: The address of the lock.
     */
    public synchronized void write(long timeStamp, String threadId, String eventName, String address) {
        this.logBook.add(new LogEntry((timeStamp - this.start)/1000, threadId, eventName, extractAddress(address)));
    }

    /**
     * Extracts the address of the lock.
     * @param reference: The reference to extract the address from.
     * @return : The address of the lock.
     */
    public String extractAddress(String reference) {
        return reference.substring(reference.indexOf("@")+1, reference.indexOf("["));
    }

    /**
     * Sets the starting time of the application
     * @param start: The starting time.
     */
    public void setStart(long start) {
        this.start = start;
    }

    /**
     * Writes the Log Book to the file.
     * @param N: The number of tasks processed.
     * @param Q: The number of WorkPools created.
     * @param P: The number of threads used.
     * @param B: The average number of tasks spawned.
     * @param workerType: Which type of worker was used.
     * @param task: The task used.
     * @param singleLock: Whether the single lock or double lock implementation was used.
     */
    public void flush(long N, int Q, int P, int B, int workerType, int task, boolean singleLock) {
        try {
            FileWriter fw = new FileWriter(new File("log.txt"));

            fw.append("------------------ CONFIGURATION --------------------\n");
            fw.append("Total number of Tasks: ");
            fw.append(String.format("%-30s", N));
            fw.append("\n");
            fw.append("Number of Work Pools: ");
            fw.append(String.format("%-30s", Q));
            fw.append("\n");
            fw.append("Number of Threads: ");
            fw.append(String.format("%-30d", P));
            fw.append("\n");
            fw.append("Number of Tasks spawned on average: ");
            fw.append(String.format("%-30d", B));
            fw.append("\n");

            fw.append("Worker type: ");
            switch (workerType) {
                case 0:
                    fw.append(String.format("%-30s", "selfish"));
                    break;

                case 1:
                    fw.append(String.format("%-30s", "friendly"));
                    break;

                case 2:
                    fw.append(String.format("%-30s", "crazy"));
                    break;
            }
            fw.append("\n");

            fw.append("Task: ");
            switch (task) {
                case 0:
                    fw.append(String.format("%-30s", "Fibonacci"));
                    break;

                case 1:
                    fw.append(String.format("%-30s", "Prime"));
                    break;

                case 2:
                    fw.append(String.format("%-30s", "Sleep"));
                    break;

                case 3:
                    fw.append(String.format("%-30s", "Loops"));
                    break;

                case 4:
                    fw.append(String.format("%-30s", "Mixed (Fibonacci, Prime, Sleep and Loops)"));
                    break;
            }
            fw.append("\n");

            fw.append("Work Pool Locks: ");
            if (singleLock) {
                fw.append(String.format("%-30s", "single"));
            } else {
                fw.append(String.format("%-30s", "double"));
            }
            fw.append("\n");
            fw.append("-----------------------------------------------------\n\n");

            Collections.sort(this.logBook);

            for (LogEntry entry: this.logBook) {
                fw.append(String.format("%-12d", entry.timeStamp));
                fw.append(" ");
                fw.append(String.format("%-12s", "Thread: " + entry.id));
                fw.append(" ");
                fw.append(String.format("%-12s", entry.address));
                fw.append(" ");
                fw.append(String.format("%-12s", entry.event));
                fw.append("\n");
            }

            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
