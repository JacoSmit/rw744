import akka.actor.*;

import job.TraceGeneratorJobPerf;
import messages.*;

import java.util.ArrayList;

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
    private ActorRef presenter;

    private ArrayList<ActorRef> workers;

    private int numActors;
    private int actorKilledCount;

    @Override
    public void preStart() throws Exception {

        this.workers = new ArrayList<ActorRef>();
        /*//Create talker as a child of Master
        executor1 = getContext().actorOf(Props.create(TaskExecutor.class, 0), "executor1");
        executor2 = getContext().actorOf(Props.create(TaskExecutor.class, 1), "executor2");*/
        presenter = getContext().actorOf(Props.create(Presenter.class), "presenter");
        //Watch executors
        /*getContext().watch(executor1);
        getContext().watch(executor2);*/
        getContext().watch(presenter);

        this.numActors = 0;
        this.actorKilledCount = 0;
    }


    @Override
    public void onReceive(Object message) throws Exception {

        if (message instanceof CreateWorker) {
            createWorker(((CreateWorker) message).getId());
        } else if (message instanceof ExecuteJob) {
            ExecuteJob ej = (ExecuteJob) message;
            this.workers.get(ej.getID()).tell(ej.getJob(), getSelf());
        } else if (message instanceof  KillWorker) {
            this.workers.get(((KillWorker) message).getID()).tell(PoisonPill.getInstance(), getSelf());
        }else if (message instanceof Terminated) {
            System.out.println(((Terminated) message).actor().path().name());
            if (this.actorKilledCount < this.numActors) {
                this.actorKilledCount++;
            }

            if (this.actorKilledCount == this.numActors) {
                //presenter.tell(PoisonPill.getInstance(), getSelf());
            }

            if (((Terminated) message).actor().path().name().equals("presenter")) {
                getContext().stop(getSelf());
            }

        } else if (message instanceof Result) {
            presenter.tell(message, getSelf());
        } else {
            unhandled(message);
        }
    }

    public void createWorker(int id) {
        ActorRef worker = getContext().actorOf(Props.create(TaskExecutor.class, id), "executor"+id);
        getContext().watch(worker);
        this.workers.add(worker);
        this.numActors++;
    }
}
