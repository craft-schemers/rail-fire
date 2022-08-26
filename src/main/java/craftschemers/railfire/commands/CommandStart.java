package craftschemers.railfire.commands;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static craftschemers.railfire.RailFirePlugin.railFirePlugin;

public class CommandStart implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player || sender instanceof ConsoleCommandSender) {
            for (Player player : railFirePlugin.getServer().getOnlinePlayers()) {
                if (player.getWorld().getEnvironment() != World.Environment.NORMAL) {

                }
//                Location arenaLocation = new Location(World.Environment.NORMAL, 78, 68, -89);
                Location arenaLocation = new Location(player.getWorld(), 78, 68, -89);
                player.teleport(arenaLocation);
            }
            return true;
        }
        return false;
    }
}
