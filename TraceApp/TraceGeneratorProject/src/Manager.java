/**
 * Created by jaco on 2016/08/15.
 *
 * The Manager class used to order the Workpools and the workers.
 */
public class Manager {

    /**
     * DEBUG constant
     */
    public static final boolean DEBUG = false;

    /**
     * All the workpools.
     */
    private WorkPool[] workPools;

    /**
     * All the Workers.
     */
    private Worker[] workers;

    /**
     * The number of tasks to spawn when one is completed.
     */
    private int B;

    /**
     * The type of the worker:
     * 0 = Selfish
     * 1 = Friendly
     * 2 = Crazy
     */
    private int workerType;

    /**
     * The task to do:
     * 0 = Fibonacci
     * 1 = Prime
     * 2 = Sleep
     * 3 = Loops
     * 4 = Mixed
     */
    private int task;

    /**
     * Whether the single locked workpools should be used.
     */
    private boolean singleLock;

    /**
     * The number of tasks to complete.
     */
    private long N;

    /**
     * The number of workpools to use.
     */
    private int Q;

    /**
     * The number of workers to create.
     */
    private int P;

    /**
     * The constructor for the Manager of the workers and workpools.
     * @param N: The number of tasks in total to spawn. This is the total across all workpools. The
     *         load will be divided by the number of workpools automatically.
     * @param Q: the number of workpools to create.
     * @param P: The number of workers for each work pool.
     * @param B: The number of tasks to spawn once a task is completed.
     * @param workerType: The type of the worker.
     * @param task: The type of the task.
     * @param singleLock: If the workpool is to use single locking or double locking.
     */
    public Manager(long N, int Q, int P, int B, int workerType, int task, boolean singleLock) {
        this.N = N;
        this.Q = Q;
        this.P = P;
        this.B = B;
        this.workPools = new WorkPool[Q];
        this.workers = new Worker[P];
        this.workerType = workerType;
        this.task = task;
        this.singleLock = singleLock;

        divideWork(N, Q);
        assignWorkersRoundRobin(P);
    }

    /**
     * Divides the work between the pools
     * @param N: the number of work to divide.
     * @param Q: The number of pools to craete.
     */
    private void divideWork(long N, int Q) {
        long remaining = N;
        long interval = N/Q;

        /*
         * Divide the work between pools.
         */
        for (int i = 0; i < Q-1; i++) {
            remaining -= interval;
            if (this.singleLock) {
                this.workPools[i] = new SingleLockedWorkPool(interval, this.task);
            } else {
                this.workPools[i] = new DoubleLockedWorkPool(interval, this.task);
            }
        }
        if (this.singleLock) {
            this.workPools[Q - 1] = new SingleLockedWorkPool(remaining, this.task);
        } else {
            this.workPools[Q - 1] = new DoubleLockedWorkPool(remaining, this.task);
        }
    }

    /**
     * Assigns the workers to the different pools.
     * @param numWorkers: The number of workers to create.
     */
    private void assignWorkersRoundRobin(int numWorkers) {

        for (int i = 0; i < numWorkers; i++) {
            if (this.workPools.length == 1) {
                switch (this.workerType) {
                    case 0:
                        workers[i] = new SelfishWorker(this.workPools[0], this.B);
                        break;

                    case 1:
                        workers[i] = new FriendlyWorker(this.workPools[0], this.workPools, this.B, i);
                        break;

                    case 2:
                        workers[i] = new CrazyWorker(this.workPools[0], this.workPools, this.B, i);
                        break;
                }
            } else {

                switch (this.workerType) {
                    case 0:
                        workers[i] = new SelfishWorker(this.workPools[i % this.workPools.length], this.B);
                        break;

                    case 1:
                        workers[i] = new FriendlyWorker(this.workPools[i % this.workPools.length], this.workPools, this.B, i);
                        break;

                    case 2:
                        workers[i] = new CrazyWorker(this.workPools[i % this.workPools.length], this.workPools, this.B, i);
                        break;
                }
            }
        }
    }

    /**
     * Orders all the workers to start working.
     */
    public void enslaveWorkers() {
        Logger.getInstance().setStart(System.nanoTime());

        int i = 1;
        for (Worker worker: this.workers) {
            worker.setName(i+"");
            worker.start();
            i++;
        }
    }

    /**
     * Joins all the threads.
     */
    public void endShift() {
        for (Worker worker: this.workers) {
            try {
                worker.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Writes all the events to the log file.
     */
    public void logBooks() {
        Manager.debug("Logging results");
        Logger.getInstance().flush(this.N, this.Q, this.P, this.B, this.workerType, this.task, this.singleLock);
    }

    /**
     * Logs the debug messages.
     * @param msg : The job to log.
     */
    public static void debug(String msg) {
        if (Manager.DEBUG) {
            System.out.println(msg);
        }
    }
}
