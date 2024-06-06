package net.guneyilmaz0.permissionss.event;

import cn.nukkit.Player;
import cn.nukkit.event.plugin.PluginEvent;
import lombok.Getter;
import net.guneyilmaz0.permissionss.Main;

@Getter
public class PlayerRankExpiredEvent extends PluginEvent {
    private final Player player;

    public PlayerRankExpiredEvent(Player player) {
        super(Main.getInstance());
        this.player = player;
    }
}
