import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jaco on 2016/08/18.
 *
 * Class to represent the WorkPool using only one lock.
 */
public class SingleLockedWorkPool extends WorkPool {

    /**
     * The lock for this pool.
     */
    private ReentrantLock l;

    /**
     * Constructor for the Work Pool.
     * @param N: The number of items to create in total.
     * @param task: The tasks to use in this workpool.
     */
    public SingleLockedWorkPool(long N, int task) {
        super(N, task);
        this.l = new ReentrantLock();
    }

    /**
     * Checks if the pool is done.
     * @return : True if the pool has completed.
     */
    @Override
    public boolean isDone() {
        try {
            lock(this.l);
            return this.done();
        } finally {
            unlock(this.l);
        }
    }

    /**
     * Enqueues the item to the workpool.
     * @param N: the N'th task to compute.
     * @return : Whether the task was appended successfully.
     */
    @Override
    public boolean enqueue(int N) {
        try {
            lock(this.l);
            return this.enqueueTask(N);
        } finally {
            unlock(this.l);
        }
    }

    /**
     * Dequeues a Task from the pool.
     * @return : The next task to execute.
     */
    @Override
    public Task dequeue() {
        try {
            lock(this.l);
            return this.dequeueTask();
        } finally {
            unlock(this.l);
        }
    }
}
