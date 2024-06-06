package net.guneyilmaz0.permissionss.listener;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.permission.PermissionAttachment;
import net.guneyilmaz0.permissionss.Utils;
import net.guneyilmaz0.permissionss.event.PlayerGroupChangeEvent;
import net.guneyilmaz0.permissionss.event.PlayerRankExpiredEvent;
import net.guneyilmaz0.permissionss.group.Group;
import net.guneyilmaz0.permissionss.group.GroupManager;
import net.guneyilmaz0.permissionss.permission.PermissionManager;

@SuppressWarnings("unused")
public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PermissionManager.handlePermissions(player);
        player.setNameTag(GroupManager.getPlayerGroup(player).getNameTagFormat().replaceAll("%nickname%", player.getName()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        PermissionAttachment attachment = PermissionManager.permissions.get(event.getPlayer().getUniqueId());
        if (attachment == null) return;
        try {
            event.getPlayer().removeAttachment(attachment);
        } catch (Throwable ignored) {}
    }


    @EventHandler
    public void onChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        Group group = GroupManager.getPlayerGroup(player);
        String chatFormat = group.getChatFormat();
        String message = chatFormat.replaceAll("%nickname%", player.getName()).replaceAll("%message%", event.getMessage());
        event.setFormat(message);
    }

    @EventHandler
    public void onGroupChange(PlayerGroupChangeEvent event) {
        Player player = event.getPlayer();
        player.setNameTag(event.getNewGroup().getNameTagFormat());
        player.sendMessage(Utils.translate("commands.set_group.player_message", event.getNewGroup().getName()));
    }

    @EventHandler
    public void onRankExpired(PlayerRankExpiredEvent event) {
        Player player = event.getPlayer();
        Group oldGroup = GroupManager.getPlayerGroup(player);
        Group group = GroupManager.getDefaultGroup();
        GroupManager.setPlayerGroup(player, group);
        PlayerGroupChangeEvent groupChangeEvent = new PlayerGroupChangeEvent(player, group, oldGroup, null);
        Server.getInstance().getPluginManager().callEvent(event);
    }
}
