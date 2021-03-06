# Import the library so we can talk to the game
from mcpi.minecraft import Minecraft
# We need to know about blocks too!
from mcpi import block as Block

# Import the JSON library so we can parse our JSON config
import json

# Import time library to only loop for so long, and pause
import time 

# Open the config
host = 'localhost'    
try:
    with open('.config') as json_config:
        config = json.load(json_config)
        host = config['minecraft']['host']
except IOError:
    print("No config file loaded")
print("Connecting to: " + host)

# Create and store a connection to our game in a variable
minecraft = Minecraft.create(host)

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

# Let's get a variable ready for making this not run forever
bootsDuration = 10
expireTime = time.time() + bootsDuration
print("Greenfeet activated!  They'll expire in " + str(bootsDuration) + " seconds.")

# Now let's use a loop to run it for this duration
while time.time() < expireTime :
    # Ask the Minecraft game where the player is, in the convenient syntax
    x, y, z = minecraft.player.getPos()

    # Let's get the block they're standing on... or 1 Y coordinate below
    blockBeneath = minecraft.getBlock(x, y - 1, z)
    
    # Only convert dirt blocks if they're being stood on
    if blockBeneath == Block.DIRT :
        # This is the actual conversion
        minecraft.setBlock(x, y - 1, z, Block.GRASS)

    # We don't want to check too fast, it'll spam the server... ~60 times/second should be good
    time.sleep(0.0166667)

# Something happening in the terminal is nice too
print("Greenfeet script completed")