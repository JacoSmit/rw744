import akka.actor.*;

/**
 * Created by jaco on 9/28/16.
 *
 */
public class Main {

    public static void main(String args[]) {

        System.out.println("Starting ....");

        ActorSystem system = ActorSystem.create("system");

        final ActorRef master = system.actorOf(Props.create(Master.class), "master");
        system.actorOf(Props.create(Terminator.class, master), "terminator");

        MasterUI ui = new MasterUI(master);

        //master.tell(Master.Msg.START, ActorRef.noSender());

    }

    /**
     * This actor watches the actor who's reference is provided during construction,
     * and when it terminates, it terminates the actor system.
     */
    public static class Terminator extends UntypedActor {

        private final ActorRef ref;

        public Terminator(ActorRef ref) {
            this.ref = ref;
            getContext().watch(ref);
        }

        @Override
        public void onReceive(Object msg) {
            if (msg instanceof Terminated) {
                System.out.println("Master has terminated, shutting down system.");
                getContext().system().terminate();
            } else {
                unhandled(msg);
            }
        }

    }
}
