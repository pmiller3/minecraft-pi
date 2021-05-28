package pmiller3.mcpi.Lesson5;

import pi.Block;
import pi.Minecraft;

public class Greenfeet extends Boots {
    private static final Block TRIGGER_BLOCK = Block.DIRT;
    private static final Block TRANSFORM_BLOCK = Block.GRASS;

    public Greenfeet(Minecraft minecraft) {
        super(minecraft);
    }

    // Implement Greenfeet
    @Override
    public boolean attemptAction(BlockIntel blockIntel) {
        if(TRIGGER_BLOCK.equals(blockIntel.getBlockUnderfoot())) {
            minecraft.setBlock(blockIntel.getBeneathPosition(), TRANSFORM_BLOCK);
            return true;
        }
        return false;
    }
}
