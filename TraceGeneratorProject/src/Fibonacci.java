/**
 * Created by jaco on 2016/08/17.
 * Class to represent a task to be run.
 */
public class Fibonacci implements Task{
    /**
     * The n'th Fibonacci number to compute.
     */
    private int n;

    /**
     * The constructor for the task.
     * @param n: The n'th Fibonacci number to compute.
     */
    public Fibonacci(int n) {
        this.n = n;
    }

    /**
     * Computes the n'th Fibonacci number.
     * @param n: The n'th Fibonacci number to compute.
     * @return The n'th Fibonacci number.
     *
     * Algorithm implemented from https://en.wikibooks.org/wiki/Algorithm_Implementation/Mathematics/Fibonacci_Number_Program#Java
     */
    private long fibonacci(int n) {

        if (n < 2) {
            return n;
        }

        long[] f = new long[n+1];
        f[0] = 0;
        f[1] = 1;

        for (int i = 2; i <= n; i++) {
            f[i] = f[i-1] + f[i-2];
        }
        return f[n];
    }

    /**
     * Executes the Task.
     * @return The n'th Fibonacci number.
     */
    public Object execute() {
        return fibonacci(this.n);
    }
}
