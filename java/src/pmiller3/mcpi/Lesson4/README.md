# Lesson 4 - Frostwalker Boots
This lesson will again build on the last.  We get information about the world, like what block is at a position, and set a particular position's block.  Now we're going to again do something to alter the game world, but with a little added spice.  Again, reference the [documentation](https://pimylifeup.com/minecraft-pi-edition-api-reference/) to help us achieve this task, and inspiration as well as the core code in the loop for this was taken from [Martin O'Hanlon's Skybridge example](https://github.com/martinohanlon/minecraft-bridge/blob/master/minecraft-bridge.py).

You should be able to open `FrostwalkerBoots.java` in any properly configured Java IDE of your choice, and just run it once you have also opened Minecraft pi edition (this may or may not be installed by default).

We're going to add here two key programming language parts: Lists, which hold multiple data elements at once, and functions, breaking out portions of the code into logical chunks we can work with.

## Goals
- Tracking state over time
- Lists!
- Functions!

## Breakdown
The `FrostwalkerBoots.java` is an alteration of `Greenfeet.java`.  For purposes of this lesson, we'll break down only the new bits - the config and connection stuff all remains the same.

So let's start by breaking this down... first, an overall explanation of the plan, similar to the last:
```
        // What are frostwalker boots?  Well, they're a bit of magic... they allow the player to
        // walk on water by freezing it!  How can we achieve this?  Let's break it down into some steps...
        // First, to walk on water, we'd have to know what the player is walking on, right?
        // To do that, we'd need to know two things, in order:
        // Where the player is in the world, and what block are they standing on?
        // The API let's us do both!  We already know the first bit, so now it's
        // just part two, asking the API what the block type is one block below the player.
        // Then, with our knowledge in hand, we just need to check if it's water.
        // If it is, we again use the API to tell the world to turn that block to ice.
        // But for this to be effective, it can't just happen once... it has to happen
        // for some amount of time.  So we'll have to make some sort of loop to check
        // and recheck at frequent enough intervals
```

At the top, we'll need two new datatypes already defined by Minecraft, because we'll need to do some rounding on it.  And to do the rounding, we'll put that in its own function - more on those in a future lesson.  `Vec` and `VecFloat` both hold positions stored as 3 numbers, x, y, and z, but the difference is that `VecFloat` stores them as `float` values, which in Java are decimal numbers, while `Vec` stores `int` values, which are just whole numbers.  This is an important distinction - while we want to manipulate whole `Block`s using `Vec`, we want to calculate player position and heading using `VecFloat`.

```
import pi.Vec;
import pi.VecFloat;
// ... other code
    // Function to round players float position to integer position
    private static Vec roundVecFloat(VecFloat vecFloat) {
        return Vec.xyz((int)vecFloat.x, (int)vecFloat.y, (int)vecFloat.z);
    }
```

On to the action.  First, let's set up a new variable holding multiple types of blocks, because Minecraft stores water in a few ways:
```
Block[] blocksToConvert = new Block[]{Block.WATER, Block.WATER_FLOWING, Block.WATER_STATIONARY};
```

We're going to keep an array (and this is a little rare - oftentimes you'll use a nicer class to hold these, like a `List<Block>` with an `ArrayList<Block>` implementation, but we'll save that for another lesson) of all the block types we want to convert, as the game represents water blocks in different ways.  Lists and Arrays are neat data types, and they'll let us do a trick later.

```    
                // Only convert water blocks if they're being stood on
                for (Block blockToConvert : blocksToConvert) {
                    // This is the actual conversion
                    if(blockToConvert.equals(blockBeneath))
                        minecraft.setBlock(blockBelowPosition, Block.ICE);
                }
```

The `for` loop here uses a special syntax, using the `:` there.  It's saying, using the Array/List to the right of this `:`, get elements out one at a time, and store them in the `Block` variable `blockToConvert`.  Inside, now I have one `Block` at a time from my list of blocks!  Now, it's easy to check, and we have a new `if` just comparting the block type for this loop iteration, to the block we're about to stand over.  Over, this says, "is the block type, of the block that is beneath the player, one of the types in this list" which, in our case, are the three WATER type blocks.  So, we'll only change the block to ice, if it's water.

The rest of the new logic in there is converted from Martin's Skybridge solution and pretty well commented, but the short of it is we're tracking a bunch of where the player was last time vs right now to get a heading, and trying to figure out the the next block beneath them will be.  And each time we do this check, which relies on the change being great enough, we store where they're at now so the next time we check, we know where they were before.