package job.traceGeneratorJobs;

/**
 * Created by jaco on 9/28/16.
 *
 */
public class TraceGeneratorJob2 extends TraceGeneratorJob{
    /**
     * Executes 1 000 000 mixed tasks on 4 workpools with 8 friendly workers
     */
    public TraceGeneratorJob2() {
        super(1000000, 4, 4, 4, 1, 4);
    }
}
