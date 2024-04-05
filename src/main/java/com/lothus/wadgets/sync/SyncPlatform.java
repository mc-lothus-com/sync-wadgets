package com.lothus.wadgets.sync;

import com.lothus.wadgets.sync.data.DataCosmeticPlayer;
import com.lothus.wadgets.sync.managers.clothes.ClothesManager;
import com.lothus.wadgets.sync.managers.particle.ParticleManager;
import com.lothus.wadgets.sync.managers.player.CosmeticPlayerManager;
import com.lothus.wadgets.sync.managers.gadgets.GadgetManager;
import com.lothus.wadgets.sync.managers.hats.HatManager;
import com.lothus.wadgets.sync.managers.status.StatusManager;
import lombok.Getter;
import lombok.Setter;

import java.util.logging.Logger;

public class SyncPlatform {

    @Getter @Setter
    private static Logger logger;

    @Getter @Setter
    private static DataCosmeticPlayer dataCosmeticPlayer;


    @Getter
    private static HatManager  hatManager = new HatManager();

    @Getter
    private static StatusManager statusManager = new StatusManager();

    @Getter
    private static GadgetManager gadgetManager = new GadgetManager();

    @Getter
    private static ClothesManager clothesManager = new ClothesManager();

    @Getter
    private static ParticleManager particleManager = new ParticleManager();


    @Getter
    private static CosmeticPlayerManager cosmeticPlayerManager = new CosmeticPlayerManager();
}
