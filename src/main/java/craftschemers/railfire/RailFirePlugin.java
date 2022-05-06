package craftschemers.railfire;

import craftschemers.railfire.listeners.PlayerInteractListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class RailFirePlugin extends JavaPlugin {

    public static RailFirePlugin railFirePlugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        railFirePlugin = this;
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
