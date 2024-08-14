package net.guneyilmaz0.permissions.commands

import cn.nukkit.Server
import cn.nukkit.command.Command
import cn.nukkit.command.CommandMap
import commands.*

object PermissionSCommands {
    fun init() {
        val map: CommandMap = Server.getInstance().commandMap
        val commands: MutableList<Command> = ArrayList()
        commands.add(AddGroup())
        commands.add(RemoveGroup())
        commands.add(SetGroup())
        commands.add(ListGroups())
        commands.add(DefaultGroup())
        commands.add(PermissionsSInfo())
        commands.add(SetFormat())
        commands.add(SetUserPermission())
        commands.add(UnsetUserPermission())
        map.registerAll("permissionss", commands)
    }
}