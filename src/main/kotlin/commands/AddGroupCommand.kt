package net.guneyilmaz0.permissions.commands

import cn.nukkit.command.Command
import cn.nukkit.command.CommandSender
import cn.nukkit.command.data.CommandParamType
import cn.nukkit.command.data.CommandParameter
import net.guneyilmaz0.permissions.Utils.translate
import net.guneyilmaz0.permissions.enums.Process
import net.guneyilmaz0.permissions.group.GroupManager.addGroup

class AddGroupCommand : Command("addgroup", translate("commands.add_group.description")) {

    init {
        usage = translate("commands.add_group.usage")
        permission = "permissions.add_group"
        commandParameters.clear()
        commandParameters["default"] = arrayOf(CommandParameter.newType("group", CommandParamType.STRING))
    }

    override fun execute(commandSender: CommandSender, string: String, strings: Array<String>): Boolean {
        if (!testPermission(commandSender)) return false

        if (strings.size != 1) {
            commandSender.sendMessage(usage)
            return false
        }

        when (addGroup(strings[0])) {
            Process.SUCCESS -> commandSender.sendMessage(
                translate("commands.add_group.success", strings[0])
            )

            Process.ALREADY_EXISTS -> commandSender.sendMessage(
                translate("commands.add_group.already_exists", strings[0])
            )

            Process.INVALID_NAME -> commandSender.sendMessage(
                translate("commands.add_group.invalid_name", strings[0])
            )
            else -> commandSender.sendMessage(translate("commands.add_group.error"))
        }
        return true
    }

}