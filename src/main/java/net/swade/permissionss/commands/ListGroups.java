package net.swade.permissionss.commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import net.swade.permissionss.Utils;
import net.swade.permissionss.group.Group;
import net.swade.permissionss.group.GroupManager;

public class ListGroups extends Command {
    public ListGroups() {
        super("listgroups", Utils.translate("commands.groups.description"));
        setAliases(new String[]{"groups"});
        setPermission("permissionss.listgroups");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!testPermission(commandSender)) {
            return false;
        }

        StringBuilder groups = new StringBuilder();
        for (Group group : GroupManager.groups) {
            groups.append(group.getName()).append(", ");
        }
        groups.deleteCharAt(groups.length() - 2);
        commandSender.sendMessage(Utils.translate("commands.groups.all_registered_groups", groups.toString()));
        return false;
    }
}
