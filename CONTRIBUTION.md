## How to contribute

Through the `Issues` page here on GitHub you can see what is actually needed in the plugin. Create your own branch of the repo or your own repo and work on the issue. Please, inform us in the section that you are indeed working on it so we can focus our efforts efficiently. Once you are done, open a pull request linked to the Issue you are working on.

Please, create/update JavaDoc for everything you do while working on an issue and pull requrest only when documentation is complete. Incomplete documentation will not be accepted.

If you find any bug, problem please open an issue specifying the following information:
- What happened: 
- When did it happen:
- What particular steps caused the issue:
- What else was happening on the server:
- Log of the console:
If need be, attach images of the issue.

If you have any feature you would like to see implemented, open an issue as well describing in detail what the idea is. If you want to work on it yourself, follow the same process as previously stated for issues. If none of the official developers are focused on more important issues or deem the idea not worth working on, we will still leave the idea there and anyone who finds it interesting can work on it.

## Coding standards and principles

As dictated by modern Software Engineering practices, we value the quality of our code as it helps us maintain this project in the long term with little effort. This helps us and the users in multiple ways:
1) Maintenance of code is easier and quicker, making bug fixing less tedious and faster than usually required.
2) Approaching the code as a new developer is easier, as documentation and readability are favored. We believe in Open Source development and thus will not stop anyone from joining us in this adventure; thus helping newbies is quite important to us.
3) Quality software allows us to add features faster and improve at a more steady rate, which will benefit everyone.

As mentioned above thus, here are the standards and best practices we ask from those who are willing to contribute to this project.

### Software Design Patterns

If you are not familiar with desing patterns, please take the time to read some of the following sources. We will try and add more as the project evolves. If you find documentated patterns missing, please open an issue so we can add them (after reviewing them).

- [Java Point](https://www.javatpoint.com/design-patterns-in-java)
- [Journal Dev](https://www.journaldev.com/1827/java-design-patterns-example-tutorial)

### Cloning

As defined in literature, different kinds of clone exist, and different reasons for clones as well. In order to facilitate maintenance, and as we plan on developing our own maintenance planner integration, we ask that, whenever you copy code to this project you document it in an appropriate way. The only hard requirement we ask is that you open your documentation with this line of comment: `// --- BEGIN CODE CLONE` and close it with `// --- CLOE CODE CLONE`. Beneath is the suggest documentation practice for code clones.

#### From this project
```java
// -- BEGIN CODE CLONE
// from: io.github.enlithamster.mgmanager.commands.directives.ToolHighlightDirective
// date: 30/12/2020
// by: EnlitHamster
// reason: specialized version of drawArena and decoupling from ToolHighlightDirective
private static void drawArena(Location loc1, Location loc2, int y, Material material) {
    World world = Objects.requireNonNull(loc1.getWorld());

    int x1 = loc1.getBlockX();
    int x2 = loc2.getBlockX();
    int z1 = loc1.getBlockZ();
    int z2 = loc2.getBlockZ();

    Utils.lineBetween(world, x1, y, z1, x2, y, z1, material);
    Utils.lineBetween(world, x2, y, z1, x2, y, z2, material);
    Utils.lineBetween(world, x2, y, z2, x1, y, z2, material);
    Utils.lineBetween(world, x1, y, z2, x1, y, z1, material);
    // Added code from original
    Utils.lineBetween(world, x1, y, z1, x2, y, z2, material);
    Utils.lineBetween(world, x1, y, z2, x1, y, z2, material);
}
// -- END CODE CLONE
```

#### From external source
```java
// -- BEGIN CODE CLONE
// from: https://stackoverflow.com/questions/65506024/i-need-to-find-the-first-maximum-element-in-a-2d-matrix-using-java-but-the-code
// date: 30/12/2020
// by: EnlitHamster
int max = matrix[0][0];
int[] maxPos = new int[2];
for (int i = 0; i < n; i++) {
    for (int j = 0; j < m; j++) {
        if (matrix[i][j] > max) {
            max = matrix[i][j];
            maxPos[0] = i;
            maxPos[1] = j;
        }
    }            
}
// -- END CODE CLONE
```

We suggest you read [Kapser & Godfrey, *"Cloning Considered Harmful" Considered Harmful*, 2006 13th Working Conference on Reverse Engineering, Benevento, 2006, pp. 19-28](https://ieeexplore.ieee.org/document/4023973) as it provides precious information on cloning patterns and practices.

### Spigot standards

Spigot Moderator Choco has published and maintains a [list of *mistakes* to avoid](https://www.spigotmc.org/threads/beginner-programming-mistakes-and-why-youre-making-them.278876/) in Spigot plugins. We suggest you take a look at this content as it is invaluable, and you could learn what and what not to do in different situations while coding. The ensued discussion in the thread also deserves a reading in our opinion.
