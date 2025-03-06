package net.guneyilmaz0.permissions.provider

import net.guneyilmaz0.mongos.MongoS
import net.guneyilmaz0.permissions.PermissionsS
import net.guneyilmaz0.permissions.Profile
import java.util.*

class MongoProvider(plugin: PermissionsS) : Provider(plugin) {

    private lateinit var database: MongoS

    override fun initialize() {
        database = MongoS(plugin.config.getString("mongo.uri"), plugin.config.getString("mongo.database"))
        database.set("profiles", "test", "sa")
    }

    override fun getProfile(uuid: UUID): Profile = database.getObject("profiles", "$uuid", Profile::class.java)

    override fun isProfileExists(uuid: UUID): Boolean = database.exists("profiles", "$uuid")

    override fun saveProfile(profile: Profile) = database.set("profiles", "${profile.uuid}", profile)
}