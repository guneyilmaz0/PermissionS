package net.guneyilmaz0.permissions.events

import cn.nukkit.Player
import cn.nukkit.event.plugin.PluginEvent
import net.guneyilmaz0.permissions.Main

class PlayerRankExpiredEvent(val player: Player) : PluginEvent(Main.instance)
