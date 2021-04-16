# Lesson 1 - Hello Minecraft
This should be pretty straightfoward - just a skeletal project to demonstrate that you indeed have your development environment and Minecraft setup appropriately.
You should be able to open `HelloMinecraft.py` in Thonny or any other properly configured Python IDE of your choice (for the pi itself with the recommended OS, Thonny is configured out of the box), and just run it once you have also opened Minecraft pi edition (this may or may not be installed by default).

Also, note that this `README.md` file itself is in [Markdown](https://en.wikipedia.org/wiki/Markdown) - readable as raw text, but it converts nicely to HTML if you're looking at it on someplace like github.  Quite useful, I think.  Here's a little [cheatsheet](https://www.markdownguide.org/cheat-sheet) for understanding the syntax.

## Goals
- Development environment is setup (for Python, Thonny is what I'm using for this one)
- Library is imported correctly (with the game installed, you just need what's on line 2)
- Minecraft is running correctly (you'll know this because, well, the game is running)
- Connection can be established and we can see proof of interaction (Once it runs, hop over to the game and see the message)
- Introduction to markdown! (above!)

## Breakdown
The `HelloMinecraft.py` is only 4 lines of code - plus some comments.  For purposes of this lesson, we'll break them down here.  First, start Minecraft pi edition, and then run the script.  Starting from the top, here's what goes down:

```
# Import the library so we can talk to the game
from mcpi.minecraft import Minecraft
```

What we've done here is import the library (that is, the underlying code) that let's us talk to Minecraft.  That's great, because now we don't have to worry about that part of it - we can just get on to the good bits of actually doing things rather than worry about networking and what content is expected to traverse the connection.

```
# Create and store a connection to our game in a variable
# By leaving the argument empty, it connects locally, but
# we could provide an IP address if we wanted to a remote pi
minecraft = Minecraft.create()
```

Now that we've got the code, we need to actually connect.  In this case, `Minecraft.create()` is called without any arguments, so it will connect locally (to `"localhost"`).  You could use something like `Minecraft.create("123.123.123.123")` where it takes an IP address as a string to connect to a remote machine.  

```
# Make sure we get something to happen in the game
minecraft.postToChat("Hello Minecraft!")
```

With our connection in place, we can use it for our most basic function - posting something to the chat window.  With a successful connection, we'll see the message in the Minecraft game!

```
# Something happening in the terminal is nice too
print("Hello Minecraft script completed");
```

And lastly, this line just prints some information to the console, or the terminal, that's running in Thonny.  This is useful to put some debug information there, stuff you don't necessarily want to see in the game.  It will become more useful later on, so it's good to have something like this.