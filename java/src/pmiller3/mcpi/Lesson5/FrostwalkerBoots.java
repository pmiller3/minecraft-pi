// Create a package, or namespace, for this lesson
package pmiller3.mcpi.Lesson5;

import java.util.Arrays;
import java.util.List;

// Import the library so we can talk to the game
import pi.Minecraft;
import pi.Block;

// Java, being object oriented, wants classes defined
public class FrostwalkerBoots extends Boots {
    private static final List<Block> blocksToConvert =
        Arrays.asList(Block.WATER, Block.WATER_FLOWING, Block.WATER_STATIONARY);
    private static final Block TRANSFORM_BLOCK = Block.ICE;

    public FrostwalkerBoots(Minecraft minecraft) {
        super(minecraft);
    }

    // Implement Frostwalker Boots
    @Override
    public boolean attemptAction(BlockIntel blockIntel) {
        if(blocksToConvert.contains(blockIntel.getNextBlock())) {
            minecraft.setBlock(blockIntel.getNextBeneathPosition(), TRANSFORM_BLOCK);
            return true;
        }
        return false;
    }
}
