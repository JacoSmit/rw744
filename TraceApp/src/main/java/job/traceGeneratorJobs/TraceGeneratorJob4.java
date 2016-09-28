package job.traceGeneratorJobs;

/**
 * Created by jaco on 9/28/16.
 *
 */
public class TraceGeneratorJob4 extends TraceGeneratorJob {

    /**
     * Executes 1 000 000 sleep cycles on 5 workpools with 2 friendly workers
     */
    public TraceGeneratorJob4() {
        super(1000000, 5, 2, 2, 1, 2);
    }
}
