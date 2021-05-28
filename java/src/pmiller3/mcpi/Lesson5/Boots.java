package pmiller3.mcpi.Lesson5;

import java.security.InvalidParameterException;
import pi.Minecraft;

public abstract class Boots implements IFootwear {
    protected Minecraft minecraft;

    public Boots(Minecraft minecraft) {
        if(minecraft == null) {
            throw new InvalidParameterException("Cannot provide null minecraft connection");
        }
        this.minecraft = minecraft;
    }
}
