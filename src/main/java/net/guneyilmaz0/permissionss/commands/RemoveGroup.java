package net.guneyilmaz0.permissionss.commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import net.guneyilmaz0.permissionss.Utils;
import net.guneyilmaz0.permissionss.enums.Process;
import net.guneyilmaz0.permissionss.group.GroupManager;

public class RemoveGroup extends Command {
    public RemoveGroup() {
        super("removegroup", Utils.translate("commands.remove_group.description"));
        setUsage(Utils.translate("commands.remove_group.usage"));
        setPermission("permissionss.removegroup");
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

        Process result = GroupManager.removeGroup(strings[0]);
        switch (result) {
            case SUCCESS:
                commandSender.sendMessage(Utils.translate("commands.remove_group.success", strings[0]));
                break;
            case NOT_FOUND:
                commandSender.sendMessage(Utils.translate("commands.remove_group.not_found", strings[0]));
                break;
            case INVALID_NAME:
                commandSender.sendMessage(Utils.translate("commands.remove_group.invalid_name", strings[0]));
                break;
        }
        return true;
    }
}