package net.guneyilmaz0.permissions.events

import cn.nukkit.Player
import cn.nukkit.command.CommandSender
import cn.nukkit.event.HandlerList
import cn.nukkit.event.plugin.PluginEvent
import net.guneyilmaz0.permissions.Main
import net.guneyilmaz0.permissions.group.Group

@Suppress("unused")
class PlayerGroupChangeEvent(
    val player: Player,
    val newGroup: Group,
    val changer: CommandSender
) : PluginEvent(Main.instance) {

    companion object {
        val handlers = HandlerList()
    }
}
