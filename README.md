# Game Menu Remove

A Fabric mod for Minecraft that removes the "Report Bugs" and "Give Feedback" buttons from the pause menu. The buttons are filtered out by their translation keys (`menu.reportBugs` and `menu.sendFeedback`) before being added to the menu layout.

## Configuration

The config file is created at `config/game-menu-remove.json` on first launch:

```json
{
  "enable_mod": true
}
```

Set `enable_mod` to `false` to disable the mod. The config is reloaded every time the pause menu opens, so changes take effect without restarting the game.

## Mod Menu

If [Mod Menu](https://modrinth.com/mod/modmenu) is installed, the mod provides a config screen with an in-game toggle for `enable_mod` (Mods screen -> Game Menu Remove -> Config). The integration is optional; the mod works fine without Mod Menu.