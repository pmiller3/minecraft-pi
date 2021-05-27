package pmiller3.mcpi.Lesson5;

import java.security.InvalidParameterException;

import pi.Block;
import pi.Minecraft;

public class Greenfeet implements IFootwear {
    private static final Block TRIGGER_BLOCK = Block.DIRT;
    private static final Block TRANSFORM_BLOCK = Block.GRASS;
    private Minecraft minecraft;

    public Greenfeet(Minecraft minecraft) {
        if(minecraft == null) {
            throw new InvalidParameterException("Cannot provide null minecraft connection");
        }
        this.minecraft = minecraft;
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
