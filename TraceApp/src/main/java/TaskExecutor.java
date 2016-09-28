import akka.actor.UntypedActor;
import job.Job;
import messages.Result;

/**
 * Created by jaco on 9/28/16.
 *
 */
public class TaskExecutor extends UntypedActor{
    public void onReceive(Object message) throws Throwable {

        if (message instanceof Job) {
            Job job = (Job)message;
            String result = job.executeCommand();
            getSender().tell(new Result(result), getSelf());
        } else {
            unhandled(message);
        }
    }
}
