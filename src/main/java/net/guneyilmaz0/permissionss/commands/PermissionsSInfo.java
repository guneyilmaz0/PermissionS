package net.guneyilmaz0.permissionss.commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import net.guneyilmaz0.permissionss.Main;
import net.guneyilmaz0.permissionss.Utils;

public class PermissionsSInfo extends Command {
    public PermissionsSInfo() {
        super("permssinfo", Utils.translate("commands.permssinfo.description"));
        setPermission("permissionss.info");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!testPermission(commandSender)) return false;

        String author = Main.getInstance().getDescription().getAuthors().get(0);
        String version = Main.getInstance().getDescription().getVersion();

        if (commandSender instanceof ConsoleCommandSender)
            commandSender.sendMessage(Utils.translate("commands.permssinfo.console", version, author));
        else commandSender.sendMessage(Utils.translate("commands.permssinfo.player", version, author));

        return true;
    }
}
