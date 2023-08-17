package net.swade.permissionss.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.permission.PermissionAttachment;
import net.swade.permissionss.permission.PermissionManager;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        PermissionManager.handlePermissions(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        PermissionAttachment attachment = PermissionManager.permissions.get(event.getPlayer().getUniqueId());
        if(attachment != null) {
            try {
                event.getPlayer().removeAttachment(attachment);
            } catch (Throwable ignored) {

            }
        }
    }
}
