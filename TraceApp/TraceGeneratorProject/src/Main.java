/**
 * Created by jaco on 2016/08/15.
 * Main class to execute the application
 */
public class Main {

    /**
     * Main method to execute the program.
     * @param args: The arguments from the job line.
     */
    public static void main(String args[]) throws InterruptedException {
        if (!checkArgs(args)) {
            printUsage();
        }

        long N = Long.parseLong(args[0]);
        int Q = Integer.parseInt(args[1]);
        int P = Integer.parseInt(args[2]);
        int B = Integer.parseInt(args[3]);
        int workerType = Integer.parseInt(args[4]); // 0 = SelfishWorker, 1 = FriendlyWorker, 2 = CrazyWorker
        int task = Integer.parseInt(args[5]); // 0 = Fibonacci, 1 = Prime, 2 = Sleep, 3 = Loops, 4 = Mixed

        boolean singleLock = true;

        int lock = Integer.parseInt(args[6]);
        if (lock == 1) {
            singleLock = false;
        }

        long start = System.currentTimeMillis();

        for (int i = 0; i < 1; i++) {
            Manager manager = new Manager(N, Q, P, B, workerType, task, singleLock, args[7]);
            manager.enslaveWorkers();
            manager.endShift();
            manager.logBooks();
        }

        long end = System.currentTimeMillis();
        Manager.debug((end-start) + "");
    }

    /**
     * Checks if the arguments are the correct format and correct.
     * @param args: The job line arguments.
     * @return : Whether the arguments were fine.
     */
    private static boolean checkArgs(String args[]) {
        if (args.length != 8) {
            return false;
        }

        try {
            long N = Long.parseLong(args[0]);
            int Q = Integer.parseInt(args[1]);
            int P = Integer.parseInt(args[2]);
            int B = Integer.parseInt(args[3]);
            int workerType = Integer.parseInt(args[4]);
            int task = Integer.parseInt(args[5]);
            int lock = Integer.parseInt(args[6]);

            if (N < 1 || Q < 1 || P < 1 || B < 1 || workerType < 0 || task < 0 || lock < 0) {
                return false;
            }

            if (workerType > 2 || task > 4 || lock > 1) {
                return false;
            }

            if (args[7] == "") {
                return false;
            }

        } catch (NumberFormatException e){
            return false;
        }

        return true;
    }

    private static void printUsage() {
        System.out.println("Usage: java Main <N> <Q> <P> <B> <workerType> <task> <lock> <filename>\n" +
                "\tN: The number of tasks to execute\n" +
                "\tQ: The number of work pools\n" +
                "\tP: The  number of workers\n" +
                "\tB: The number of tasks to spawn on average\n" +
                "\tworkerType: 0 = Selfish, 1 = Friendly, 2 = Crazy\n" +
                "\ttask: 0 = Fibonacci, 1 = Prime, 2 = Sleep, 3 = Loops, 4 = Mixed\n" +
                "\tlock: 0 = single, 1 = double" +
                "\tfilename: name of the file to be logged to.");
        System.exit(1);
    }

}
