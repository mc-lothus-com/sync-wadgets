package com.lothus.wadgets.sync.collectibles.clothes.loader;

import com.lothus.core.utils.bukkit.classes.ClassGetter;
import com.lothus.wadgets.sync.SyncPlatform;
import com.lothus.wadgets.sync.collectibles.clothes.Clothe;
import org.bukkit.plugin.java.JavaPlugin;

public class ClotheLoader {

    public static void loadClothe(JavaPlugin plugin, String path) {
        for (Class<?> gadC : ClassGetter.getClassesForPackage(plugin, path)) {
            if (Clothe.class.isAssignableFrom(gadC)) {
                try {
                    Clothe clothe = (Clothe) gadC.newInstance();
                    SyncPlatform.getClothesManager().load(clothe);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
