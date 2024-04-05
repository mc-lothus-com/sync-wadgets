package com.lothus.wadgets.sync.menus.particle;

import com.lothus.core.utils.bukkit.ItemCreator;
import com.lothus.wadgets.sync.SyncPlatform;
import com.lothus.wadgets.sync.collectibles.particles.Particle;
import com.lothus.wadgets.sync.menus.CosmeticMenu;
import com.lothus.wadgets.sync.player.CosmeticPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ParticleMenu implements Listener {

    public static void open(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 6, "Partículas");

        int slot = 9;
        for (Particle particle : SyncPlatform.getParticleManager().getAll()) {
            if (particle.getIdentify() == "None") continue;
            slot++;

            if (slot == 17) slot += 2;
            if (slot == 26) slot += 2;
            if (slot == 35) slot += 2;

            ItemStack icon = particle.icon(player);

            inventory.setItem(slot, icon);
        }


        inventory.setItem(48, new ItemCreator(Material.BARRIER, "§cRemover Partícula").build());
        inventory.setItem(49, new ItemCreator(Material.ARROW, "§cVoltar").build());

        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        ItemStack currentItem = event.getCurrentItem();

        CosmeticPlayer cosmeticPlayer = SyncPlatform.getCosmeticPlayerManager().get(player.getUniqueId());
        if (inventory == null) return;
        if (currentItem == null) return;

        if (!inventory.getName().equalsIgnoreCase("Partículas")) return;

        event.setCancelled(true);

        if (currentItem.getType().equals(Material.AIR)) return;

        if (event.getRawSlot() == 48) {
            player.playSound(player.getLocation(), Sound.CLICK, 2.0f, 2.0f);
            cosmeticPlayer.setParticle("None");
            player.closeInventory();
            SyncPlatform.getDataCosmeticPlayer().update(cosmeticPlayer);
            return;
        }

        if (event.getRawSlot() == 49) {
            player.playSound(player.getLocation(), Sound.CLICK, 2.0f, 2.0f);
            CosmeticMenu.open(player);
            return;
        }

        Particle g = SyncPlatform.getParticleManager().get(currentItem.getItemMeta().getDisplayName().split("§a")[1]);
        if (cosmeticPlayer.getParticle() == g.getIdentify()) {
            player.sendMessage("§cVocê já está usando essa partícula.");
            return;
        }

        if (!cosmeticPlayer.hasParticle(g)) {
            player.sendMessage("§cVocê não pode utilizar esse cosmético.");
            return;
        }

        cosmeticPlayer.setParticle(g.getIdentify());
        player.closeInventory();
        player.playSound(player.getLocation(), Sound.NOTE_PLING, 5, 5);
        player.sendMessage("§eVocê selecionou §b" + g.getIdentify() + "§e.");
        SyncPlatform.getDataCosmeticPlayer().update(cosmeticPlayer);
        return;
    }
}
