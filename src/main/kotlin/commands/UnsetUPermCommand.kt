package net.guneyilmaz0.permissions.commands

import cn.nukkit.command.Command
import cn.nukkit.command.CommandSender
import cn.nukkit.command.data.CommandParamType
import cn.nukkit.command.data.CommandParameter
import cn.nukkit.utils.Config
import net.guneyilmaz0.permissions.Main
import net.guneyilmaz0.permissions.Utils.translate
import net.guneyilmaz0.permissions.enums.Process
import net.guneyilmaz0.permissions.permission.PermissionManager.removePermission
import java.util.*

class UnsetUPermCommand : Command("unset", translate("commands.unset_user_permission.description")) {

    init {
        usage = translate("commands.unset_user_permission.usage")
        permission = "permissionss.unset"
        commandParameters.clear()
        commandParameters["default"] =
            arrayOf(
                CommandParameter.newType("player", CommandParamType.TARGET),
                CommandParameter.newType("permission", CommandParamType.STRING)
            )
    }

    override fun execute(commandSender: CommandSender, string: String, strings: Array<String>): Boolean {
        if (!testPermission(commandSender)) return false

        if (strings.size != 2) {
            commandSender.sendMessage(usage)
            return false
        }

        if (!Config(Main.playerConfigPath, 1).exists(strings[0].lowercase(Locale.getDefault())))
            Main.instance.registerPlayer(strings[0])

        val process = removePermission(strings[0], strings[1].lowercase(Locale.getDefault()))
        when (process) {
            Process.SUCCESS -> commandSender.sendMessage(
                translate("commands.unset_user_permission.success", strings[1], strings[0])
            )

            Process.NOT_FOUND -> commandSender.sendMessage(
                translate("commands.unset_user_permission.not_found", strings[1], strings[0])
            )

            else -> commandSender.sendMessage(translate("commands.unset_user_permission.error"))
        }
        return true
    }
}