package craftschemers.railfire;

import craftschemers.railfire.commands.CommandStart;
import craftschemers.railfire.listeners.PlayerInteractListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class RailFirePlugin extends JavaPlugin {

    public static RailFirePlugin railFirePlugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        railFirePlugin = this;

        // Event Registry
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);

        // Command Registry
        this.getCommand("start").setExecutor(new CommandStart());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("RailFire plugin shutdown");
    }
}
