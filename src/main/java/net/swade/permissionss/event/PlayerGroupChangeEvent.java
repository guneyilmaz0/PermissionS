package net.swade.permissionss.event;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.plugin.PluginEvent;
import lombok.Getter;
import net.swade.permissionss.Main;
import net.swade.permissionss.group.Group;

@Getter
public class PlayerGroupChangeEvent extends PluginEvent {
    private final Player player;
    private final Group newGroup;
    private final Group oldGroup;
    private final CommandSender changer;

    private static final HandlerList handlers = new HandlerList();

    public PlayerGroupChangeEvent(Player player, Group newGroup, Group oldGroup, CommandSender changer) {
        super(Main.getInstance());
        this.player = player;
        this.newGroup = newGroup;
        this.oldGroup = oldGroup;
        this.changer = changer;
    }
}
