package pmiller3.mcpi.Lesson5;

import java.security.InvalidParameterException;

import pi.Block;
import pi.Minecraft;

public class Greenfeet implements IFootwear {
    private static final Block TRIGGER_BLOCK = Block.DIRT;
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
            minecraft.setBlock(blockIntel.getBeneathPosition(), Block.GRASS);
            return true;
        }
        return false;
    }
}
