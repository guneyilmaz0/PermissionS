package net.guneyilmaz0.permissions.commands

import cn.nukkit.Server
import cn.nukkit.command.Command
import cn.nukkit.command.CommandSender
import cn.nukkit.command.data.CommandParamType
import cn.nukkit.command.data.CommandParameter
import cn.nukkit.utils.Config
import net.guneyilmaz0.permissions.PermissionsS
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

        val player = Server.getInstance().getOfflinePlayer(strings[0])
        if (player == null){
            commandSender.sendMessage(translate("commands.set_user_permission.player_not_found"))
            return false
        }

        if (!PermissionsS.provider.isProfileExists(player.uniqueId)) {
            PermissionsS.provider.registerPlayer(player.uniqueId, player.name)
        }

        val process = removePermission(player.uniqueId, strings[1].lowercase(Locale.getDefault()))
        when (process) {
            Process.SUCCESS -> commandSender.sendMessage(
                translate("commands.unset_user_permission.success", strings[1], player.name)
            )

            Process.NOT_FOUND -> commandSender.sendMessage(
                translate("commands.unset_user_permission.not_found", strings[1], player.name)
            )

            else -> commandSender.sendMessage(translate("commands.unset_user_permission.error"))
        }
        return true
    }
}