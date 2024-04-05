package com.lothus.wadgets.sync.collectibles.particles.loader;

import com.lothus.core.utils.bukkit.classes.ClassGetter;
import com.lothus.wadgets.sync.SyncPlatform;
import com.lothus.wadgets.sync.collectibles.particles.Particle;
import org.bukkit.plugin.java.JavaPlugin;

public class ParticleLoader {

    public static void loadParticles(JavaPlugin plugin, String path) {
        for (Class<?> hatC : ClassGetter.getClassesForPackage(plugin, path)) {
            if (Particle.class.isAssignableFrom(hatC)) {
                try {
                    Particle hat = (Particle) hatC.newInstance();
                    SyncPlatform.getParticleManager().load(hat);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
