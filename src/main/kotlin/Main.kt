package net.guneyilmaz0.permissions

import cn.nukkit.plugin.PluginBase
import cn.nukkit.utils.Config
import net.guneyilmaz0.permissions.commands.*
import net.guneyilmaz0.permissions.group.GroupManager
import net.guneyilmaz0.permissions.listeners.PlayerListener
import net.guneyilmaz0.permissions.permission.PermissionManager
import net.guneyilmaz0.permissions.tasks.ExpireDateCheckTask

class Main : PluginBase() {

    companion object {
        lateinit var instance: Main
        lateinit var playerConfigPath: String
    }

    override fun onLoad() {
        instance = this
        playerConfigPath = "${dataFolder.path}/players.json"
        saveDefaultConfig()
        saveResource("groups.yml")
    }

    override fun onEnable() {
        registerCommands()
        GroupManager.load()
        logger.info("Groups Loaded")
        logger.info("Groups size: ${GroupManager.groups.size}")
        PermissionManager.addPermsToOnlinePlayers()
        server.pluginManager.registerEvents(PlayerListener(), this)
        server.scheduler.scheduleRepeatingTask(ExpireDateCheckTask(), 72000)
    }

    override fun onDisable() {
        PermissionManager.removePermissions()
    }

    fun registerPlayer(name: String) {
        val config = Config(playerConfigPath, Config.JSON)
        val profile = Profile(name, GroupManager.getDefaultGroup().id, mutableListOf(), -1)
        config.set(name.lowercase(), profile.toString())
        config.save()
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
