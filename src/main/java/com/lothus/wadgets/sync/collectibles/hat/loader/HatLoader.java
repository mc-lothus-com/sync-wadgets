package com.lothus.wadgets.sync.collectibles.hat.loader;

import com.lothus.core.utils.bukkit.classes.ClassGetter;
import com.lothus.wadgets.sync.SyncPlatform;
import com.lothus.wadgets.sync.collectibles.hat.Hat;
import org.bukkit.plugin.java.JavaPlugin;

public class HatLoader {

    public static void loadHats(JavaPlugin plugin, String path) {
        for (Class<?> hatC : ClassGetter.getClassesForPackage(plugin, path)) {
            if (Hat.class.isAssignableFrom(hatC)) {
                try {
                    Hat hat = (Hat) hatC.newInstance();
                    SyncPlatform.getHatManager().load(hat);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
