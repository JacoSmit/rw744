import akka.actor.*;

import job.traceGeneratorJobs.TraceGeneratorJob1;
import job.traceGeneratorJobs.TraceGeneratorJob2;
import job.traceGeneratorJobs.TraceGeneratorJob3;
import job.traceGeneratorJobs.TraceGeneratorJob4;
import messages.*;

/**
 * Created by jaco on 9/28/16.
 *
 */
public class Master extends UntypedActor{

    public enum Msg {
        START
    }

    private ActorRef executor1;
    private ActorRef executor2;

    @Override
    public void preStart() throws Exception {
        //Create talker as a child of Master
        executor1 = getContext().actorOf(Props.create(TaskExecutor.class), "executor1");
        executor2 = getContext().actorOf(Props.create(TaskExecutor.class), "executor2");
        //Watch executors
        getContext().watch(executor1);
        getContext().watch(executor2);
    }


    @Override
    public void onReceive(Object message) throws Exception {

        if (message == Msg.START) {
            executor1.tell(new TraceGeneratorJob1(), getSelf());
            executor2.tell(new TraceGeneratorJob2(), getSelf());
            executor1.tell(new TraceGeneratorJob3(), getSelf());
            executor1.tell(new TraceGeneratorJob4(), getSelf());
            executor1.tell(PoisonPill.getInstance(), getSelf());
            executor2.tell(PoisonPill.getInstance(), getSelf());
        } else if (message instanceof Terminated) {
            getContext().stop(getSelf());
        } else if (message instanceof Result) {
            System.out.println(((Result) message).getResult());
        } else {
            unhandled(message);
        }
    }
}
