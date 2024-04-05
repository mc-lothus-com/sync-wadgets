package com.lothus.wadgets.sync.collectibles.status.loader;

import com.lothus.core.utils.bukkit.classes.ClassGetter;
import com.lothus.wadgets.sync.SyncPlatform;
import com.lothus.wadgets.sync.collectibles.status.Status;
import org.bukkit.plugin.java.JavaPlugin;

public class StatusLoader {

    public static void loadStatus(JavaPlugin plugin, String path) {
        for (Class<?> hatC : ClassGetter.getClassesForPackage(plugin, path)) {
            if (Status.class.isAssignableFrom(hatC)) {
                try {
                    Status hat = (Status) hatC.newInstance();
                    SyncPlatform.getStatusManager().load(hat);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
