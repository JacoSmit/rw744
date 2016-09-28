import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jaco on 2016/08/15.
 *
 * Class to represent the WorkPool.
 */
public abstract class WorkPool {

    /**
     * The Work pool.
     */
    protected Queue<Task> workPool;

    /**
     * The total number of work items to create.
     */
    protected long totalNumWorkItems;

    /**
     * The current number of work items processed and on the Queue.
     */
    protected long currentNumWorkItems;

    /**
     * Which task should be executed.
     * 0 = Fibonacci
     * 1 = Prime
     * 2 = Sleep
     * 3 = Loops
     * 4 = Mixed
     */
    protected int task;

    /**
     * Constructor for the Workpool.
     * @param N: The number of items to create.
     * @param task: The tasks to use in this workpool.
     */
    public WorkPool(long N, int task) {
        this.workPool = new LinkedList<Task>();
        this.totalNumWorkItems = N;
        this.task = task;
    }

    /**
     * Enqueues a Task in work pool.
     * @param N: The n'th Fibonacci number to compute.
     * @return : Whether it was successful or not.
     */
    protected boolean enqueueTask(int N) {

        if (this.currentNumWorkItems >= this.totalNumWorkItems) {
            return false;
        }
        this.currentNumWorkItems += 1L;

        switch (this.task) {
            case 0:
                this.workPool.add(new Fibonacci(N));
                break;

            case 1:
                this.workPool.add(new Prime(N));
                break;

            case 2:
                this.workPool.add(new Sleep(N));
                break;

            case 3:
                this.workPool.add(new Loops(N));
                break;

            case 4:
                double rand = Math.random();

                if (rand < 0.25) {
                    this.workPool.add(new Fibonacci(N));
                } else if (rand < 0.5){
                    this.workPool.add(new Prime(N));
                } else if (rand < 0.75) {
                    this.workPool.add(new Sleep(N));
                } else {
                    this.workPool.add(new Loops(N));
                }
                break;
        }
        return true;
    }

    /**
     * Wrapper for enqueueTask. Locking should take place in this method.
     * @param N : The n'th number to compute.
     * @return : Whether it was enqueued successfully.
     */
    abstract boolean enqueue(int N);


    /**
     * Returns the head of the Queue.
     * @return :The task at the head of the Queue.
     */
    protected Task dequeueTask() {

        if (this.workPool.isEmpty()) {
            return null;
        }

        return this.workPool.remove();
    }

    /**
     * Wrapper for dequeueTask. Locking should take place in this method.
     * @return : The Task to execute next.
     */
    abstract Task dequeue();

    /**
     * Returns if this workpool is done.
     * @return : Whether the number of tasks required by this Workpool is completed.
     */
    protected boolean done() {
        return this.currentNumWorkItems == this.totalNumWorkItems;
    }

    /**
     * Wrapper for done. Locking should take place in this method.
     * @return : If the workpool has completed.
     */
    abstract boolean isDone();

    /**
     * Returns the size of Queue
     * @return : The size of the queue.
     */
    protected int getSize() {
        return this.workPool.size();
    }

    /**
     * Writes an event to the Log book.
     * @param id: The thread ID.
     * @param eventName: The event that occurred.
     */
    protected void write(String id, String eventName, String address) {
        Manager.debug(id + " " + eventName);
        Logger.getInstance().write(System.nanoTime(), id, eventName, address);
    }

    /**
     * Logs the event and locks the lock.
     * @param lock: The lock to be locked.
     */
    protected void lock(ReentrantLock lock) {
        write(Thread.currentThread().getName()+"", "trylock", lock.toString());
        lock.lock();
        write(Thread.currentThread().getName()+"", "lock", lock.toString());
    }

    /**
     * Logs the event and unlocks the lock.
     * @param lock : The lock to be unlocked.
     */
    protected void unlock(ReentrantLock lock) {
        write(Thread.currentThread().getName()+"", "release", lock.toString());
        lock.unlock();
    }



}
