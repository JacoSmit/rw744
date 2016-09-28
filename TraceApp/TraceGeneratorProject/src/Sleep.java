import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

/**
 * Created by jaco on 2016/08/22.
 *
 * Task for computing the N'th Digit of Pi.
 */
public class Sleep implements Task {

    /**
     * The N'th digit to compute.
     */
    private int N;

    /**
     * The Constructor for the task.
     * @param N: The N'th digit to compute.
     */
    public Sleep(int N) {
        this.N = N;
    }

    /**
     * Computes the n'th digit of Pi.
     *
     * Code from http://www.codecodex.com/wiki/Calculate_digits_of_pi
     *
     * @param n: The n'th digit to compute.
     * @return : The n'th digit of Pi
     */
    public int goodNight(int n) {
        try {
            Thread.sleep(n);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return n;
    }

    @Override
    public Object execute() {
        return goodNight(this.N);
    }

}
