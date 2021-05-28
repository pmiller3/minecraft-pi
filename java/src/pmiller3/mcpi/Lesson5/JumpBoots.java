package pmiller3.mcpi.Lesson5;

import pi.Block;
import pi.Minecraft;
import pi.Vec;

public class JumpBoots extends Boots {
    private static final int JUMP_HEIGHT = 10;
    private static final Block TRIGGER_BLOCK = Block.GRASS_TALL;

    public JumpBoots(Minecraft minecraft) {
        super(minecraft);
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
