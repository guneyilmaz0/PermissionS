package net.guneyilmaz0.permissions.commands

import cn.nukkit.Server
import cn.nukkit.command.Command
import cn.nukkit.command.CommandSender
import cn.nukkit.command.data.CommandParamType
import cn.nukkit.command.data.CommandParameter
import net.guneyilmaz0.permissions.Utils.translate
import net.guneyilmaz0.permissions.events.PlayerGroupChangeEvent
import net.guneyilmaz0.permissions.group.GroupManager.getGroup
import net.guneyilmaz0.permissions.group.GroupManager.setPlayerGroup

class SetGroupCommand : Command("setgroup", translate("commands.set_group.description")) {

    init {
        usage = translate("commands.set_group.usage")
        permission = "permissionss.setgroup"
        commandParameters.clear()
        commandParameters["default"] =
            arrayOf(
                CommandParameter.newType("player", CommandParamType.TARGET),
                CommandParameter.newType("group", CommandParamType.STRING)
            )
    }

    override fun execute(commandSender: CommandSender, string: String, strings: Array<String>): Boolean {
        if (!testPermission(commandSender)) return false

        if (strings.size != 2) {
            commandSender.sendMessage(usage)
            return false
        }

        val group = getGroup(strings[1])
        if (group == null) {
            commandSender.sendMessage(translate("commands.set_group.not_found", strings[1]))
            return false
        }

        var name = strings[0]
        val player = Server.getInstance().getPlayer(strings[0])
        if (player != null) {
            val event = PlayerGroupChangeEvent(player, group, commandSender)
            Server.getInstance().pluginManager.callEvent(event)
            name = player.name
        }

        setPlayerGroup(name, group)
        commandSender.sendMessage(translate("commands.set_group.success", name, group.name))
        return true
    }
}