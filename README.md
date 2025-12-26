# Quickdraw Commands

A Fabric mod for Minecraft Java Edition that automatically executes commands upon joining a multiplayer server.

## Features

- **Automatic Command Execution**: Execute up to 5 commands immediately when joining a server
- **Flexible Commands**: Supports both slash commands (e.g., `/t spawn`) and chat messages
- **Configurable Cooldown**: Set a delay between commands to avoid server throttling
- **Easy Configuration**: Configure via ModMenu integration
- **Enable/Disable Toggle**: Quickly enable or disable the mod without removing it

## Use Cases

- Automatically teleport to your home town on Towny servers with `/t spawn`
- Send greeting messages when joining
- Execute multiple setup commands in sequence
- Any repetitive commands you run on every login

## Installation

1. Install [Fabric Loader](https://fabricmc.net/use/) for Minecraft 1.21.8
2. Install [Fabric API](https://modrinth.com/mod/fabric-api)
3. (Optional) Install [Mod Menu](https://modrinth.com/mod/modmenu) for in-game configuration
4. Download the Quickdraw Commands JAR and place it in your `mods` folder

## Configuration

### Via Mod Menu (Recommended)

1. Open Minecraft and go to Mods
2. Find "Quickdraw Commands" and click the configuration button
3. Configure your settings:
   - **Enabled**: Toggle the mod on/off (default: ON)
   - **Command 1-5**: Enter your commands (with or without leading `/`)
   - **Cooldown**: Set delay between commands in seconds (default: 0)

### Via Config File

Edit `config/quickdraw-commands.json`:

```json
{
  "enabled": true,
  "command1": "/t spawn",
  "command2": "",
  "command3": "",
  "command4": "",
  "command5": "",
  "cooldownSeconds": 0
}
```

## Default Configuration

- **Enabled**: ON
- **Command 1**: `/t spawn` (teleport to town spawn on Towny servers)
- **Commands 2-5**: Empty (not configured)
- **Cooldown**: 0 seconds

## Building from Source

```bash
./gradlew build
```

The compiled JAR will be in `build/libs/`.

## Requirements

- Minecraft 1.21.8
- Fabric Loader 0.16.0+
- Fabric API
- Java 21+

## License

MIT License - see [LICENSE](LICENSE) for details.
