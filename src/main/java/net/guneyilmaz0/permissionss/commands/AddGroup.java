package net.guneyilmaz0.permissionss.commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import net.guneyilmaz0.permissionss.Utils;
import net.guneyilmaz0.permissionss.enums.Process;
import net.guneyilmaz0.permissionss.group.GroupManager;

public class AddGroup extends Command {
    public AddGroup() {
        super("addgroup", Utils.translate("commands.add_group.description"));
        setUsage(Utils.translate("commands.add_group.usage"));
        setPermission("permissionss.addgroup");
        commandParameters.clear();
        commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newType("group", CommandParamType.STRING)
        });
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!testPermission(commandSender)) return false;

        if (strings.length != 1) {
            commandSender.sendMessage(getUsage());
            return false;
        }

        Process result = GroupManager.addGroup(strings[0]);
        switch (result) {
            case SUCCESS:
                commandSender.sendMessage(Utils.translate("commands.add_group.success", strings[0]));
                break;
            case ALREADY_EXISTS:
                commandSender.sendMessage(Utils.translate("commands.add_group.already_exists", strings[0]));
                break;
            case INVALID_NAME:
                commandSender.sendMessage(Utils.translate("commands.add_group.invalid_name", strings[0]));
                break;
        }
        return true;
    }
}
