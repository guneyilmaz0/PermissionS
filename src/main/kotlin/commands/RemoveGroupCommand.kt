package net.guneyilmaz0.permissions.commands

import cn.nukkit.command.Command
import cn.nukkit.command.CommandSender
import cn.nukkit.command.data.CommandParamType
import cn.nukkit.command.data.CommandParameter
import net.guneyilmaz0.permissions.Utils.translate
import net.guneyilmaz0.permissions.enums.Process
import net.guneyilmaz0.permissions.group.GroupManager.removeGroup

class RemoveGroupCommand : Command("removegroup", translate("commands.remove_group.description")) {

    init {
        usage = translate("commands.remove_group.usage")
        permission = "permissionss.remove_group"
        commandParameters.clear()
        commandParameters["default"] = arrayOf(CommandParameter.newType("group", CommandParamType.STRING))
    }

    override fun execute(commandSender: CommandSender, string: String, strings: Array<String>): Boolean {
        if (!testPermission(commandSender)) return false

        if (strings.size != 1) {
            commandSender.sendMessage(usage)
            return false
        }

        when (removeGroup(strings[0])) {
            Process.SUCCESS -> commandSender.sendMessage(
                translate("commands.remove_group.success", strings[0])
            )

            Process.NOT_FOUND -> commandSender.sendMessage(
                translate("commands.remove_group.not_found", strings[0])
            )

            Process.INVALID_NAME -> commandSender.sendMessage(
                translate("commands.remove_group.invalid_name", strings[0])
            )
            else -> commandSender.sendMessage(translate("commands.remove_group.error"))
        }
        return true
    }
}