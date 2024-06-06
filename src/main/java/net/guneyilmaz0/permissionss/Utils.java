package net.guneyilmaz0.permissionss;

public class Utils {
    public static boolean isInvalidGroupName(String groupName) {
        //TODO
        return false;
    }

    public static String translate(String string) {
        return Main.getInstance().getConfig().getString(string, string);
    }

    public static String translate(String string, String... strings) {
        String msg = Main.getInstance().getConfig().getString(string);
        if (msg != null) {
            int number = 0;
            for (String v : strings) {
                msg = msg.replace("%var" + number + "%", v);
                number++;
            }
            return msg;
        }
        return string;
    }
}