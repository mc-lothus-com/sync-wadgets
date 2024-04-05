package com.lothus.wadgets.sync.listener;

import com.lothus.core.Core;
import com.lothus.core.event.update.UpdateEvent;
import com.lothus.core.player.LothPlayer;
import com.lothus.wadgets.sync.SyncPlatform;
import com.lothus.wadgets.sync.SyncWadget;
import com.lothus.wadgets.sync.collectibles.clothes.Clothe;
import com.lothus.wadgets.sync.collectibles.particles.Particle;
import com.lothus.wadgets.sync.collectibles.status.Status;
import com.lothus.wadgets.sync.player.CosmeticPlayer;
import com.lothus.wadgets.sync.collectibles.gadgets.Gadget;
import com.lothus.wadgets.sync.collectibles.hat.Hat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class PlayerJoinQuitListener implements Listener {

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        CosmeticPlayer cosmeticPlayer = Core.getGson().fromJson(Core.getRedis().get("cosmeticPlayer=" + event.getUniqueId().toString()), CosmeticPlayer.class);

        if (cosmeticPlayer == null) {
            cosmeticPlayer = SyncPlatform.getDataCosmeticPlayer().get(event.getUniqueId());
            if (cosmeticPlayer == null) {
                cosmeticPlayer = new CosmeticPlayer(event.getUniqueId());
                SyncPlatform.getDataCosmeticPlayer().create(cosmeticPlayer);
            }
        }
        SyncPlatform.getCosmeticPlayerManager().load(cosmeticPlayer);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        CosmeticPlayer cosmeticPlayer = SyncPlatform.getCosmeticPlayerManager().get(player.getUniqueId());
        LothPlayer l = Core.getPlayerController().get(player.getUniqueId());

        Bukkit.getScheduler().runTaskLater(SyncWadget.getPlugin(), () -> {
            if (!cosmeticPlayer.getHat().equalsIgnoreCase("None")) {
                Hat hat = SyncPlatform.getHatManager().get(cosmeticPlayer.getHat());
                player.getInventory().setHelmet(hat.getHat());
            } else {
                player.getInventory().setHelmet(null);
            }
            if (!cosmeticPlayer.getGadget().equalsIgnoreCase("None")) {
                Gadget gadget = SyncPlatform.getGadgetManager().getGadget(cosmeticPlayer.getGadget());
                player.getInventory().setItem((Core.getServerInfo().getType().name().startsWith("LOBBY_") ? 5 : 2), gadget.getGadget());
            }
            if (!cosmeticPlayer.getClothe().equalsIgnoreCase("None")) {
                Clothe gadget = SyncPlatform.getClothesManager().getGadget(cosmeticPlayer.getClothe());
                if (gadget != null) gadget.applyClothes(player);
            }
        }, 10L);
    }

    private HashMap<UUID, Long> timeout = new HashMap<>();

    @EventHandler(priority = EventPriority.LOW)
    public void onUpdate(UpdateEvent event) {
        Player player = event.getPlayer();

        CosmeticPlayer cosmeticPlayer = SyncPlatform.getCosmeticPlayerManager().get(player.getUniqueId());

        if (cosmeticPlayer == null) return;

        if (cosmeticPlayer.getParticle() == null) return;
        if (cosmeticPlayer.getParticle() == "None") return;
        if (cosmeticPlayer.getParticle().equalsIgnoreCase("None")) return;

        Particle particle = SyncPlatform.getParticleManager().get(cosmeticPlayer.getParticle());

        if (particle == null) return;

        if (timeout.get(player.getUniqueId()) != null && timeout.get(player.getUniqueId()) > System.currentTimeMillis()) {
            return;
        }

        Location location = player.getLocation().clone();
        LothPlayer lothPlayer = Core.getPlayerController().get(player.getUniqueId());

        if (!lothPlayer.getPrefs().isVanish()) {
            particle.playEffect(location.add(0, 2.5, 0));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if ((player.getPassenger() != null && player.getPassenger() instanceof ArmorStand)) {
            player.getPassenger().remove();
        }

        SyncPlatform.getCosmeticPlayerManager().unload(player.getUniqueId());
    }
}
