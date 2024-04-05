package com.lothus.wadgets.sync.managers.player;

import com.lothus.wadgets.sync.player.CosmeticPlayer;

import java.util.HashMap;
import java.util.UUID;

public class CosmeticPlayerManager {

    private HashMap<UUID, CosmeticPlayer> player = new HashMap<>();

    public void load(CosmeticPlayer lobbyPlayer) {
        player.put(lobbyPlayer.getUniqueId(), lobbyPlayer);
    }

    public void unload(UUID uuid) {
        player.remove(uuid);
    }

    public CosmeticPlayer get(UUID uniqueId) {
        return player.get(uniqueId);
    }

}
