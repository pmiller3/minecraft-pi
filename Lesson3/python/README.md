# Lesson 3 - Frostwalker Boots
This lesson will again build on the last.  We know we can connect to Minecraft pi, and get some information - namely, the players position.  Now let's ask, and the do something to alter the game world, not just the player.  Again, reference the [documentation](https://pimylifeup.com/minecraft-pi-edition-api-reference/) to help us achieve this task.

You should be able to open `FrostwalkerBoots.py` in Thonny or any other properly configured Python IDE of your choice (for the pi itself with the recommended OS, Thonny is configured out of the box), and just run it once you have also opened Minecraft pi edition (this may or may not be installed by default).

We're going to add here two key programming language parts: looping (repeating portions multiple times), and conditional statements (do this thing, sometimes, depending on these factors).  Of course, we'll still need our old friends, variables and the API calls through our friendly library.

## Goals
- Control structures (Loops and If statements)
- API calls to gather info about the world in the loop
- API calls to set info conditionally

## Breakdown
The `FrostwalkerBoots.py` is an alteration of `Jump.py`.  For purposes of this lesson, we'll break down only the new bits - the config and connection stuff all remains the same.

So let's start by breaking this down... first, an overall explanation of the plan:
```
# What are frostwalker boots?  Well, they're a bit of magic... they allow the player to
# walk on water by freezing it!  How can we achieve this?  Let's break it down into some steps...
# First, to walk on water, we'd have to know what the player is walking on, right?
# To do that, we'd need to know two things, in order:
# Where the player is in the world, and what block are they standing on?
# The API let's us do both!  We already know the first bit, so now it's
# just part two, asking the API what the block type is one block below the player.
# Then, with our knowledge in hand, we just need to check if it's water.
# If it is, we again use the API to tell the world to turn that block to ice.
# But for this to be effective, it can't just happen once... it has to happen
# for some amount of time.  So we'll have to make some sort of loop to check
# and recheck at frequent enough intervals
```

Alright, so first, let's set up some variables that aren't going to change over the course of the script:
```
# Let's get a variable ready for making this not run forever
bootsDuration = 10
expireTime = time.time() + bootsDuration
print("Frostwalker boots activated!  They'll expire in " + str(bootsDuration) + " seconds.")
blocksToConvert = [Block.WATER, Block.WATER_FLOWING, Block.WATER_STATIONARY]
```

First, I've got a duration set in seconds that I can easily change now, 10 as shown above.  I add that to the current time (the `time` library's `.time()` call returns the current time in seconds) , to let me know when to turn off.  I put some nice information in the console so we're aware (and `bootsDuration` being an Integer, I cannot just add it to Strings... that `str()` function is to turn it into a string for easy adding).  Lastly, we're going to keep a list of all the block types we want to convert, as the game represents water blocks in different ways.  Lists are neat data types, and they'll let us do a trick later.  You can check out `block.py` in the source to see them all, likely located at `/usr/lib/python3/dist-packages/mcpi/block.py` on your pi.

```
# Now let's use a loop to run it for this duration
while time.time() < expireTime :
```

Next we start our first new control statement, a `while` loop.  `while` is a keyword that basically says, while the condition presented next is true, do this block of code, re-evaluate it afterwards, and if true again, repeat.  So, in this case, while the current time in seconds is less than when we set the expiry above, do our code block, which is our frostwalker boots!

```
    # Ask the Minecraft game where the player is, in the convenient syntax
    x, y, z = minecraft.player.getPos()

    # Let's get the block they're standing on... or 1 Y coordinate below
    blockBeneath = minecraft.getBlock(x, y - 1, z)
```

Note all this code is indented.  It has to be, in Python, to recognize it as part of the loop.  What we're doing here is mostly nothing new, we're just asking, at this very moment, where is the player?  We're storing the X, Y, and Z coordinates in their own variables for ease.  Next, we're using a new call, `getBlock()` to figure out which block is at the players, position, except `y - 1`, so the block beneath them, and storing it.

```    
    # Only convert water blocks if they're being stood on
    if blockBeneath in blocksToConvert :
        # This is the actual conversion
        minecraft.setBlock(x, y - 1, z, Block.ICE)
```

Now we've got our second neat bit of code, a second control statement.  The `if` allows us to optionally run the code inside it, in the case, setting the block below the player to ice.  The `if` condition there uses an `in` keyword, something special we can do with our lists of blocks, and we're just asking, "is the block type, of the block that is beneath the player, one of the types in this list" which, in our case, are the three WATER type blocks.  So, we'll only change the block to ice, if it's water.

```
    # We don't want to check too fast, it'll spam the server... ~60 times/second should be good
    time.sleep(0.0166667)
```

And this last part is just timing.  The computer will run as fast as it can, but that means lots, and lots, of reaching out to the game for info.  This is usually not desirable, as the connections can only handle so much (and normally, things going over networks probably have more than one connection at a time!), so we tell it to only check so often.  The `time.sleep()` function takes a numerical argument, the number of seconds to essentially wait for.  In our case, I'm using `0.0166667` because it's approximately 1/60th of a second - many games run on a loop expected to execute 60 times per second.

Now, as-is, this doesn't work quite perfectly - what we really need to know is not where the player is, but where they're *about* to go.