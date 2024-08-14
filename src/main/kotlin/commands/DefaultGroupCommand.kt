package net.guneyilmaz0.permissions.commands

import cn.nukkit.command.Command
import cn.nukkit.command.CommandSender
import cn.nukkit.command.data.CommandParamType
import cn.nukkit.command.data.CommandParameter
import net.guneyilmaz0.permissions.Utils.translate
import net.guneyilmaz0.permissions.group.GroupManager.getGroup
import net.guneyilmaz0.permissions.group.GroupManager.setDefaultGroup

class DefaultGroupCommand : Command("defaultgroup", translate("commands.default_group.description")) {

    init {
        usage = translate("commands.default_group.usage")
        permission = "permissions.default_group"
        commandParameters.clear()
        commandParameters["default"] = arrayOf(CommandParameter.newType("group", CommandParamType.STRING))
    }

    override fun execute(commandSender: CommandSender, string: String, strings: Array<String>): Boolean {
        if (!testPermission(commandSender)) return false

        if (strings.size != 1) {
            commandSender.sendMessage(usage)
            return false
        }

        val group = getGroup(strings[0])
        if (group == null) {
            commandSender.sendMessage(translate("commands.default_group.not_found", strings[0]))
            return false
        }

        setDefaultGroup(group)
        commandSender.sendMessage(translate("commands.default_group.success", group.name))
        return true
    }
}