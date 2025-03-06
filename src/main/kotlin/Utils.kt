package net.guneyilmaz0.permissions

object Utils {
    @JvmStatic
    fun isInvalidGroupName(groupName: String): Boolean = !"^[a-zA-Z0-9_]+$".toRegex().matches(groupName)

    @JvmStatic
    fun translate(string: String): String = PermissionsS.instance.config.getString(string, string)

    @JvmStatic
    fun translate(string: String, vararg strings: String): String {
        var msg = PermissionsS.instance.config.getString(string) ?: return string

        strings.forEachIndexed { index, v ->
            msg = msg.replace("%var$index%", v)
        }

        return msg
    }
}
