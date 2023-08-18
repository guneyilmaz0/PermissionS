package net.swade.permissionss;

import java.util.regex.Pattern;

public class Utils {
    public static boolean isValidGroupName(String groupName) {
        return true;
    }

    public static String translate(String string){
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
