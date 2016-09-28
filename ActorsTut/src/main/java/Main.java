
import akka.actor.*;
import messages.*;

/**
 * Created by jaco on 9/21/16.
 *
 */



public class Main {

    public static void main(String []args) {
        System.out.println("Starting ....");

        ActorSystem system = ActorSystem.create("Main");

        Props props1 = Props.create(MyActor.class);
        Props props2 = Props.create(MyActor.class);

        ActorRef actor1 = system.actorOf(props1, "actor1");
        ActorRef actor2 = system.actorOf(props2, "actor2");


        actor1.tell(new Greet("Huey"), actor2);
        actor2.tell(new Praise("Dewey"), actor1);

        final Inbox inbox = Inbox.create(system);
        inbox.send(actor1, new Celebrate("Louie", 16));


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.exit(0);


    }

}
