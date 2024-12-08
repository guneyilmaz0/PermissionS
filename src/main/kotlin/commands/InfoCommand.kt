package net.guneyilmaz0.permissions.commands

import cn.nukkit.command.Command
import cn.nukkit.command.CommandSender
import cn.nukkit.command.ConsoleCommandSender
import net.guneyilmaz0.permissions.Main
import net.guneyilmaz0.permissions.Utils.translate

class InfoCommand : Command("permsinfo", translate("commands.permsinfo.description")) {

    init {
        permission = "permissionss.info"
    }

    override fun execute(commandSender: CommandSender, string: String, strings: Array<String>): Boolean {
        if (!testPermission(commandSender)) return false

        val author = Main.instance.description.authors[0]
        val version = Main.instance.description.version

        if (commandSender is ConsoleCommandSender) commandSender.sendMessage(
            translate("commands.permsinfo.console", version, author)
        )
        else commandSender.sendMessage(translate("commands.permsinfo.player", version, author))

        return true
    }
}