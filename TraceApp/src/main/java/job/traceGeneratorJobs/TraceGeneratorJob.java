package job.traceGeneratorJobs;

import job.Job;

/**
 * Created by jaco on 9/28/16.
 *
 */
public abstract class TraceGeneratorJob extends Job {

    private long N;
    private int Q;
    private int P;
    private int B;
    private int workerType;
    private int task;

    public TraceGeneratorJob(long N, int Q, int P, int B, int workerType, int task) {
        super();
        this.N = N;
        this.Q = Q;
        this.P = P;
        this.B = B;
        this.workerType = workerType;
        this.task = task;
    }

    public String toString() {
        return "perf java Main " + N + " " + Q + " " + P + " " + B + " " + workerType + " " + task;
    }

    public String getCommand() {
        return this.toString();
    }

}
