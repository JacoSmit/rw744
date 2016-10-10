import akka.actor.Terminated;
import akka.actor.UntypedActor;
import job.Job;
import messages.Result;


/**
 * Created by jaco on 10/4/16.
 *
 */
public class Presenter extends UntypedActor {

    private PresenterUI presenterUI;

    @Override
    public void preStart() throws Exception {
        /*this.presenterUI = new PresenterUI();
        this.presenterUI.createAndShow();*/

    }



    @Override
    public void onReceive(Object message) {
        if (message instanceof Result) {

            Result result = (Result) message;
            System.out.println(result.getResult());

        } else if (message instanceof Terminated) {
            //getContext().stop(getSelf());
        } else {
            unhandled(message);
        }

    }

}
