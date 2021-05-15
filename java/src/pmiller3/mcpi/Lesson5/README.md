# Lesson 5 - Footwear
This lesson will build on all previous lessons, and in fact, combine them into one!  We've learned and used a lot of programming basics, as well used a decent bit of the Minecraft API library, and now we're going to get into a bit more advanced programming concepts, moving beyond just a single script.  And, we'll write some tests - which might not sound like anything exciting, but as a program grows in complexity, you'll need these to check your work so when you make changes, you don't inadvertently break something else (and believe me, that's so much easier than it sounds).

## Goals
- Interfaces
- Classes
- Functions
- Unit Tests

## Breakdown
Our goal here is to get all 3 of our previous lessons and turn them into one, without duplicating a bunch of elements.  We can see the `Jump.java` could easily, instead of just being run and happening on demand, be set to a block type to cause a large jump.  And we know the algorithm for changing block types is much better in `FrostwalkerBoots.java` than `Greenfeet.java` because it figures out what block you're about to step on, rather than being on.  So let's make it so we can utilize that part, and make it really easy to create any other type of boot that you like.

In order to do that, we'll need some sort of abstraction - some more general way of grouping those similar items.  In this case, I'm choosing an `interface` `IFootwear.java`; an interface is a way of defining a set of functions that have to be implemented by all implementing classes.  I've also made a little data object to hold info we need, `BlockIntel.java`.  That's a concrete class, that holds both the `Block` the player is standing on and the `Block` they are about to stand on.  This way, we can do our boots on either one, but they all look the same thanks to the interface.

Now, each one becomes its own, smaller file that uses the `implements` keyword to indicate that its going to be a footwear, and bears the responsibility of defining that function.

And lastly, in our main `Footwear.java` class that actually runs, we build a list of `IFootwear` and fill it with each type.  Later on, we gather the `BlockIntel` only once, and feed it to all 3 footwear.  They'll decide on their own based on the data whether or not to do what they do, and actually do it.  And then we'll loop again.