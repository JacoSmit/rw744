package messages;

import job.Job;

/**
 * Created by jaco on 10/10/16.
 *
 */
public class ExecuteJob {

    private Job job;
    private int id;

    public ExecuteJob(int id, Job job) {
        this.id = id;
        this.job = job;
    }

    public int getID() {
        return this.id;
    }

    public Job getJob() {
        return this.job;
    }
}
