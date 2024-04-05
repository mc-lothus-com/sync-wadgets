package com.lothus.wadgets.sync.collectibles.gadgets.loader;

import com.lothus.core.utils.bukkit.classes.ClassGetter;
import com.lothus.wadgets.sync.SyncPlatform;
import com.lothus.wadgets.sync.collectibles.gadgets.Gadget;
import org.bukkit.plugin.java.JavaPlugin;

public class GadgetLoader {

    public static void loadGadgets(JavaPlugin plugin, String path) {
        for (Class<?> gadC : ClassGetter.getClassesForPackage(plugin, path)) {
            if (Gadget.class.isAssignableFrom(gadC)) {
                try {
                    Gadget gadget = (Gadget) gadC.newInstance();
                    SyncPlatform.getGadgetManager().load(gadget);
                    plugin.getServer().getPluginManager().registerEvents(gadget, plugin);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
