package net.swade.permissionss;

import cn.nukkit.plugin.PluginBase;
import lombok.Getter;
import net.swade.permissionss.group.GroupManager;

public class Main extends PluginBase {

    @Getter private static Main instance;
    @Getter private String playerConfigPath;

    @Override
    public void onLoad() {
        playerConfigPath  = getDataFolder().getPath() + "/players.json";
        instance = this;
        saveDefaultConfig();
        GroupManager.load();
        getLogger().info("Groups Loaded");
        getLogger().info("Groups size: " + GroupManager.groups.size());
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }
}