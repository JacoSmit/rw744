package job;

import job.Job;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by jaco on 9/28/16.
 *
 */
public class TraceGeneratorJobPerf extends Job {

    private long N;
    private int Q;
    private int P;
    private int B;
    private int workerType;
    private int task;
    private int lockType;
    private int jobID;

    public TraceGeneratorJobPerf(long N, int Q, int P, int B, int workerType, int task, int lockType, int jobID) {
        super();
        this.N = N;
        this.Q = Q;
        this.P = P;
        this.B = B;
        this.workerType = workerType;
        this.task = task;
        this.lockType = lockType;
        this.jobID = jobID;
    }

    @Override
    public String executeCommand() {
        Runtime rt = Runtime.getRuntime();
        Process proc = null;
        try {
            proc = rt.exec(this.getCommand());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            proc.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        BufferedReader stdErr = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

        StringBuilder sb = new StringBuilder();

        String line;

        try {
            while((line = stdErr.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            stdErr.close();
            stdIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public String toString() {
        return "perf stat java -classpath ./TraceGeneratorProject/out/production/TraceGeneratorProject/ " +
                "Main " + N + " " + Q + " " + P + " " + B + " " + workerType + " " + task + " " + lockType
                + " " + jobID;
    }

    public String getCommand() {
        return this.toString();
    }

    public String getParams() {
        return "N=" + this.N + " Q=" + Q + " P=" + P + " B=" + B + " wT=" + workerType + " T=" + task
                + " lT=" + lockType + " fn=" + jobID + ".log";
    }

}
