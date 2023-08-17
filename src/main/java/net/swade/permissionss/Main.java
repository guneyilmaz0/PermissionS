package net.swade.permissionss;

import cn.nukkit.plugin.PluginBase;
import lombok.Getter;
import net.swade.permissionss.group.GroupManager;
import net.swade.permissionss.listener.PlayerListener;
import net.swade.permissionss.permission.PermissionManager;

public class Main extends PluginBase {

    @Getter private static Main instance;
    @Getter private String playerConfigPath;

    @Override
    public void onLoad() {
        instance = this;
        playerConfigPath  = getDataFolder().getPath() + "/players.json";
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getCommandMap().register("test", new TestCommand());

        GroupManager.load();
        getLogger().info("Groups Loaded");
        getLogger().info("Groups size: " + GroupManager.groups.size());
        PermissionManager.addPermsToOnlinePlayers();
    }

    @Override
    public void onDisable() {
        PermissionManager.removePermissions();
    }
}