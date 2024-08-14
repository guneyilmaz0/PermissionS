package commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import net.guneyilmaz0.permissions.Utils;
import net.guneyilmaz0.permissions.group.Group;
import net.guneyilmaz0.permissions.group.GroupManager;

public class ListGroups extends Command {
    public ListGroups() {
        super("listgroups", Utils.translate("commands.groups.description"));
        setAliases(new String[]{"groups"});
        setPermission("permissionss.listgroups");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!testPermission(commandSender)) return false;

        StringBuilder groups = new StringBuilder();
        for (Group group : GroupManager.getGroups()) groups.append(group.getName()).append(", ");

        groups.deleteCharAt(groups.length() - 2);
        commandSender.sendMessage(Utils.translate("commands.groups.all_registered_groups", groups.toString()));
        return false;
    }
}