package net.guneyilmaz0.permissionss.permission;

import cn.nukkit.Player;
import cn.nukkit.permission.PermissionAttachment;
import net.guneyilmaz0.permissionss.Main;
import net.guneyilmaz0.permissionss.Profile;
import net.guneyilmaz0.permissionss.enums.Process;
import net.guneyilmaz0.permissionss.group.Group;
import net.guneyilmaz0.permissionss.group.GroupManager;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PermissionManager {
    public static HashMap<UUID, PermissionAttachment> permissions = new HashMap<>();

    public static void addPermsToOnlinePlayers() {
        for (Player player : Main.getInstance().getServer().getOnlinePlayers().values())
            if (player != null)
                handlePermissions(player);
    }

    public static void handlePermissions(Player player) {
        PermissionAttachment attachment = player.addAttachment(Main.getInstance());
        Group group = GroupManager.getPlayerGroup(player);
        for (String perm : group.getAllPermissions()) attachment.setPermission(perm, true);

        List<String> specialPermissions = Profile.getProfile(player.getName()).getPermissions();
        for (String specialPermission : specialPermissions) attachment.setPermission(specialPermission, true);

        permissions.put(player.getUniqueId(), attachment);
    }

    public static Process addPermission(String name, String permission) {
        Profile profile = Profile.getProfile(name.toLowerCase());
        List<String> specialPermissions = profile.getPermissions();
        if (specialPermissions.contains(permission)) return Process.ALREADY_EXISTS;

        specialPermissions.add(permission);
        profile.setPermissions(specialPermissions);
        Profile.save(name, profile);
        reloadPermissions();
        return Process.SUCCESS;
    }

    public static Process removePermission(String name, String permission) {
        Profile profile = Profile.getProfile(name.toLowerCase());
        List<String> specialPermissions = profile.getPermissions();
        if (!specialPermissions.contains(permission)) return Process.NOT_FOUND;

        specialPermissions.remove(permission);
        profile.setPermissions(specialPermissions);
        Profile.save(name, profile);
        reloadPermissions();
        return Process.SUCCESS;
    }

    public static void removePermissions() {
        for (Player player : Main.getInstance().getServer().getOnlinePlayers().values()) {
            PermissionAttachment attachment = permissions.get(player.getUniqueId());
            player.removeAttachment(attachment);
        }
    }

    public static void reloadPermissions() {
        removePermissions();
        addPermsToOnlinePlayers();
    }
}
