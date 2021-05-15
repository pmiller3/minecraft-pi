package pmiller3.mcpi.Lesson5;

import pi.Block;
import pi.Minecraft;
import pi.Vec;

public class BlockIntel {
    // constant values, only to be created once
    private static final int BENEATH_OFFSET = -1;

    // private member variables hold data
    private Block blockUnderfoot;
    private Block nextBlock;
    private Vec playerPosition;
    private Vec beneathPosition;
    private Vec nextBeneathPosition;
    
    // public constructor to get data in
    public BlockIntel(Minecraft minecraft, Vec playerPosition, Vec nextBeneathPosition) {
        this.playerPosition = playerPosition;
        this.nextBeneathPosition = nextBeneathPosition;
        beneathPosition = playerPosition.add(0, BENEATH_OFFSET, 0);
        blockUnderfoot = minecraft.getBlock(beneathPosition);
        nextBlock = minecraft.getBlock(nextBeneathPosition);
    }

    // public getter to retrieve info
    public Block getBlockUnderfoot() {
        return blockUnderfoot;
    }

    // public getter to retrieve info
    public Block getNextBlock() {
        return nextBlock;
    }

    public Vec getPlayerPosition() {
        return playerPosition;
    }

    public Vec getBeneathPosition() {
        return beneathPosition;
    }

    public Vec getNextBeneathPosition() {
        return nextBeneathPosition;
    }
}
