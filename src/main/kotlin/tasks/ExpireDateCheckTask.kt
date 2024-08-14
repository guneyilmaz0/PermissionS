package net.guneyilmaz0.permissions.tasks

import cn.nukkit.Server
import cn.nukkit.scheduler.Task
import cn.nukkit.utils.Config
import net.guneyilmaz0.permissions.Main
import net.guneyilmaz0.permissions.Profile
import net.guneyilmaz0.permissions.events.PlayerRankExpiredEvent

class ExpireDateCheckTask : Task() {
    override fun onRun(currentTick: Int) {
        val config = Config(Main.playerConfigPath, Config.JSON)
        Server.getInstance().onlinePlayers.values.forEach { player ->
            val playerName = player.name.lowercase()
            if (!config.exists(playerName)) return@forEach

            val profile = Profile.getProfile(playerName) ?: return@forEach
            if (System.currentTimeMillis() < profile.time) return@forEach

            val event = PlayerRankExpiredEvent(player)
            Server.getInstance().pluginManager.callEvent(event)
        }
    }
}
