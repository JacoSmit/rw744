/**
 * Created by jaco on 2016/08/17.
 *
 * Worker class to remove, execute and add tasks to the work pool.
 * This Worker also helps out the others by executing tasks from the other pools if his are done.
 */
public class FriendlyWorker extends Worker{

    /**
     * All the other workers.
     */
    private WorkPool[] all;

    /**
     * The index of this pool in the all array.
     */
    private int poolIndex;

    /**
     * The constructor of the Worker.
     * @param workPool: The Workpool the tasks are in.
     * @param all: All the workpools.
     * @param numTasksToSpawn: The number of tasks to spawn on completion.
     * @param poolIndex: The index of this pool in the all array.
     */
    public FriendlyWorker(WorkPool workPool, WorkPool[] all, int numTasksToSpawn, int poolIndex) {
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
         * Change pool if done.
         */
        if (this.workPool.isDone()) {
            boolean allDone = true;

            int i = 0;

            for (WorkPool pool : this.all) {

                if (i == this.poolIndex) {
                    continue;
                }

                if (!pool.isDone()) {
                    this.workPool = pool;
                    this.poolIndex = i;
                    allDone = false;
                    break;
                }

                i++;
            }
            if (allDone) {
                return true;
            }
        }
        return false;
    }
}
