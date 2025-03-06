package net.guneyilmaz0.permissions

import net.guneyilmaz0.mongos.MongoSObject
import net.guneyilmaz0.permissions.group.GroupManager
import java.util.UUID

data class Profile(
    val uuid: UUID,
    val name: String,
    var group: String,
    var permissions: List<String> = emptyList(),
    var time: Long
) : MongoSObject() {
    override fun toString(): String = super.toString()

    companion object {
        fun create(uuid: UUID, name: String): Profile =
            Profile(uuid, name, GroupManager.getDefaultGroup().id, time = System.currentTimeMillis())
    }
}
