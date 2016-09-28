/**
 * Created by jaco on 2016/08/22.
 *
 * This worker randomly selects a pool after executing a task and spawning new ones.
 */
public class CrazyWorker extends Worker {

    /**
     * All the other workers.
     */
    private WorkPool[] all;

    /**
     * The index of the current pool in the all array.
     */
    private int poolIndex;

    /**
     * The constructor of the Worker.
     * @param workPool: The Workpool the tasks are in.
     * @param all: All the workpools.
     * @param numTasksToSpawn: The number of tasks to spawn on completion.
     * @param poolIndex: The index of this pool in the all array.
     */
    public CrazyWorker(WorkPool workPool, WorkPool[] all, int numTasksToSpawn, int poolIndex) {
        this.workPool = workPool;
        this.numTasksToSpawn = numTasksToSpawn;
        this.all = all;
        this.poolIndex = poolIndex;
    }

    /**
     * Checks if the queue is done.
     * @return : Whether the workpool has been completed.
     */
    @Override
    protected boolean checkDone() {
        /*
         * Change pool after spawn.
         */
        while (true) {
            /*
             * Need to check in here as this may have changed in the meanwhile.
             */
            if (isAllDone()) {
                return true;
            }

            this.poolIndex = (int)(Math.random() * this.all.length);
            this.workPool = this.all[this.poolIndex];

            if (!this.workPool.isDone()) {
                break;
            }
        }

        return false;
    }

    /**
     * Checks if all the pools are done.
     * @return : True if all the pools are done, false otherwise.
     */
    private boolean isAllDone() {
        for (WorkPool pool: this.all) {
            if (!pool.isDone()) {
                return false;
            }
        }
        return true;
    }
}
