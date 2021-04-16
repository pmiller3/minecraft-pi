# Import the library so we can talk to the game
from mcpi.minecraft import Minecraft

# Create and store a connection to our game in a variable
# By leaving the argument empty, it connects locally, but
# we could provide an IP address if we wanted to a remote pi
minecraft = Minecraft.create()

# Ask the Minecraft game where the player is
playerPosition = minecraft.player.getPos()

# We can see in the output, the player position is represented
# as a vector with 3 numbers in it - these are the X, Y and Z
# coordinates in 3d space
print(playerPosition)

# A more convenient syntax we can use
x, y, z = playerPosition

# So, to jump, we just have to move the player "up"!
# This is done by increasing the y coordinate of the position
#minecraft.player.setPos(playerPosition.x, playerPosition.y + 10, playerPosition.z)
minecraft.player.setPos(x, y + 10, z)

# Something happening in the terminal is nice too
print("Hello Minecraft script completed")