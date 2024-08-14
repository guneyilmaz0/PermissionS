package net.guneyilmaz0.permissions

object Utils {
    @JvmStatic
    fun isInvalidGroupName(groupName: String): Boolean {
        //TODO: Implement validation logic
        return false
    }

    @JvmStatic
    fun translate(string: String): String {
        return Main.instance.config.getString(string, string)
    }

    @JvmStatic
    fun translate(string: String, vararg strings: String): String {
        var msg = Main.instance.config.getString(string) ?: return string

        strings.forEachIndexed { index, v ->
            msg = msg.replace("%var$index%", v)
        }

        return msg
    }
}
