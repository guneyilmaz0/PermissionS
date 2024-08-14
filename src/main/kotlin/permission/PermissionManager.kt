package net.guneyilmaz0.permissions.permission

import cn.nukkit.Player
import cn.nukkit.permission.PermissionAttachment
import net.guneyilmaz0.permissions.enums.Process
import net.guneyilmaz0.permissions.Main
import net.guneyilmaz0.permissions.Profile
import net.guneyilmaz0.permissions.group.Group
import net.guneyilmaz0.permissions.group.GroupManager
import java.util.*

object PermissionManager {
    val permissions: MutableMap<UUID, PermissionAttachment> = mutableMapOf()

    fun addPermsToOnlinePlayers() {
        Main.instance.server.onlinePlayers.values.forEach { player ->
            handlePermissions(player)
        }
    }

    fun handlePermissions(player: Player) {
        val attachment = player.addAttachment(Main.instance)
        val group: Group = GroupManager.getPlayerGroup(player)

        group.getAllPermissions().forEach { perm ->
            attachment.setPermission(perm, true)
        }

        val specialPermissions: List<String> = Profile.getProfile(player.name)?.permissions ?: emptyList()
        specialPermissions.forEach { specialPermission ->
            attachment.setPermission(specialPermission, true)
        }

        permissions[player.uniqueId] = attachment
    }

    @JvmStatic
    fun addPermission(name: String, permission: String): Process {
        val profile = Profile.getProfile(name.lowercase()) ?: return Process.NOT_FOUND
        val specialPermissions = profile.permissions.toMutableList()

        if (specialPermissions.contains(permission)) {
            return Process.ALREADY_EXISTS
        }

        specialPermissions.add(permission)
        profile.permissions = specialPermissions
        profile.save()
        reloadPermissions()
        return Process.SUCCESS
    }

    @JvmStatic
    fun removePermission(name: String, permission: String): Process {
        val profile = Profile.getProfile(name.lowercase()) ?: return Process.NOT_FOUND
        val specialPermissions = profile.permissions.toMutableList()

        if (!specialPermissions.contains(permission)) {
            return Process.NOT_FOUND
        }

        specialPermissions.remove(permission)
        profile.permissions = specialPermissions
        profile.save()
        reloadPermissions()
        return Process.SUCCESS
    }

    fun removePermissions() {
        Main.instance.server.onlinePlayers.values.forEach { player ->
            permissions[player.uniqueId]?.let { attachment ->
                player.removeAttachment(attachment)
            }
        }
    }

    fun reloadPermissions() {
        removePermissions()
        addPermsToOnlinePlayers()
    }
}
