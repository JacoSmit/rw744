package messages;

/**
 * Created by jaco on 9/21/16.
 */
public class Celebrate {
    private String name;
    private int age;

    public Celebrate(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String toString() {
        return "Here's to another " + this.age + " years, " + this.name;
    }

}
