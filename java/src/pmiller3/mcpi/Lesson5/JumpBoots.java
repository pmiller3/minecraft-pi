package pmiller3.mcpi.Lesson5;

import java.security.InvalidParameterException;

import pi.Block;
import pi.Minecraft;
import pi.Vec;

public class JumpBoots implements IFootwear {
    private static final int JUMP_HEIGHT = 10;
    private static final Block TRIGGER_BLOCK = Block.GRASS_TALL;
    private Minecraft minecraft;

    public JumpBoots(Minecraft minecraft) {
        if(minecraft == null) {
            throw new InvalidParameterException("Cannot provide null minecraft connection");
        }
        this.minecraft = minecraft;
    }

    // Implement Jump Boots
    @Override
    public boolean attemptAction(BlockIntel blockIntel) {
        if(TRIGGER_BLOCK.equals(blockIntel.getBlockUnderfoot())) {
            // Ask the Minecraft game where the player is and store it
            Vec playerPosition = minecraft.player.getPosition();
            // Move the player by JUMP_HEIGHT blocks
            minecraft.player.setPosition(playerPosition.add(0, JUMP_HEIGHT, 0));
            return true;
        }
        return false;
    }
}
