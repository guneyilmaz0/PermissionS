package net.swade.permissionss.task;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.scheduler.Task;
import cn.nukkit.utils.Config;
import net.swade.permissionss.Main;
import net.swade.permissionss.Profile;
import net.swade.permissionss.event.PlayerRankExpiredEvent;

public class ExpireDateCheckTask extends Task {
    @Override
    public void onRun(int i) {
        Config config = new Config(Main.getInstance().getPlayerConfigPath(), 1);
        for (Player value : Server.getInstance().getOnlinePlayers().values()) {
            if (config.exists(value.getName().toLowerCase())){
                Profile profile = Profile.getProfile(value.getName());
                if (System.currentTimeMillis() >= profile.getTime()){
                    PlayerRankExpiredEvent event = new PlayerRankExpiredEvent(value);
                    Server.getInstance().getPluginManager().callEvent(event);
                }
            }
        }
    }
}
