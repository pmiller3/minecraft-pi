package pmiller3.mcpi.Lesson5;

// Interface to define common footwear functions/actions
public interface IFootwear {
    // Function all footwear have to implement
    // Returns true if it activated, false if not
    boolean attemptAction(BlockIntel blockIntel);
}
