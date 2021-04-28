# Import the library so we can talk to the game
from mcpi.minecraft import Minecraft

# Import the JSON library so we can parse our JSON config
import json

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

# Ask the Minecraft game where the player is
playerPosition = minecraft.player.getPos()

# We can see in the output, the player position is represented
# as a vector with 3 numbers in it - these are the X, Y and Z
# coordinates in 3d space
print(playerPosition)

# A more convenient syntax we can use
x, y, z = playerPosition

# Something happening in the terminal is nice too
print("Hello Minecraft script completed")