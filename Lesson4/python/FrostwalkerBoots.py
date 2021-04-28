# Import the library so we can talk to the game
from mcpi.minecraft import Minecraft
# We need to know about blocks too!
from mcpi import block as Block
# We need the vec3 datatype
from mcpi.vec3 import Vec3

# Import the JSON library so we can parse our JSON config
import json

# Import time library to only loop for so long, and pause
import time 

# Function to round players float position to integer position
def roundVec3(vec3):
    return Vec3(int(vec3.x), int(vec3.y), int(vec3.z))

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

# This first attempt doesn't quite work right... we really need to find out
# if the player is about to go over top water, not if they're already falling in

# Let's get a variable ready for making this not run forever
bootsDuration = 30
expireTime = time.time() + bootsDuration
print("Frostwalker boots activated!  They'll expire in " + str(bootsDuration) + " seconds.")
blocksToConvert = [Block.WATER, Block.WATER_FLOWING, Block.WATER_STATIONARY]
lastX, lastY, lastZ = minecraft.player.getPos()

# Now let's use a loop to run it for this duration
while time.time() < expireTime :
    # Ask the Minecraft game where the player is
    playerPosition = minecraft.player.getPos()
    x, y, z = playerPosition

    # Find the difference between the player's position and the last position
    # But only the non-vertical directions
    movementX = lastX - x
    movementZ = lastZ - z

    #Has the player moved more than 0.2 in any horizontal (x,z) direction
    if ((movementX < -0.2) or (movementX > 0.2) or (movementZ < -0.2) or (movementZ > 0.2)):
        # Project players direction forward to the next square
        nextPlayerPosition = playerPosition
        # Keep adding the movement to the players location till the next block is found
        while ((int(playerPosition.x) == int(nextPlayerPosition.x)) and (int(playerPosition.z) == int(nextPlayerPosition.z))):
            nextPlayerPosition = Vec3(nextPlayerPosition.x - movementX, nextPlayerPosition.y, nextPlayerPosition.z - movementZ)

        # Is the block below the next player position water? If so fill it in with ICE
        # Note the use of our function here!
        blockBelowPosition = roundVec3(nextPlayerPosition)
        # Resolve issues with negative positions
        if blockBelowPosition.z < 0: blockBelowPosition.z = blockBelowPosition.z - 1
        if blockBelowPosition.x < 0: blockBelowPosition.x = blockBelowPosition.x - 1
        # Look 1 Y coordinate below
        blockBelowPosition.y = blockBelowPosition.y - 1

        # Let's get the block beneath the player's heading
        blockBeneath = minecraft.getBlock(blockBelowPosition)
        
        # Only convert water blocks if they're being stood on
        if blockBeneath in blocksToConvert :
            # This is the actual conversion
            minecraft.setBlock(blockBelowPosition, Block.ICE)

        # Update the last known position, note this is still inside the loop
        # If it were outside, it might update too fast to track changes in movement
        lastX, lastY, lastZ = x, y, z

    # We don't want to check too fast, it'll spam the server... ~60 times/second should be good
    time.sleep(0.0166667)

# Something happening in the terminal is nice too
print("Hello Minecraft script completed")