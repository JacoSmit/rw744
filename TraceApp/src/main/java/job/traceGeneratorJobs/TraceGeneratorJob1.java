package job.traceGeneratorJobs;

/**
 * Created by jaco on 9/28/16.
 *
 */
public class TraceGeneratorJob1 extends TraceGeneratorJob {

    /**
     * Executes 1 000 000 fibonacci numbers on 2 workpools with 4 selfish workers
     */
    public TraceGeneratorJob1() {
        super(1000000, 2, 4, 4, 0, 0);
    }

}
