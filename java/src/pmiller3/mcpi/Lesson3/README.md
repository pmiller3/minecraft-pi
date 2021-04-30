# Lesson 3 - Greenfeet
This lesson will again build on the last.  We know we can connect to Minecraft pi, and get some information - namely, the players position.  Now let's ask, and the do something to alter the game world, not just the player.  Again, reference the [documentation](https://pimylifeup.com/minecraft-pi-edition-api-reference/) to help us achieve this task.

You should be able to open `Greenfeet.java` in any properly configured Java IDE of your choice (I used VSCode opened to the `java` folder), and just run it once you have also opened Minecraft pi edition (this may or may not be installed by default).

We're going to add here two key programming language parts: looping (repeating portions multiple times), and conditional statements (do this thing, sometimes, depending on these factors).  Of course, we'll still need our old friends, variables and the API calls through our friendly library.

## Goals
- Control structures (Loops and If statements)
- API calls to gather info about the world in the loop
- API calls to set info conditionally

## Breakdown
The `Greenfeet.java` is an alteration of `Jump.java`.  For purposes of this lesson, we'll break down only the new bits - the config and connection stuff all remains the same.

So let's start by breaking this down... first, an overall explanation of the plan:
```
# What are greenfeet?  Well, they're a bit of magic... they allow the player to
# change dirt to grass!  How can we achieve this?  Let's break it down into some steps...
# First, to change dirt, we'd have to know what the player is walking on, right?
# To do that, we'd need to know two things, in order:
# Where the player is in the world, and what block are they standing on?
# The API let's us do both!  We already know the first bit, so now it's
# just part two, asking the API what the block type is one block below the player.
# Then, with our knowledge in hand, we just need to check if it's dirt.
# If it is, we again use the API to tell the world to turn that block to grass.
# But for this to be effective, it can't just happen once... it has to happen
# for some amount of time.  So we'll have to make some sort of loop to check
# and recheck at frequent enough intervals
```

Alright, so first, let's set up some variables that aren't going to change over the course of the script:
```
        // Let's get a variable ready for making this not run forever, in seconds
        int bootsDuration = 10;
        Instant expireTime = Instant.now().plusSeconds(bootsDuration);
        System.out.println("Greenfeet activated!  They'll expire in " + bootsDuration + " seconds.");
```

First, I've got a duration set in seconds that I can easily change now, 10 as shown above.  I add that to the current time (the `java.time.Instant` library's `Instant.now()` call returns the current time in an `Instant` variable) , to let me know when to turn off.  I put some nice information in the console so we're aware; if you compare this to the Python code, you'll see Java has no problem with concatenating an `int` onto the end of a `String`.

```
        // Now let's use a loop to run it for this duration
        while (Instant.now().isBefore(expireTime)) {
```

Next we start our first new control statement, a `while` loop.  `while` is a keyword that basically says, while the condition presented next is true, do this block of code, re-evaluate it afterwards, and if true again, repeat.  So, in this case, while the current time in seconds is before when we set the expiry above, do our code block, which is our Greenfeet!  Also, you'll notice another difference here between Java and Python - the colon denotes the start of the loop, while Java uses braces (we'll need a closing one later) to define blocks of code.  Python blocks are defined by whitespaces, so in that code the tabs/indentation are actually part of the code.

```
            // Ask the Minecraft game where the player is
            Vec playerPosition = minecraft.player.getPosition();

            // Get the position beneath the player
            Vec positionBeneath = playerPosition.add(0, -1, 0);

            // Let's get the block they're standing on... or 1 Y coordinate below
            Block blockBeneath = minecraft.getBlock(positionBeneath);
```

Note all this code is before the closing brace `}` later on.  It has to be, in Java, to recognize it as part of the loop.  What we're doing here is mostly nothing new, we're just asking, at this very moment, where is the player?  We're storing the `Vec`tor coordinates in its own variables for ease, as the Java API gives us a nice `add` function, so we can add `0, -1, 0` to get a position at the same `x` and `z`, but a `y` one lower.  Next, we're using a new call, `getBlock()` to figure out which block is at the position below the player, and storing it.

```    
            // Only convert dirt blocks if they're being stood on
            if (blockBeneath.equals(Block.DIRT)) {
                // This is the actual conversion
                minecraft.setBlock(positionBeneath, Block.GRASS);
            }
```

Now we've got our second neat bit of code, a second control statement.  The `if` allows us to optionally run the code inside it, in the case, setting the block below the player to dirt.  The `if` condition there uses an `.equals` call, because in this case, `==` couldn't do the right comparison.  It's something that compares two values, so we're just asking, "is the block, of the block that is beneath the player, the type of DIRT?".  If we used `==`, it'd be kind of like having a special DIRT block outside the game, and we're asking "is this dirt block *THE* dirt block?". So, we'll only change the block to grass, if it's dirt.  You can check out `block.class` in the library to see all block types, which can be done by right clicking on `Block` in the code and using the `F12` key or a right click to go to the definition in VSCode.

```
            // We don't want to check too fast, it'll spam the server... ~60 times/second should be good
            Thread.sleep(1000 / 60);
```

And this last part is just timing.  The computer will run as fast as it can, but that means lots, and lots, of reaching out to the game for info.  This is usually not desirable, as the connections can only handle so much (and normally, things going over networks probably have more than one connection at a time!), so we tell it to only check so often.  The `Thread.sleep()` function takes a `long` argument, the number of millieseconds to essentially wait for.  In our case, I'm using `1000 / 60` because it's approximately 1/60th of a second - many games run on a loop expected to execute 60 times per second.