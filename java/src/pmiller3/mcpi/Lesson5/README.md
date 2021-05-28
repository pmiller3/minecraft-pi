# Lesson 5 - Footwear
This lesson will build on all previous lessons, and in fact, combine them into one!  We've learned and used a lot of programming basics, as well used a decent bit of the Minecraft API library, and now we're going to get into a bit more advanced programming concepts, moving beyond just a single script.  And, we'll write some tests - which might not sound like anything exciting, but as a program grows in complexity, you'll need these to check your work so when you make changes, you don't inadvertently break something else (and believe me, that's so much easier than it sounds).

## Goals
- Interfaces
- Classes
- Functions
- Unit Tests

## Breakdown
Our goal here is to get all 3 of our previous lessons and turn them into one, without duplicating a bunch of elements.  We can see the `Jump.java` could easily, instead of just being run and happening on demand, be set to a block type to cause a large jump.  And we know the algorithm for changing block types is much better in `FrostwalkerBoots.java` than `Greenfeet.java` because it figures out what block you're about to step on, rather than being on.  So let's make it so we can utilize that part, and make it really easy to create any other type of boot that you like.

In order to do that, we'll need some sort of abstraction - some more general way of grouping those similar items.  In this case, I'm choosing an `interface` `IFootwear.java`; an interface is a way of defining a set of functions that have to be implemented by all implementing classes, so you can use them in a uniform way, but aren't concerned about the underlying mechanics.  I've also made a little data object to hold info we need, `BlockIntel.java`.  That's a concrete class, that holds both the `Block` the player is standing on and the `Block` they are about to stand on.  This way, we can do our boots on either one, but they all look the same thanks to the interface.

```
public interface IFootwear {
    boolean attemptAction(BlockIntel blockIntel);
}
```

That's it, that's all the relevant code in the interface; just a series of function signatures that all implementing classes will have to have (if not, it's a compilation error).  Next, let's look at an `abstract` parent class, `Boots.java`:

```
public abstract class Boots implements IFootwear {
    protected Minecraft minecraft;

    public Boots(Minecraft minecraft) {
        if(minecraft == null) {
            throw new InvalidParameterException("Cannot provide null minecraft connection");
        }
        this.minecraft = minecraft;
    }
}
```

The abstract keyword means that you can't just instantiate `new Boots()`.  Also note the keyword `implements`.  That means this class will have to implement the `attemptAction()` function from the interface.  However, we don't have to do this one, as it's abstract; but, any child classes will have to do so.  We're doing this to share code, particularly what's in that constructor, holding the connection to Minecraft and the requirements the interface puts on it.  Crucially, it's also at the access level `protected` - there are plenty of resources on these levels, but `private` is restricted to just the file/scope in question, while `public` is open to everybody, so `protected` is our best choice as it lets package peers have access.  Now let's look at one of those peers, an implementation:

```
public class Greenfeet extends Boots {
    private static final Block TRIGGER_BLOCK = Block.DIRT;
    private static final Block TRANSFORM_BLOCK = Block.GRASS;

    public Greenfeet(Minecraft minecraft) {
        super(minecraft);
    }

    // Implement Greenfeet
    @Override
    public boolean attemptAction(BlockIntel blockIntel) {
        if(TRIGGER_BLOCK.equals(blockIntel.getBlockUnderfoot())) {
            minecraft.setBlock(blockIntel.getBeneathPosition(), TRANSFORM_BLOCK);
            return true;
        }
        return false;
    }
}
```

First we define the class name, its access level, and most importantly, that it extends `Boots`, so it gets the code in that class.  We then have a couple private member variables, which are `static` - that means its the same for the whole class, `Greenfeet` in this instance.  It's also `final` meaning it will never change - just a convenience to make the program not store it over and over again (because greenfeet will always trigger on DIRT, and always make GRASS).  The third is neither of those, but we need something to hold the connection to the game, so we can set the block later.  The first function is a `constructor`, it tells you how to get a new instance of these Greenfeet boots.  So you could actually run a program connecting to multiple Minecraft games and make Greenfeet boots for each of them.  It uses the `super` keyword to call the constructor for the parent `Boots` class first, saving us the trouble and giving us a common bit we need. The second function, with the `@Override` annotation, is what implements the interface.  There, we put all of our Greenfeet specific code - you can see it uses the two static fields to check and set block types, and the connection that was passed in and stored from the constructor.

Now, each one becomes its own, smaller file, known as a `class` (which, in Java, really everything is) that uses the `implements` keyword to indicate that its going to be a footwear, and bears the responsibility of defining that function.  Classes are a basic building block of Object-Oriented programming; they provide a template for some concept you want to create, in our case, these specific boots.  We also extended a class, our `abstract` Boot class, that has general things that apply to all Boots, and extended into specific ones.  You can see in the example above, the specific logic to Greenfeet is really all that's in the class.  Everything else has been defined once elsewhere, so we've done a decent job at minimizing code to write to make new Boots.

And lastly, in our main `Footwear.java` class that actually runs, we build a list of `IFootwear` and fill it with one of each type.  Later on, we gather the `BlockIntel` only once, and feed it to all 3 footwear.  They'll decide on their own based on the data whether or not to do what they do, and actually do it.  And then we'll loop again.  It doesn't need to know which footwear it's actually got, just that it can call `attemptAction()` and the concrete implementation will take care of the rest.