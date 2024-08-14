package net.guneyilmaz0.permissions.listeners

import cn.nukkit.Server
import cn.nukkit.event.EventHandler
import cn.nukkit.event.Listener
import cn.nukkit.event.player.PlayerChatEvent
import cn.nukkit.event.player.PlayerJoinEvent
import cn.nukkit.event.player.PlayerQuitEvent
import net.guneyilmaz0.permissions.Utils
import net.guneyilmaz0.permissions.events.PlayerGroupChangeEvent
import net.guneyilmaz0.permissions.events.PlayerRankExpiredEvent
import net.guneyilmaz0.permissions.group.GroupManager
import net.guneyilmaz0.permissions.permission.PermissionManager

@Suppress("unused")
class PlayerListener : Listener {

    @EventHandler
    private fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        PermissionManager.handlePermissions(player)
        player.nameTag = GroupManager.getPlayerGroup(player)?.nameTagFormat?.replace("%nickname%", player.name) ?: player.name
    }

    @EventHandler
    private fun onQuit(event: PlayerQuitEvent) {
        val attachment = PermissionManager.permissions[event.player.uniqueId] ?: return
        event.player.removeAttachment(attachment)
    }

    @EventHandler
    private fun onChat(event: PlayerChatEvent) {
        val player = event.player
        val group = GroupManager.getPlayerGroup(player) ?: return
        val format = group.chatFormat
        val message = format.replace("%nickname%", player.name).replace("%message%", event.message)
        event.format = message
    }

    @EventHandler
    private fun onGroupChange(event: PlayerGroupChangeEvent) {
        val player = event.player
        player.nameTag = event.newGroup.nameTagFormat.replace("%nickname%", player.name)
        player.sendMessage(Utils.translate("commands.set_group.player_message", event.newGroup.name))
    }

    @EventHandler
    private fun onRankExpired(event: PlayerRankExpiredEvent) {
        val player = event.player
        val group = GroupManager.getDefaultGroup()
        GroupManager.setPlayerGroup(player, group)
        val groupChangeEvent = PlayerGroupChangeEvent(player, group, player)
        Server.getInstance().pluginManager.callEvent(groupChangeEvent)
    }
}