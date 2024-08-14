package commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import net.guneyilmaz0.permissions.Utils;
import net.guneyilmaz0.permissions.group.Group;
import net.guneyilmaz0.permissions.group.GroupManager;

public class SetFormat extends Command {
    public SetFormat() {
        super("setformat", Utils.translate("commands.set_format.description"));
        setUsage(Utils.translate("commands.set_format.usage"));
        setPermission("permissionss.setformat");
        commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newType("group", CommandParamType.STRING),
                CommandParameter.newType("format", CommandParamType.TEXT)
        });
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!testPermission(commandSender)) return false;

        if (strings.length < 2) {
            commandSender.sendMessage(getUsage());
            return false;
        }

        Group group = GroupManager.getGroup(strings[0]);
        if (group == null) {
            commandSender.sendMessage(Utils.translate("commands.set_format.not_found", strings[0]));
            return false;
        }

        StringBuilder chatFormat = new StringBuilder();

        for (int i = 1; i < strings.length; ++i) chatFormat.append(strings[i]).append(" ");


        if (!chatFormat.isEmpty()) chatFormat = new StringBuilder(chatFormat.substring(0, chatFormat.length() - 1));

        GroupManager.setChatFormat(group, String.valueOf(chatFormat));
        commandSender.sendMessage(Utils.translate("commands.set_format.success", strings[0], String.valueOf(chatFormat)));
        return false;
    }
}