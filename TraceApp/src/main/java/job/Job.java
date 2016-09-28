package job;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by jaco on 9/28/16.
 *
 */
public abstract class Job {

    public String executeCommand() throws InterruptedException, IOException {
        Runtime rt = Runtime.getRuntime();
        Process proc = rt.exec("echo " + this.getCommand());
        proc.waitFor();

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        BufferedReader stdErr = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = stdIn.readLine()) != null) {
            sb.append(line);
        }

        stdErr.close();
        stdIn.close();

        return sb.toString();
    }

    public abstract String getCommand();

}
