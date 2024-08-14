package net.guneyilmaz0.permissions.commands

import cn.nukkit.command.Command
import cn.nukkit.command.CommandSender
import cn.nukkit.command.data.CommandParamType
import cn.nukkit.command.data.CommandParameter
import net.guneyilmaz0.permissions.Utils.translate
import net.guneyilmaz0.permissions.group.GroupManager.getGroup
import net.guneyilmaz0.permissions.group.GroupManager.setChatFormat

class SetFormatCommand : Command("setformat", translate("commands.set_format.description")) {

    init {
        usage = translate("commands.set_format.usage")
        permission = "permissionss.setformat"
        commandParameters["default"] =
            arrayOf(
                CommandParameter.newType("group", CommandParamType.STRING),
                CommandParameter.newType("format", CommandParamType.TEXT)
            )
    }

    override fun execute(commandSender: CommandSender, string: String, strings: Array<String>): Boolean {
        if (!testPermission(commandSender)) return false

        if (strings.size < 2) {
            commandSender.sendMessage(usage)
            return false
        }

        val group = getGroup(strings[0])
        if (group == null) {
            commandSender.sendMessage(translate("commands.set_format.not_found", strings[0]))
            return false
        }

        var chatFormat = StringBuilder()

        for (i in 1 until strings.size) chatFormat.append(strings[i]).append(" ")


        if (chatFormat.isNotEmpty()) chatFormat = StringBuilder(chatFormat.substring(0, chatFormat.length - 1))

        setChatFormat(group, chatFormat.toString())
        commandSender.sendMessage(translate("commands.set_format.success", strings[0], chatFormat.toString()))
        return true
    }
}