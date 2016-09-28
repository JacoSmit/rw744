package messages;

/**
 * Created by jaco on 9/21/16.
 */
public class Greet {

    private String name;

    public Greet(String name) {
        this.name = name;
    }

    public String toString() {
        return "Hello " + name;
    }
}
