/**
 * Created by jaco on 2016/08/17.
 *
 * This worker only completes his work pool's tasks.
 */
public class SelfishWorker extends Worker{

    /**
     * The constrcutor of the Worker.
     * @param workPool: The Workpool the tasks are in.
     * @param numTasksToSpawn: The number of tasks to spawn on completion.
     */
    public SelfishWorker(WorkPool workPool, int numTasksToSpawn) {
        this.workPool = workPool;
        this.numTasksToSpawn = numTasksToSpawn;
    }

    /**
     * Checks if the Workpool is completed.
     * @return : Whether the work has completed.
     */
    @Override
    protected boolean checkDone() {
        Manager.debug("Check done");
        return this.workPool.isDone();
    }
}
