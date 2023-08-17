package net.swade.permissionss.group;

import cn.nukkit.Player;
import cn.nukkit.permission.PermissionAttachment;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import net.swade.permissionss.Main;

import java.util.*;

public class GroupManager {

    public static List<Group> groups = new ArrayList<>();

    public static void load() {
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
                    section.getString("inheritance", null));
            groups.add(group);
        });
    }

    public static Group getGroup(String string) {
        for (Group value : groups) {
            if (value.getName().equalsIgnoreCase(string) || value.getAliases().contains(string.toLowerCase()) || value.getId().equalsIgnoreCase(string) ) {
                return value;
            }
        }

        throw new NullPointerException("Group not found.");
    }

    public static Group getDefaultGroup(){
        Config config = Main.getInstance().getConfig();
        System.out.println(config.getString("defaultGroup")+ " default here");
        return getGroup(config.getString("defaultGroup"));
    }

    public static Group getPlayerGroup(UUID uuid){
        Config config = new Config(Main.getInstance().getDataFolder() + "/players.json", 1);
        if (config.exists(uuid.toString())){
            System.out.println(config.getString(uuid.toString())  + " uuid true");
            return getGroup(config.getString(uuid.toString()));
        }
        config.set(uuid.toString(), getDefaultGroup().getId());
        config.save();
        System.out.println(config.getString(uuid.toString())  + " uuid false");
        return getGroup(config.getString(uuid.toString()));
    }

}
