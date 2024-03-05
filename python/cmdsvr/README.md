# JuicyRaspberryPie Command Server

You don't need a command server to program Minecraft.  You can use Python, Javascript, or any language as long as you implement MCPI(JuicyRaspberryPie) API.

The command server simply provides "/p" command for player in Minecraft to use.

The command server takes commands from JuicyRaspberryPie in Minecraft server, either Spigot server or a single-user world with Forge.  It finds the function that corresponds to that specific command and execute the function.

The function send the API request to JuicyRaspberryPie, JuicyRaspberryPie fulfill the request.

Let's look at an example.

You have following function `spawnDonkey(count)` defined in a file inside pplugins folder:

```
def spawnDonkey(count):
   "_mcp:"
   mc = Minecraft.create()
   pos = mc.player.getTilePos()
   for i in range(count):
       mc.spawnEntity(pos.x+2, pos.y, pos.z, 'donkey')
```

The command server registers `spawnDonkey` function when it runs.

Inside Minecraft, you issue `/p spawnDonkey 10`, this command is sent to your `spawnDonkey(count)` function, the function send API calls to JuicyRaspberryPie, 10 donkeys will be created near you(the player) in Minecraft. 

# Running JuicyRaspberryPie Python command server

## Start

If using JuicyRaspberryPie Spigot plugin:

    python startsvr.py

On Linux, you can omit "python", just do "./startsvr.py".

If you changed the default host/port number, just specify the correct --apihost/--apiport/--svrhost/--svrport options.
 
## shutdown 

Press Ctrl-C will shutdown the command server.

From within Minecraft, issuing "/p shutdownserver" will also shutdown the server

## **Note**

Please note it's best to make a copy of the directory `\bukkit\src\main\resources`, then run command server within the copied directory -- just do `python cmdsvr/pycmdsvr.py`.

If using Spigot, don't edit directly with the generated `plugins/JuicyRaspberryPie` directory because the files there might be deleted or over-written inadvertently.
