// Create a package, or namespace, for this lesson
package pmiller3.mcpi.Lesson3;

// imports for reading config file, and doing time maths
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Properties;

// Import the library so we can talk to the game
import pi.Minecraft;
import pi.Vec;
import pi.Block;

// Java, being object oriented, wants classes defined
public class Greenfeet {
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

        // What are greenfeet?  Well, they're a bit of magic... they allow the player to
        // change dirt to grass!  How can we achieve this?  Let's break it down into some steps...
        // First, to change dirt, we'd have to know what the player is walking on, right?
        // To do that, we'd need to know two things, in order:
        // Where the player is in the world, and what block are they standing on?
        // The API let's us do both!  We already know the first bit, so now it's
        // just part two, asking the API what the block type is one block below the player.
        // Then, with our knowledge in hand, we just need to check if it's dirt.
        // If it is, we again use the API to tell the world to turn that block to grass.
        // But for this to be effective, it can't just happen once... it has to happen
        // for some amount of time.  So we'll have to make some sort of loop to check
        // and recheck at frequent enough intervals

        // Let's get a variable ready for making this not run forever, in seconds
        int bootsDuration = 10;
        Instant expireTime = Instant.now().plusSeconds(bootsDuration);
        System.out.println("Greenfeet activated!  They'll expire in " + bootsDuration + " seconds.");

        // Now let's use a loop to run it for this duration
        while (Instant.now().isBefore(expireTime)) {
            // Ask the Minecraft game where the player is
            Vec playerPosition = minecraft.player.getPosition();

            // Get the position beneath the player
            Vec positionBeneath = playerPosition.add(0, -1, 0);

            // Let's get the block they're standing on... or 1 Y coordinate below
            Block blockBeneath = minecraft.getBlock(positionBeneath);
            
            // Only convert dirt blocks if they're being stood on
            if (blockBeneath.equals(Block.DIRT)) {
                // This is the actual conversion
                minecraft.setBlock(positionBeneath, Block.GRASS);
            }

            // We don't want to check too fast, it'll spam the server... ~60 times/second should be good
            Thread.sleep(1000 / 60);
        }

        // Something happening in the terminal is nice too
        System.out.println("Greenfeet script completed");
    }

    // Read the configuration file and populate host
    private static void readConfig() {
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
