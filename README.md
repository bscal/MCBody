# MCBody
PaperSpigot 1.16.3 minecraft plugin that adds individual health pools to body parts (head, body, legs, arm), with the option to add damage multipliers to body parts also.
Contains an API to easily work with and hook into these events.

The plugin is built 1.16.3 I do not know compatibility with previous version, however no NMS is used.

Uses the following libraries:
[TheAPI](https://github.com/TheDevTec/TheAPI), [BKCommonLib](https://github.com/bergerhealer/BKCommonLib)
and [RaycastAPI](https://www.spigotmc.org/resources/api-raycastapi-create-guns-get-towards-entity-block.77541/) libraries.

The plugin has not been fully tested or complete.

# Install Instructions

## Servers
These are built for PaperSpigot 1.16.3 which means it should also work on Spigot.

1. Put MCBody.jar inside your plugins folder
2. Get RaycastAPI.jar [here](https://www.spigotmc.org/resources/api-raycastapi-create-guns-get-towards-entity-block.77541/) and place it in your server's plugins folder.
3. Get TheAPI.jar [here](https://www.spigotmc.org/resources/theapi.72679/) and place it in your server's plugins folder.
4. Get BKCommonLib.jar [here](https://www.spigotmc.org/resources/bkcommonlib.39590/) and place it in your server's plugins folder.

Somewhat more plugin dependencies that I wanted but it was hard trying to find some core APIs for certain features that I wanted. On the good side is BKCommonLib and TheAPI seems to be more popular and contain a lot of helpful code to speed up this and other plugins.

## Developers

### RaycastAPI
I was not sure RaycastAPI licence. It does not seem to have a maven repo so you must obtain a copy yourself from [Spigot](https://www.spigotmc.org/resources/api-raycastapi-create-guns-get-towards-entity-block.77541/).

After that you must put the jar in `/vendor/RaycastAPI` named: `RaycastAPI.jar`.
