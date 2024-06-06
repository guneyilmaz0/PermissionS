package net.guneyilmaz0.permissionss.task;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.scheduler.Task;
import cn.nukkit.utils.Config;
import net.guneyilmaz0.permissionss.Main;
import net.guneyilmaz0.permissionss.Profile;
import net.guneyilmaz0.permissionss.event.PlayerRankExpiredEvent;

public class ExpireDateCheckTask extends Task {
    @Override
    public void onRun(int i) {
        Config config = new Config(Main.getInstance().getPlayerConfigPath(), 1);
        for (Player value : Server.getInstance().getOnlinePlayers().values()) {
            if (!config.exists(value.getName().toLowerCase())) continue;
            Profile profile = Profile.getProfile(value.getName());
            if (System.currentTimeMillis() < profile.getTime()) continue;
            PlayerRankExpiredEvent event = new PlayerRankExpiredEvent(value);
            Server.getInstance().getPluginManager().callEvent(event);
        }
    }
}
