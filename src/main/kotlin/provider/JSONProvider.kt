package net.guneyilmaz0.permissions.provider

import cn.nukkit.utils.Config
import com.google.gson.Gson
import net.guneyilmaz0.permissions.PermissionsS
import net.guneyilmaz0.permissions.Profile
import java.util.*

class JSONProvider(plugin: PermissionsS) : Provider(plugin) {

    private lateinit var profiles: Config
    private lateinit var gson: Gson

    override fun initialize() {
        profiles = Config("${PermissionsS.instance.dataFolder.path}/profiles.json", 1)
        gson = Gson()
    }

    override fun getProfile(uuid: UUID): Profile? =
        if (profiles.exists(uuid.toString()))
            gson.fromJson(profiles.getString(uuid.toString()), Profile::class.java)
        else null

    override fun isProfileExists(uuid: UUID): Boolean = profiles.exists(uuid.toString())

    override fun saveProfile(profile: Profile) {
        profiles.set(profile.uuid.toString(), profile)
        profiles.save()
    }
}