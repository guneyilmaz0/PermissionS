package net.guneyilmaz0.permissionss.commands;

import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandMap;

import java.util.ArrayList;
import java.util.List;

public class PermissionSCommands {
    public static void init() {
        CommandMap map = Server.getInstance().getCommandMap();
        List<Command> commands = new ArrayList<>();
        commands.add(new AddGroup());
        commands.add(new RemoveGroup());
        commands.add(new SetGroup());
        commands.add(new ListGroups());
        commands.add(new DefaultGroup());
        commands.add(new PermissionsSInfo());
        commands.add(new SetFormat());
        commands.add(new SetUserPermission());
        commands.add(new UnsetUserPermission());
        map.registerAll("permissionss", commands);
    }
}
