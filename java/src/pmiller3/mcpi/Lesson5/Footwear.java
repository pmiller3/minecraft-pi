// Create a package, or namespace, for this lesson
package pmiller3.mcpi.Lesson5;

// imports for reading config file, and doing time maths
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

// Import the library so we can talk to the game
import pi.Minecraft;
import pi.Block;
import pi.Vec;
import pi.VecFloat;

// Java, being object oriented, wants classes defined
public class Footwear {
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

        // What are frostwalker boots?  Well, they're a bit of magic... they allow the player to
        // walk on water by freezing it!  How can we achieve this?  Let's break it down into some steps...
        // First, to walk on water, we'd have to know what the player is walking on, right?
        // To do that, we'd need to know two things, in order:
        // Where the player is in the world, and what block are they standing on?
        // The API let's us do both!  We already know the first bit, so now it's
        // just part two, asking the API what the block type is one block below the player.
        // Then, with our knowledge in hand, we just need to check if it's water.
        // If it is, we again use the API to tell the world to turn that block to ice.
        // But for this to be effective, it can't just happen once... it has to happen
        // for some amount of time.  So we'll have to make some sort of loop to check
        // and recheck at frequent enough intervals

        // Let's get a variable ready for making this not run forever, in seconds
        int bootsDuration = 10;
        Instant expireTime = Instant.now().plusSeconds(bootsDuration);
        System.out.println("Frostwalker Boots activated!  They'll expire in " + bootsDuration + " seconds.");
        long frametime = 1000 / 60; // 60 frames per second, of FPS, in milliseconds

        // Build and hold all types of footwear we want.
        List<IFootwear> footwear = new ArrayList<IFootwear>();
        footwear.add(new JumpBoots(minecraft));
        footwear.add(new Greenfeet(minecraft));
        footwear.add(new FrostwalkerBoots(minecraft));

        // Initialize lastPosition to current position so it's not empty the first go
        // Not the use of VecFloat vs Vec... VecFloats are more precise and use decimals
        VecFloat lastPosition = minecraft.player.getExactPosition();

        // Now let's use a loop to run it for this duration
        while (Instant.now().isBefore(expireTime)) {
            // Ask the Minecraft game where the player is
            VecFloat playerPosition = minecraft.player.getExactPosition();
            
            // Find the difference between the player's position and the last position
            // But only the non-vertical directions
            float movementX = lastPosition.x - playerPosition.x;
            float movementZ = lastPosition.z - playerPosition.z;

            //Has the player moved more than 0.2 in any horizontal (x,z) direction
            if ((movementX < -0.2) || (movementX > 0.2) || (movementZ < -0.2) || (movementZ > 0.2)) {
                // Project players direction forward to the next square
                VecFloat nextPlayerPosition = playerPosition;
                // Keep adding the movement to the players location till the next block is found
                while (playerPosition.x == nextPlayerPosition.x && playerPosition.z == nextPlayerPosition.z) {
                    nextPlayerPosition = VecFloat.xyz(
                        nextPlayerPosition.x - movementX, 
                        nextPlayerPosition.y, 
                        nextPlayerPosition.z - movementZ);
                }

                // Is the block below the next player position water? If so fill it in with ICE
                // Note the use of our function here!
                Vec blockBelowPosition = roundVecFloat(nextPlayerPosition);
                // Resolve issues with negative positions
                // Note that single line if statements do not require braces
                if (blockBelowPosition.z < 0) blockBelowPosition = blockBelowPosition.add(0, 0, -1);
                if (blockBelowPosition.x < 0) blockBelowPosition = blockBelowPosition.add(-1, 0, 0);
                // Look 1 Y coordinate below
                blockBelowPosition = blockBelowPosition.add(0, -1, 0);

                // Create block intel
                BlockIntel blockIntel = new BlockIntel(minecraft, 
                    roundVecFloat(playerPosition), blockBelowPosition);

                // Run all the footwear
                for (IFootwear onePair : footwear) {
                    // This is the actual work
                    if(onePair.attemptAction(blockIntel)) {
                        System.out.println("Activated footwear!");
                    }
                }

                // Update the last known position, note this is still inside the loop
                // If it were outside, it might update too fast to track changes in movement
                lastPosition = playerPosition;
            }

            // We don't want to check too fast, it'll spam the server... ~60 times/second should be good
            Thread.sleep(frametime);
        }

        // Something happening in the terminal is nice too
        System.out.println("Frostwalker Boots script completed");
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

    // Function to round players float position to integer position
    private static Vec roundVecFloat(VecFloat vecFloat) {
        return Vec.xyz((int)vecFloat.x, (int)vecFloat.y, (int)vecFloat.z);
    }
}
