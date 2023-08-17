package net.swade.permissionss.event;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.plugin.PluginEvent;
import lombok.Getter;
import net.swade.permissionss.Main;
import net.swade.permissionss.group.Group;

public class PlayerGroupChangeEvent extends PluginEvent {
    @Getter private final Player player;
    @Getter private final Group newGroup;
    @Getter private final Group oldGroup;
    @Getter private final CommandSender changer;

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    public PlayerGroupChangeEvent(Player player, Group newGroup, Group oldGroup, CommandSender changer) {
        super(Main.getInstance());
        this.player = player;
        this.newGroup = newGroup;
        this.oldGroup = oldGroup;
        this.changer = changer;
    }
}
