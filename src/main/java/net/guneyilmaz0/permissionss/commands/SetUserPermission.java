package net.guneyilmaz0.permissionss.commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.Config;
import net.guneyilmaz0.permissionss.Main;
import net.guneyilmaz0.permissionss.Utils;
import net.guneyilmaz0.permissionss.enums.Process;
import net.guneyilmaz0.permissionss.permission.PermissionManager;

public class SetUserPermission extends Command {
    public SetUserPermission() {
        super("setuperm", Utils.translate("commands.set_user_permission.description"));
        setUsage(Utils.translate("commands.set_user_permission.usage"));
        setPermission("permissionss.setuperm");
        commandParameters.clear();
        commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newType("player", CommandParamType.TARGET),
                CommandParameter.newType("permission", CommandParamType.STRING)
        });
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!testPermission(commandSender)) return false;

        if (strings.length != 2) {
            commandSender.sendMessage(getUsage());
            return false;
        }

        Config config = new Config(Main.getInstance().getPlayerConfigPath(), 1);
        if (!config.exists(strings[0].toLowerCase())) Main.getInstance().registerPlayer(strings[0]);

        Process process = PermissionManager.addPermission(strings[0], strings[1].toLowerCase());
        switch (process) {
            case SUCCESS:
                commandSender.sendMessage(Utils.translate("commands.set_user_permission.success", strings[1], strings[0]));
                break;
            case ALREADY_EXISTS:
                commandSender.sendMessage(Utils.translate("commands.set_user_permission.already_exists", strings[1], strings[0]));
        }
        return true;
    }
}
