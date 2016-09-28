package messages;

/**
 * Created by jaco on 9/21/16.
 */
public class Praise {

    private String name;

    public Praise(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name + ", you are amazing";
    }
}
