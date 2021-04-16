// Import the library so we can talk to the game
import pi.Minecraft;

// Java, being object oriented, wants classes defined
public class HelloMinecraft {
    // Java will start a program from a static function named "main"
    public static void main(String[] args) throws Exception {
        // Create and store a connection to our game in a variable
        // By leaving the argument empty, it connects locally, but
        // we could provide an IP address if we wanted to a remote pi
        Minecraft minecraft = Minecraft.connect();

        // Make sure we get something to happen in the game
        minecraft.postToChat("Hello Minecraft, from Java!");

        // Something happening in the terminal is nice too
        System.out.println("That's all, folks.");
    }
}
