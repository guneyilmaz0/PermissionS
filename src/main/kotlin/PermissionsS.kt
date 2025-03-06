package net.guneyilmaz0.permissions

import cn.nukkit.plugin.PluginBase
import net.guneyilmaz0.permissions.commands.*
import net.guneyilmaz0.permissions.group.GroupManager
import net.guneyilmaz0.permissions.listeners.PlayerListener
import net.guneyilmaz0.permissions.permission.PermissionManager
import net.guneyilmaz0.permissions.provider.*
import net.guneyilmaz0.permissions.tasks.CheckUpdateTask
import net.guneyilmaz0.permissions.tasks.ExpireDateCheckTask

class PermissionsS : PluginBase() {

    companion object {
        lateinit var instance: PermissionsS
        lateinit var provider: Provider
    }

    override fun onLoad() {
        instance = this
        saveDefaultConfig()
        saveResource("groups.yml")
        provider = when (config.getString("provider", "json")) {
            "mongo" -> MongoProvider(this)
            else -> JSONProvider(this)
        }
        provider.initialize()
    }

    override fun onEnable() {
        registerCommands()
        GroupManager.load()
        logger.info("Groups Loaded")
        logger.info("Groups size: ${GroupManager.groups.size}")
        PermissionManager.addPermsToOnlinePlayers()
        server.pluginManager.registerEvents(PlayerListener(), this)
        server.scheduler.scheduleRepeatingTask(ExpireDateCheckTask(), 72000)
        server.scheduler.scheduleAsyncTask(this, CheckUpdateTask())
    }

    private fun registerCommands() {
        val map = mapOf(
            "addgroup" to AddGroupCommand(),
            "defaultgroup" to DefaultGroupCommand(),
            "info" to InfoCommand(),
            "listgroup" to ListGroupCommand(),
            "removegroup" to RemoveGroupCommand(),
            "setformat" to SetFormatCommand(),
            "setgroup" to SetGroupCommand(),
            "setuperm" to SetUPermCommand(),
            "unsetuperm" to UnsetUPermCommand()
        )

        for ((name, command) in map) server.commandMap.register(name, command)
    }
}
