package net.swade.permissionss.commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import net.swade.permissionss.Utils;
import net.swade.permissionss.group.Group;
import net.swade.permissionss.group.GroupManager;

public class DefaultGroup extends Command {
    public DefaultGroup() {
        super("defaultgroup", Utils.translate("commands.default_group.description"));
        setUsage(Utils.translate("commands.default_group.usage"));
        setPermission("permissionss.defaultgroup");
        commandParameters.clear();
        commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newType("group", CommandParamType.STRING)
        });
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!testPermission(commandSender)){
            return false;
        }

        if (strings.length != 1){
            commandSender.sendMessage(getUsage());
            return false;
        }

        Group group = GroupManager.getGroup(strings[0]);
        if (group == null){
            commandSender.sendMessage(Utils.translate("commands.default_group.not_found", strings[0]));
            return false;
        }

        GroupManager.setDefaultGroup(group);
        commandSender.sendMessage(Utils.translate("commands.default_group.success", group.getName()));
        return false;
    }
}
