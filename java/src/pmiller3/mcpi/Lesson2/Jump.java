// Create a package, or namespace, for this lesson
package pmiller3.mcpi.Lesson2;

// imports for reading config file
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// Import the library so we can talk to the game
import pi.Minecraft;
import pi.Vec;

// Java, being object oriented, wants classes defined
public class Jump {
    // Let's have a place to keep our host
    private static String host = "localhost";

    // Java will start a program from a static function named "main"
    public static void main(String[] args) throws Exception {
        // Attempt to read the config and set some things from the file,
        // in this case the host to connect to
        readConfig();

        // Create and store a connection to our game in a variable
        // By leaving the argument empty, it connects locally, but
        // we could provide an IP address if we wanted to a remote pi
        Minecraft minecraft = Minecraft.connect(host);

        // Ask the Minecraft game where the player is and store it
        Vec playerPosition = minecraft.player.getPosition();

        // We can see in the output, the player position is represented
        // as a vector with 3 numbers in it - these are the X, Y and Z
        // coordinates in 3d space
        System.out.println(playerPosition);

        // A more convenient syntax we can use to get just the 3 integers
        int x = playerPosition.x, y = playerPosition.y, z = playerPosition.z;

        // So, to jump, we just have to move the player "up"!
        // This is done by increasing the y coordinate of the position
        // Lots of alternate ways to express this, creating a new object or using
        // built in vector math on the existing playerPosition
        // minecraft.player.setPosition(Vec.xyz(playerPosition.x, playerPosition.y + 10, playerPosition.z));
        // minecraft.player.setPosition(Vec.xyz(x, y + 10, z));
        minecraft.player.setPosition(playerPosition.add(0, 10, 0));

        // Something happening in the terminal is nice too
        System.out.println("Jump! script completed");
    }

    // Read the configuration file and populate host
    public static void readConfig() {
        Properties properties = new Properties();
        InputStream filestream = null;
        try {
            filestream = new FileInputStream(".config");
            properties.load(filestream);
            host = properties.getProperty("host");
        } catch (FileNotFoundException ex) {
            System.err.println("No .config file found; using defaults.");
        } catch (IOException ex) {
            System.err.println("Input/Output exception when streaming .config file; using defaults.");
        }
    }
}
