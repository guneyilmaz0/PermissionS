package net.guneyilmaz0.permissions.permission

import cn.nukkit.Player
import cn.nukkit.Server
import cn.nukkit.permission.PermissionAttachment
import net.guneyilmaz0.permissions.enums.Process
import net.guneyilmaz0.permissions.PermissionsS
import net.guneyilmaz0.permissions.group.Group
import net.guneyilmaz0.permissions.group.GroupManager
import java.util.*

object PermissionManager {
    val attachments: HashMap<UUID, PermissionAttachment> = HashMap()

    fun addPermsToOnlinePlayers() {
        for (value in PermissionsS.instance.server.onlinePlayers.values) handlePermissions(value)
    }

    fun handlePermissions(player: Player) {
        if (attachments.containsKey(player.uniqueId)) player.removeAttachment(attachments[player.uniqueId]!!)

        val attachment = player.addAttachment(PermissionsS.instance)
        val group: Group = GroupManager.getPlayerGroup(player)

        group.getAllPermissions().forEach { perm ->
            attachment.setPermission(perm, true)
        }

        if (!PermissionsS.provider.isProfileExists(player.uniqueId)) return

        val specialPermissions: List<String> = PermissionsS.provider.getProfile(player.uniqueId)?.permissions ?: emptyList()
        specialPermissions.forEach { specialPermission ->
            attachment.setPermission(specialPermission, true)
        }

        attachments[player.uniqueId] = attachment
    }

    @JvmStatic
    fun addPermission(uuid: UUID, permission: String): Process {
        if (!PermissionsS.provider.isProfileExists(uuid)) return Process.NOT_FOUND
        val profile = PermissionsS.provider.getProfile(uuid)!!
        val specialPermissions = profile.permissions.toMutableList()

        if (specialPermissions.contains(permission)) return Process.ALREADY_EXISTS

        specialPermissions.add(permission)
        profile.permissions = specialPermissions
        PermissionsS.provider.saveProfile(profile)
        val player = Server.getInstance().getOfflinePlayer(uuid)
        if (player.isOnline) handlePermissions(player as Player)
        return Process.SUCCESS
    }

    @JvmStatic
    fun removePermission(uuid: UUID, permission: String): Process {
        if (!PermissionsS.provider.isProfileExists(uuid)) return Process.NOT_FOUND
        val profile = PermissionsS.provider.getProfile(uuid)!!
        val specialPermissions = profile.permissions.toMutableList()

        if (!specialPermissions.contains(permission)) return Process.NOT_FOUND

        specialPermissions.remove(permission)
        profile.permissions = specialPermissions
        PermissionsS.provider.saveProfile(profile)
        val player = Server.getInstance().getOfflinePlayer(uuid)
        if (player.isOnline) handlePermissions(player as Player)
        return Process.SUCCESS
    }
}
