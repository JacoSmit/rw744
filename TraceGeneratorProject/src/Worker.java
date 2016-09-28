/**
 * Created by jaco on 2016/08/15.
 *
 * Worker class to remove, execute and add tasks to the work pool.
 */
public abstract class Worker extends Thread{

    /**
     * The number of tasks to spawn on completion of another task.
     */
    protected int numTasksToSpawn;

    /**
     * The current workpool.
     */
    protected WorkPool workPool;

    /**
     * Spawns the number of tasks needed and adds them to the WorkPool.
     */
    protected void spawnTasks() {
        for (int i = 0; i < this.numTasksToSpawn; i++) {
            //TODO: Find which value size to use as upper bound.
            int rand = (int) (Math.random() * 50);
            if (!this.workPool.enqueue(rand)) {
                break;
            }
        }
    }

    /**
     * Checks if the workpool is done.
     * @return : Whether the worker should stop.
     */
    protected abstract boolean checkDone();

    /**
     * Runs the thread.
     */
    @Override
    public void run() {
        locksRun();
    }

    /**
     * Runs the thread using locks.
     */
    public void locksRun() {

        Task task;

        while (true) {

            task = this.workPool.dequeue();

            /*
             * Handles the initial starting of the tasks.
             */
            if (task != null) {
                Manager.debug(task.execute().toString());
            }

            spawnTasks();

            if (checkDone()) {
                break;
            }
        }
    }
}
