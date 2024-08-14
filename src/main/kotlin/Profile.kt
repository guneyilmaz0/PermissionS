package net.guneyilmaz0.permissions

import cn.nukkit.utils.Config
import com.google.gson.Gson

//TODO make & use better database
data class Profile(
    val name: String,
    var group: String,
    var permissions: List<String>,
    var time: Long
) {

    companion object {
        fun getProfile(name: String): Profile? {
            val config = Config(Main.playerConfigPath, Config.JSON)
            return Gson().fromJson(config.getString(name.lowercase()), Profile::class.java)
        }
    }

    override fun toString(): String = Gson().toJson(this)

    fun save() {
        val config = Config(Main.playerConfigPath, Config.JSON)
        config.set(name.lowercase(), this.toString())
        config.save()
    }
}
