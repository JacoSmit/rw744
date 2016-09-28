/**
 * Created by jaco on 2016/08/17.
 *
 * Computes the N'th Prime number
 */
public class Prime implements Task{

    /**
     * The N'th Prime to compute
     */
    private int N;

    /**
     * The constructor of the task.
     * @param N: The n'th prime number ot compute.
     */
    public Prime(int N) {
        this.N = N;
    }

    /**
     * Algorithm found on ftp://www.cabm.rutgers.edu/nmr/outgoing/gliu/cs/NthPrime.java
     * @return :Returns the N'th prime number
     */
    private long prime() {
        long num;
        int count, i;

        num = 1L;
        count = 0;

        while (count < this.N){

            num=num+1; //find the next prime number
            for (i = 2; i <= num; i++){

                if (num % i == 0L) {

                    break; //prime not found
                }
            }
            if (i == num){

                count = count+1; //prime found
            }
        }
        return num;
    }

    /**
     * Executes the task.
     * @return : The result of the task.
     */
    @Override
    public Object execute() {
        return prime();
    }
}
