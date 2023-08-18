package net.swade.permissionss.permission;

import cn.nukkit.Player;
import cn.nukkit.permission.PermissionAttachment;
import net.swade.permissionss.Main;
import net.swade.permissionss.group.Group;
import net.swade.permissionss.group.GroupManager;

import java.util.HashMap;
import java.util.UUID;

public class PermissionManager {
    public static HashMap<UUID, PermissionAttachment> permissions = new HashMap<>();

    public static void addPermsToOnlinePlayers() {
        for (Player player : Main.getInstance().getServer().getOnlinePlayers().values()) {
            if (player != null)
                handlePermissions(player);
        }
    }

    public static void handlePermissions(Player player){
        PermissionAttachment attachment = player.addAttachment(Main.getInstance());
        Group group = GroupManager.getPlayerGroup(player);
        for (String perm : group.getAllPermissions()) {
            attachment.setPermission(perm, true);
        }

        permissions.put(player.getUniqueId(), attachment);
    }

    public static void removePermissions() {
        for (Player player : Main.getInstance().getServer().getOnlinePlayers().values()) {
            PermissionAttachment attachment = permissions.get(player.getUniqueId());
            player.removeAttachment(attachment);
        }
    }

    public static void reloadPermissions(){
        removePermissions();
        addPermsToOnlinePlayers();
    }
}
