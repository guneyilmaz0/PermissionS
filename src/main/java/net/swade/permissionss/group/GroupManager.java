package net.swade.permissionss.group;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import net.swade.permissionss.Main;
import net.swade.permissionss.Utils;
import net.swade.permissionss.enums.Process;
import net.swade.permissionss.permission.PermissionManager;

import java.util.*;

public class GroupManager {

    public static List<Group> groups;

    public static void load() {
        groups = new ArrayList<>();
        Config config = new Config(Main.getInstance().getDataFolder() + "/groups.yml", 2);
        Map<String, Object> map = config.getAll();
        map.forEach((string, object) -> {
            ConfigSection section = config.getSection(string);
            Group group = new Group(section.getString("name"),
                    section.getString("id"),
                    section.getString("nameTagFormat"),
                    section.getString("chatFormat"),
                    section.getStringList("aliases"),
                    section.getStringList("permissions"),
                    section.getStringList("inheritance"));
            groups.add(group);
        });
    }

    public static Group getGroup(String string) {
        for (Group value : groups) {
            if (value.getName().equalsIgnoreCase(string) || value.getAliases().contains(string.toLowerCase()) || value.getId().equalsIgnoreCase(string) ) {
                return value;
            }
        }

        return null;
    }

    public static Group getDefaultGroup(){
        Config config = Main.getInstance().getConfig();
        return getGroup(config.getString("defaultGroup"));
    }

    public static Group getPlayerGroup(Player player){
        return getPlayerGroup(player.getName());
    }

    public static Group getPlayerGroup(String name){
        Config config = new Config(Main.getInstance().getDataFolder() + "/players.json", 1);
        if (config.exists(name.toLowerCase())){
            return getGroup(config.getString(name.toLowerCase()));
        }
        setPlayerGroup(name.toLowerCase(), getDefaultGroup());
        return getDefaultGroup();
    }

    public static void setPlayerGroup(Player player, Group group){
        setPlayerGroup(player.getName(), group);
    }

    public static void setPlayerGroup(String name, Group group){
        Config config = new Config(Main.getInstance().getDataFolder() + "/players.json", 1);
        config.set(name.toLowerCase(), group.getId());
        config.save();
        PermissionManager.reloadPermissions();
    }

    public static Process addGroup(String groupName){
        if (!Utils.isValidGroupName(groupName)){
            return Process.INVALID_NAME;
        }else if (getGroup(groupName) != null){
            return Process.ALREADY_EXISTS;
        }

        Config config = new Config(Main.getInstance().getDataFolder() + "/groups.yml", 2);
        config.set(groupName +".name", groupName);
        config.set(groupName +".id", groupName.toLowerCase());
        config.set(groupName +".nameTagFormat", "§7[§a"+ groupName +"§7] %nickname%");
        config.set(groupName +".chatFormat", "§7[§a"+ groupName +"§7] %nickname% §e» §f%message%");
        config.set(groupName +".aliases", new ArrayList<>());
        config.set(groupName +".permissions", new ArrayList<>());
        config.set(groupName +".inheritance", new ArrayList<>());
        config.save();
        load();
        return Process.SUCCESS;
    }

    public static Process removeGroup(String groupName){
        if (!Utils.isValidGroupName(groupName)){
            return Process.INVALID_NAME;
        }else if (getGroup(groupName) == null){
            return Process.NOT_FOUND;
        }
        Group group = getGroup(groupName);
        Config config = new Config(Main.getInstance().getDataFolder() + "/groups.yml", 2);
        config.remove(group.getName());
        config.save();
        load();
        return Process.SUCCESS;
    }
}
