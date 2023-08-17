package net.swade.permissionss;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

public class TestCommand extends Command {
    public TestCommand() {
        super("testt");
        setPermission("test.perm");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (commandSender.hasPermission(getPermission())){
            commandSender.sendMessage("perm var");
        }else {
            commandSender.sendMessage("perm yok");
        }
        return false;
    }
}
