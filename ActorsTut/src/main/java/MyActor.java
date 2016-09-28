import akka.actor.UntypedActor;
import messages.Celebrate;
import messages.Greet;
import messages.Praise;

/**
 * Created by jaco on 9/21/16.
 */
public class MyActor extends UntypedActor {
    public void onReceive(Object message) throws Throwable {

        if (message instanceof Greet) {
            System.out.println(message);
        } else if (message instanceof Praise) {
            System.out.println(message);
        } else if (message instanceof Celebrate) {
            System.out.println(message);
        }
    }
}