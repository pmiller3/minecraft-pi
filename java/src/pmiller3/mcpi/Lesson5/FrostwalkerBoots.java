// Create a package, or namespace, for this lesson
package pmiller3.mcpi.Lesson5;

// imports for reading config file, and doing time maths
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;

// Import the library so we can talk to the game
import pi.Minecraft;
import pi.Block;

// Java, being object oriented, wants classes defined
public class FrostwalkerBoots implements IFootwear {
    private static final List<Block> blocksToConvert =
        Arrays.asList(Block.WATER, Block.WATER_FLOWING, Block.WATER_STATIONARY);
    private Minecraft minecraft;

    public FrostwalkerBoots(Minecraft minecraft) {
        if(minecraft == null) {
            throw new InvalidParameterException("Cannot provide null minecraft connection");
        }
        this.minecraft = minecraft;
    }

    // Implement Frostwalker Boots
    @Override
    public boolean attemptAction(BlockIntel blockIntel) {
        if(blocksToConvert.contains(blockIntel.getNextBlock())) {
            minecraft.setBlock(blockIntel.getNextBeneathPosition(), Block.ICE);
            return true;
        }
        return false;
    }
}
