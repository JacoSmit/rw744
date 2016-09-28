package job.traceGeneratorJobs;

/**
 * Created by jaco on 9/28/16.
 *
 */
public class TraceGeneratorJob3 extends TraceGeneratorJob {
    /**
     * Executes 1 000 000 loops on 3 workpools with 4 crazy workers
     */
    public TraceGeneratorJob3() {
        super(1000000, 3, 4, 2, 2, 3);
    }
}
