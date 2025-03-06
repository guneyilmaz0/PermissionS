package net.guneyilmaz0.permissions.events

import cn.nukkit.Player
import cn.nukkit.event.plugin.PluginEvent
import net.guneyilmaz0.permissions.PermissionsS

class PlayerRankExpiredEvent(val player: Player) : PluginEvent(PermissionsS.instance)
