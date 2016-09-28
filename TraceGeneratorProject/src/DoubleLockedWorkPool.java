import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jaco on 2016/08/18.
 *
 * Class to represent the WorkPool using only one lock.
 */
public class DoubleLockedWorkPool extends WorkPool {

    /**
     * Lock for the tail of the Queue.
     */
    private ReentrantLock tail;

    /**
     * Lock for the head of the queue.
     */
    private ReentrantLock head;

    /**
     * Constructor for the Work Pool.
     * @param N: The number of items to create in total.
     * @param task: The tasks to use in this workpool.
     */
    public DoubleLockedWorkPool(long N, int task) {
        super(N, task);
        this.tail = new ReentrantLock();
        this.head = new ReentrantLock();
    }

    /**
     * Checks if the pool is done.
     * @return : True if the pool has completed.
     */
    @Override
    public boolean isDone() {
        try {
            synchronized (this) {
                lock(this.head);
                lock(this.tail);
            }

            return this.done();
        } finally {
            unlock(this.head);
            unlock(this.tail);
        }
    }

    /**
     * Enqueues the item to the workpool.
     * @param N: the N'th task to compute.
     * @return : Whether the task was appended successfully.
     */
    @Override
    public boolean enqueue(int N) {
        boolean headLocked = true;

        try {
            synchronized (this) {
                lock(this.head);
                lock(this.tail);
            }

            if (this.getSize() > 1) {
                headLocked = false;
                unlock(this.head);
            }

            return this.enqueueTask(N);
        } finally {
            if (headLocked) {
                unlock(this.head);
            }
            unlock(this.tail);
        }
    }

    /**
     * Dequeues a Task from the pool.
     * @return : The next task to execute.
     */
    @Override
    public Task dequeue() {
        boolean tailLocked = true;

        try {
            synchronized (this) {
                lock(this.head);
                lock(this.tail);
            }

            if (this.getSize() > 1) {
                tailLocked = false;
                unlock(this.tail);
            }

            return this.dequeueTask();
        } finally {
            if (tailLocked) {
                unlock(this.tail);
            }
            unlock(this.head);
        }
    }
}
