package com.lothus.wadgets.sync;

import com.lothus.core.api.loaders.ListenerLoader;
import com.lothus.wadgets.sync.collectibles.clothes.loader.ClotheLoader;
import com.lothus.wadgets.sync.collectibles.hat.loader.HatLoader;
import com.lothus.wadgets.sync.collectibles.particles.loader.ParticleLoader;
import com.lothus.wadgets.sync.collectibles.status.loader.StatusLoader;
import com.lothus.wadgets.sync.data.DataCosmeticPlayer;
import com.lothus.wadgets.sync.collectibles.gadgets.loader.GadgetLoader;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class SyncWadget {

    @Getter
    private static JavaPlugin plugin;

    public SyncWadget(JavaPlugin plugin) {
        this.plugin = plugin;

        SyncPlatform.setLogger(plugin.getLogger());
        SyncPlatform.setDataCosmeticPlayer(new DataCosmeticPlayer());

        ListenerLoader.loadListeners(plugin, "com.lothus.wadgets.sync.menus");
        ListenerLoader.loadListeners(plugin, "com.lothus.wadgets.sync.com.redelegit.npc.listener");
        HatLoader.loadHats(plugin, "com.lothus.wadgets.sync.collectibles.hat.register");
        GadgetLoader.loadGadgets(plugin, "com.lothus.wadgets.sync.collectibles.gadgets.register");
        ClotheLoader.loadClothe(plugin, "com.lothus.wadgets.sync.collectibles.clothes.register");
        StatusLoader.loadStatus(plugin, "com.lothus.wadgets.sync.collectibles.status.register");
        ParticleLoader.loadParticles(plugin, "com.lothus.wadgets.sync.collectibles.particles.register");
    }
}
