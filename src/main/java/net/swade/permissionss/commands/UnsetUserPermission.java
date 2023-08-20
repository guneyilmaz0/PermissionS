package net.swade.permissionss.commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.Config;
import net.swade.permissionss.Main;
import net.swade.permissionss.Utils;
import net.swade.permissionss.enums.Process;
import net.swade.permissionss.permission.PermissionManager;

public class UnsetUserPermission extends Command {
    public UnsetUserPermission() {
        super("unset", Utils.translate("commands.unset_user_permission.description"));
        setUsage(Utils.translate("commands.unset_user_permission.usage"));
        setPermission("permissionss.unset");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!testPermission(commandSender)){
            return false;
        }

        if (strings.length != 2){
            commandSender.sendMessage(getUsage());
            return false;
        }

        Config config = new Config(Main.getInstance().getPlayerConfigPath(), 1);
        if (!config.exists(strings[0].toLowerCase())){
            Main.getInstance().registerPlayer(strings[0]);
        }

        Process process =  PermissionManager.removePermission(strings[0], strings[1].toLowerCase());
        switch (process){
            case SUCCESS:
                commandSender.sendMessage(Utils.translate("commands.unset_user_permission.success", strings[1], strings[0]));
                break;
            case NOT_FOUND:
                commandSender.sendMessage(Utils.translate("commands.unset_user_permission.not_found", strings[1], strings[0]));
        }
        return true;
    }
}
