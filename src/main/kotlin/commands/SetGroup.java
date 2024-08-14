package commands;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import net.guneyilmaz0.permissions.Utils;
import net.guneyilmaz0.permissions.events.PlayerGroupChangeEvent;
import net.guneyilmaz0.permissions.group.Group;
import net.guneyilmaz0.permissions.group.GroupManager;

public class SetGroup extends Command {
    public SetGroup() {
        super("setgroup", Utils.translate("commands.set_group.description"));
        setUsage(Utils.translate("commands.set_group.usage"));
        setPermission("permissionss.setgroup");
        commandParameters.clear();
        commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newType("player", CommandParamType.TARGET),
                CommandParameter.newType("group", CommandParamType.STRING)
        });
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!testPermission(commandSender)) return false;

        if (strings.length != 2) {
            commandSender.sendMessage(getUsage());
            return false;
        }

        Group group = GroupManager.getGroup(strings[1]);
        if (group == null) {
            commandSender.sendMessage(Utils.translate("commands.set_group.not_found", strings[1]));
            return false;
        }

        String name = strings[0];
        Player player = Server.getInstance().getPlayer(strings[0]);
        if (player != null) {
            PlayerGroupChangeEvent event = new PlayerGroupChangeEvent(player, group, commandSender);
            Server.getInstance().getPluginManager().callEvent(event);
            name = player.getName();
        }
        GroupManager.setPlayerGroup(name, group);
        commandSender.sendMessage(Utils.translate("commands.set_group.success", name, group.getName()));
        return true;
    }
}
