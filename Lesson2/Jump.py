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
print("Jump! script completed")