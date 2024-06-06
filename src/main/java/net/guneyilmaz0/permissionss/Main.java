package net.guneyilmaz0.permissionss;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import lombok.Getter;
import net.guneyilmaz0.permissionss.listener.PlayerListener;
import net.guneyilmaz0.permissionss.task.ExpireDateCheckTask;
import net.guneyilmaz0.permissionss.commands.PermissionSCommands;
import net.guneyilmaz0.permissionss.group.GroupManager;
import net.guneyilmaz0.permissionss.permission.PermissionManager;

import java.util.ArrayList;

public class Main extends PluginBase {

    @Getter
    private static Main instance;
    @Getter
    private String playerConfigPath;

    @Override
    public void onLoad() {
        instance = this;
        playerConfigPath = getDataFolder().getPath() + "/players.json";
        saveDefaultConfig();
        saveResource("groups.yml");
    }

    @Override
    public void onEnable() {
        PermissionSCommands.init();
        GroupManager.load();
        getLogger().info("Groups Loaded");
        getLogger().info("Groups size: " + GroupManager.groups.size());
        PermissionManager.addPermsToOnlinePlayers();
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getScheduler().scheduleRepeatingTask(new ExpireDateCheckTask(), 72000);
    }

    @Override
    public void onDisable() {
        PermissionManager.removePermissions();
    }

    public void registerPlayer(String name) {
        Config config = new Config(playerConfigPath, 1);
        Profile profile = new Profile(name, GroupManager.getDefaultGroup().getId(), new ArrayList<>(), -1);
        config.set(name.toLowerCase(), profile.toString());
        config.save();
    }
}