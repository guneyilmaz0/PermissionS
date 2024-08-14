package net.guneyilmaz0.permissions.group

import cn.nukkit.Player
import cn.nukkit.utils.Config
import cn.nukkit.utils.ConfigSection
import net.guneyilmaz0.permissions.Main
import net.guneyilmaz0.permissions.Profile
import net.guneyilmaz0.permissions.Utils
import net.guneyilmaz0.permissions.enums.Process
import net.guneyilmaz0.permissions.permission.PermissionManager

object GroupManager {

    var groups: MutableList<Group> = mutableListOf()

    fun load() {
        groups.clear()
        val config = Config("${Main.instance.dataFolder}/groups.yml", Config.YAML)
        val map: Map<String, Any> = config.all
        map.forEach { (string, _) ->
            val section: ConfigSection = config.getSection(string)
            val group = Group(
                section.getString("name")!!,
                section.getString("id")!!,
                section.getString("nameTagFormat")!!,
                section.getString("chatFormat")!!,
                section.getStringList("aliases"),
                section.getStringList("permissions"),
                section.getStringList("inheritance")
            )
            groups.add(group)
        }
    }

    fun getGroup(name: String): Group? {
        return groups.find {
            it.name.equals(name, ignoreCase = true) ||
                    it.aliases.contains(name.lowercase()) ||
                    it.id.equals(name, ignoreCase = true)
        }
    }

    fun getGroupIds(): List<String> = groups.map { it.id }


    fun getDefaultGroup(): Group {
        val config = Main.instance.config
        return getGroup(config.getString("defaultGroup")) ?: run {
            val group = groups.first()
            setDefaultGroup(group)
            group
        }
    }

    fun setDefaultGroup(group: Group) {
        val config = Main.instance.config
        config.set("defaultGroup", group.id)
        config.save()
    }

    fun getPlayerGroup(player: Player): Group {
        return getPlayerGroup(player.name)
    }

    private fun getPlayerGroup(name: String): Group {
        val profile = Profile.getProfile(name)
        return profile?.let {
            getGroup(it.group)
        } ?: run {
            Main.instance.registerPlayer(name)
            getDefaultGroup()
        }
    }

    fun setPlayerGroup(player: Player, group: Group) {
        setPlayerGroup(player.name, group)
    }

    fun setPlayerGroup(name: String, group: Group) {
        val profile = Profile.getProfile(name)
        profile?.let {
            it.group = group.id
            it.save()
        }
        PermissionManager.reloadPermissions()
    }

    fun addGroup(groupName: String): Process {
        return when {
            Utils.isInvalidGroupName(groupName) -> Process.INVALID_NAME
            getGroup(groupName) != null -> Process.ALREADY_EXISTS
            else -> {
                val config = Config("${Main.instance.dataFolder}/groups.yml", Config.YAML)
                config.set("$groupName.name", groupName)
                config.set("$groupName.id", groupName.lowercase())
                config.set("$groupName.nameTagFormat", "§7[§a$groupName§7] %nickname%")
                config.set("$groupName.chatFormat", "§7[§a$groupName§7] %nickname% §e» §f%message%")
                config.set("$groupName.aliases", mutableListOf<String>())
                config.set("$groupName.permissions", mutableListOf<String>())
                config.set("$groupName.inheritance", mutableListOf<String>())
                config.save()
                load()
                Process.SUCCESS
            }
        }
    }

    fun removeGroup(groupName: String): Process {
        return when {
            Utils.isInvalidGroupName(groupName) -> Process.INVALID_NAME
            getGroup(groupName) == null -> Process.NOT_FOUND
            else -> {
                val group = getGroup(groupName) ?: return Process.NOT_FOUND
                val config = Config("${Main.instance.dataFolder}/groups.yml", Config.YAML)
                config.remove(group.name)
                config.save()
                load()
                Process.SUCCESS
            }
        }
    }

    fun setChatFormat(group: Group, format: String) {
        val config = Config("${Main.instance.dataFolder}/groups.yml", Config.YAML)
        config.set("${group.id}.chatFormat", format)
        config.save()
        load()
    }
}
