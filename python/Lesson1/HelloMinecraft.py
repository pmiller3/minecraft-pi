# Import the library so we can talk to the game
from mcpi.minecraft import Minecraft

# Create and store a connection to our game in a variable
# By leaving the argument empty, it connects locally, but
# we could provide an IP address if we wanted to a remote pi
minecraft = Minecraft.create()

# Make sure we get something to happen in the game
minecraft.postToChat("Hello Minecraft!")

# Something happening in the terminal is nice too
print("Hello Minecraft script completed")