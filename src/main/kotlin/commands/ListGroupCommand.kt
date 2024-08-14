package net.guneyilmaz0.permissions.commands

import cn.nukkit.command.Command
import cn.nukkit.command.CommandSender
import net.guneyilmaz0.permissions.Utils.translate
import net.guneyilmaz0.permissions.group.GroupManager

class ListGroupCommand : Command("listgroups", translate("commands.groups.description")) {

    init {
        aliases = arrayOf("groups")
        permission = "permissions.list_groups"
    }

    override fun execute(commandSender: CommandSender, string: String, strings: Array<String>): Boolean {
        if (!testPermission(commandSender)) return false

        val groups = StringBuilder()
        for ((name) in GroupManager.groups) groups.append(name).append(", ")

        groups.deleteCharAt(groups.length - 2)
        commandSender.sendMessage(translate("commands.groups.all_registered_groups", groups.toString()))
        return true
    }
}