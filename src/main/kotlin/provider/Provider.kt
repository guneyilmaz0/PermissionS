package net.guneyilmaz0.permissions.provider

import net.guneyilmaz0.permissions.PermissionsS
import net.guneyilmaz0.permissions.Profile
import java.util.UUID

abstract class Provider(protected val plugin: PermissionsS) {

    init {
        this.initialize()
    }

    abstract fun initialize()

    abstract fun getProfile(uuid: UUID) : Profile?

    fun registerPlayer(uuid: UUID, name: String) {
        saveProfile(Profile.create(uuid, name))
    }

    abstract fun isProfileExists(uuid: UUID) : Boolean

    abstract fun saveProfile(profile: Profile)

}