package net.guneyilmaz0.permissions.tasks

import cn.nukkit.Server
import cn.nukkit.scheduler.Task
import cn.nukkit.utils.Config
import net.guneyilmaz0.permissions.PermissionsS
import net.guneyilmaz0.permissions.Profile
import net.guneyilmaz0.permissions.events.PlayerRankExpiredEvent

class ExpireDateCheckTask : Task() {
    override fun onRun(currentTick: Int) {
        Server.getInstance().onlinePlayers.values.forEach { player ->
            if (!PermissionsS.provider.isProfileExists(player.uniqueId)) return@forEach

            val profile = PermissionsS.provider.getProfile(player.uniqueId) ?: return@forEach
            if (System.currentTimeMillis() < profile.time) return@forEach

            val event = PlayerRankExpiredEvent(player)
            Server.getInstance().pluginManager.callEvent(event)
        }
    }
}
