![Logo](/screenshots/Logo.png)

# Veyzen Client
Veyzen Client is an open source Minecraft PVP Client for 1.8.9 using the Forge API.

## Downloading
- Download the mod from the Releases section [here](https://github.com/Arctyll/Veyzen/releases)!

## Screenshots
### TitleScreen
![TitleScreen](/screenshots/TitleScreen.png)

### HudEditor
![HudEditor](/screenshots/HudEditor.png)

### ModMenu
![ModMenu](/screenshots/ModMenu.png)
## Workspace Setup
1. Clone or download the repository either using git or the zip download.
2. Open a command proment or terminal and change the directory to Veyzen's folder.
```
cd C:\User\Desktop\Veyzen
```
3. Creating the workspace for your IDE <br>
- IntelliJ IDEA
```
gradlew setupDecompWorkspace idea
```
- Eclipse
```
gradlew setupDecompWorkspace eclipse
```
4. Open the project with your preferred IDE. Don't import it as a gradle project.
5. To get Mixins working in a Dev Environment, add the args below, to your program arguments.
```
--tweakClass org.spongepowered.asm.launch.MixinTweaker --mixin mixins.veyzen.json
```

## Building
1. Open a command proment or terminal and change the directory to the Veyzen's folder.
```
cd C:\User\Desktop\Veyzen
```
2. Make a build
```
gradlew build
```
You will find the new build in
```
C:\User\Desktop\Veyzen\build\libs
```
3. Copy the .jar file and paste it into your mods folder and launch forge for your version.

## Contributions
Feel free to fork this project, make changes and finally make a pull request to the development branch.

## License
This project is licensed under the GNU Lesser General Public License v3.0

Permissions:
- Modification 
- Distribution 
- Private use

Conditions:
- License and copyright notice
- State changes 
- Disclose source
- Same license 

This project uses code from:
- CloudClient
- superblaubeere27 (Font Renderer) https://github.com/superblaubeere27
- LaVache-FR (AnimationUtil) https://github.com/LaVache-FR
- Moulberry (MotionBlur) https://github.com/Moulberry
