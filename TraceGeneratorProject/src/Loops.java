/**
 * Created by jaco on 2016/08/22.
 *
 * Task that loops for a number of times.
 */
public class Loops implements Task {

    /**
     * The number that will be looped *10000 times.
     */
    private int N;

    /**
     * The constructor for the loops.
     * @param N : The number that will be looped *10000 times.
     */
    public Loops(int N) {
        this.N = N;
    }

    /**
     * Loops N*10000 times.
     * @param N: The number to loop.
     * @return : The number of times the loop executed.
     */
    private int loop(int N) {
        for (int i = 0; i < N*10000; i++);
        return N*10000;
    }

    /**
     * Executes the task.
     * @return : The number of times the loop executed.
     */
    @Override
    public Object execute() {
        return loop(this.N);
    }

}
