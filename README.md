# MGManager
Stand-alone framework to create Minecraft Minigames plugins (Java 1.8)

## Workspace setup

This guide was made for IntelliJ IDEA Ultimate. It should still work on the Community version. For other IDEs, use google. The devs do not take responsibility for any faulty setup you prepare on your own.

### Downloading the source

As a tool of choice, we suggest you use the git Client [GitKraken](https://www.gitkraken.com/) as it is the one used on the project. Follow the steps on the client to clone the repo from GitHub: `File -> Clone Repo`, select `Clone with URL` and copy and paste `https://github.com/EnlitHamster/MGManager.git` in the URL field; select the folder you want to download the sources to and hit `Clone the repo!`. If you use git CLI or another GUI, please refer to their guides on how to do so.

### Preparing the IDE

For ease of usage, we recommend you install the [Minecraft Development](https://plugins.jetbrains.com/plugin/8327-minecraft-development) plugin on IntelliJ. If you are using another IDE, it is up to you choose the tools that better suite your coding style.

The repo contains already a `.iml` file that is the project file for IntelliJ. On all other IDEs you will have to import the folder manually. If you are using IntelliJ, you can just hit `Open...` and select the `.iml` of the project.

### Setting up testing server

If you want to make the dev & test flow smoother, we suggest you take the time to follow the steps below. To setup the server, follow [this](https://minecraft.gamepedia.com/Tutorials/Setting_up_a_Spigot_server) guide. The folder in which you install the server will from now be called `root`.

On IntelliJ IDEA:
- Select `File -> Project Structure...`
- Select `Artifacts`
- Hit the `+` button and select `empty`
  - Change `Name` to `MGManager`
  - Change `Output directory` to `root/plugins`
  - In `Output Layout` add `'MGManager' compile output` to the JAR file
- Hit `OK`
- Create a new `Run/Debug Configuration`
- Hit the `+` button and select `JAR Application`
- In the configuration window set the values as follows:
  - `Path to JAR`: `root/spigot-1.16.4.jar` (or whatever your spigot JAR file is called)
  - `VM options`: `-XmsMG -XmxNG -XX:UserG1GC` where `M` is the starting amount of RAM and `N` the maximum given to the server
  - `Program arguments`: `nogui`
  - `JRE`: `1.8`
  - In `Before launch` hit the `+`, select `Build Artifacts`, check `MGManager` and hit `OK`
- Hit `OK`
> This configuration allows you to easily run the server from IntelliJ IDEA
  
