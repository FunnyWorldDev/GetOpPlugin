package org.oftx.getopplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.UUID;

public class GetOpPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Save the default config file if it doesn't exist
        this.saveDefaultConfig();
        // Register the /getop command executor
        this.getCommand("getop").setExecutor(new GetOpCommandExecutor(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public class GetOpCommandExecutor implements CommandExecutor {
        private final GetOpPlugin plugin;

        public GetOpCommandExecutor(GetOpPlugin plugin) {
            this.plugin = plugin;
        }

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                UUID playerUUID = player.getUniqueId();
                FileConfiguration config = plugin.getConfig();
                List<String> allowedUUIDs = config.getStringList("allowed-uuids");

                if (allowedUUIDs.contains(playerUUID.toString())) {
                    if (!player.isOp()) {
                        player.setOp(true);
                        player.sendMessage("You are now an operator!");
                    } else {
                        player.sendMessage("You are already an operator!");
                    }
                } else {
                    player.sendMessage("You are not allowed to use this command.");
                }
                return true;
            } else {
                sender.sendMessage("Only players can use this command.");
                return false;
            }
        }
    }
}
