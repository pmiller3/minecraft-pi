# Lesson 1 - Hello Minecraft
This should be pretty straightfoward - just a skeletal project to demonstrate that you indeed have your development environment and Minecraft setup appropriately.
You should be able to open this folder in VS Code (the OS should have installers for this) or any other properly configured Java IDE of your choice, and just run `HelloMinecraft.java` once you have also opened Minecraft pi edition (this may or may not be installed by default).

Also, note that this `README.md` file itself is in [Markdown](https://en.wikipedia.org/wiki/Markdown) - readable as raw text, but it converts nicely to HTML if you're looking at it on someplace like github.  Quite useful, I think.  Here's a little [cheatsheet](https://www.markdownguide.org/cheat-sheet) for understanding the syntax.

## Goals
- Development environment is setup (for Java, VS Code is what I'm using for this one)
- Library is imported correctly (with the game installed, you just need what's on line 2)
- Minecraft is running correctly (you'll know this because, well, the game is running)
- Connection can be established and we can see proof of interaction (Once it runs, hop over to the game and see the message)
- Introduction to markdown! (above!)

## Breakdown
The `HelloMinecraft.java` is only a few real lines of code - plus some structure and comments.  For purposes of this lesson, we'll break them down here.  First, start Minecraft pi edition, and then run the script.  First, unlike Python, you'll need the library itself as part of the project (and there are other dependency management systems out there, but this will do for ease of use) - it's the `lib/McPi.jar` file with the project.  Starting from the top, here's what goes down:

```
// Create a package, or namespace, for this lesson
package pmiller3.mcpi.Lesson1;
// Import the library so we can talk to the game
import pi.Minecraft;
```

What we've done here is created a namespace, using the `package` keyword to differentiate the code in this lesson from any future ones.  Then, using the `import` keyword, we've brought in the library (that is, the underlying code) that let's us talk to Minecraft.  That's great, because now we don't have to worry about that part of it - we can just get on to the good bits of actually doing things rather than worry about networking and what content is expected to traverse the connection.

```
// Java, being object oriented, wants classes defined
public class HelloMinecraft {
    // Java will start a program from a static function named "main"
    public static void main(String[] args) throws Exception {
        // Create and store a connection to our game in a variable
        // By leaving the argument empty, it connects locally, but
        // we could provide an IP address if we wanted to a remote pi
        Minecraft minecraft = Minecraft.connect();
        ... more code explained below
    }
}
```

Now that we've got the code, we need to define a class and function to run in, and actually connect.  The class name is the same as the filename, and a `public static void main` function is used as a starting point (we can have many classes and many functions in the project).  That's just some basic Java structure.  Later on, we get the actual line of code we want.  In this case, `Minecraft.create()` is called without any arguments, so it will connect locally (to `"localhost"`).  You could use something like `Minecraft.create("123.123.123.123")` where it takes an IP address as a string to connect to a remote machine.  

```
        // Make sure we get something to happen in the game
        minecraft.postToChat("Hello Minecraft, from Java!");
```

With our connection in place, we can use it for our most basic function - posting something to the chat window.  With a successful connection, we'll see the message in the Minecraft game!

```
        // Something happening in the terminal is nice too
        System.out.println("That's all, folks.");
```

And lastly, this line just prints some information to the console, or the terminal, that's running in your IDE.  This is useful to put some debug information there, stuff you don't necessarily want to see in the game.  It will become more useful later on, so it's good to have something like this.

# Boilerplate from VS Code

This section was auto-generated by VS Code - some useful info on how they structure Java projects.

## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

## Dependency Management

The `JAVA DEPENDENCIES` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-pack/blob/master/release-notes/v0.9.0.md#work-with-jar-files-directly).
