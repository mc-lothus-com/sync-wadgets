package com.lothus.wadgets.sync.menus;

import com.lothus.core.Core;
import com.lothus.core.player.LothPlayer;
import com.lothus.core.utils.bukkit.ItemCreator;
import com.lothus.wadgets.sync.SyncPlatform;
import com.lothus.wadgets.sync.menus.clothe.ClothesMenu;
import com.lothus.wadgets.sync.menus.gadget.GadgetMenu;
import com.lothus.wadgets.sync.menus.hat.HatMenu;
import com.lothus.wadgets.sync.menus.particle.ParticleMenu;
import com.lothus.wadgets.sync.menus.status.StatusMenu;
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

public class CosmeticMenu implements Listener {

    public static void open(Player player) {
        CosmeticPlayer cosmeticPlayer = SyncPlatform.getCosmeticPlayerManager().get(player.getUniqueId());
        Inventory inventory = Bukkit.createInventory(null, 9*5, "Cosméticos");

        inventory.setItem(13, new ItemCreator(Material.SKULL_ITEM, "§a" + player.getName())
                .setLore(
                        "",
                        "§fChapéu: §6" + cosmeticPlayer.getHat().replace("None", "Nenhum"),
                        "§fEngenhoca: §6" +  cosmeticPlayer.getGadget().replace("None", "Nenhum"),
                        "§fRoupa: §6" +  cosmeticPlayer.getClothe().replace("None", "Nenhum"),
                        "§fParticula: §6" + cosmeticPlayer.getParticle().replace("None", "Nenhum")
                ).setId(3).setAmount(1).withSkullOwner(player.getName()).build());

        for (int slot = 18; slot < 27; slot++) {
            inventory.setItem(slot, new ItemCreator(Material.STAINED_GLASS_PANE, " ").setId(7).build());
        }

        inventory.setItem(29, new ItemCreator(Material.GOLD_HELMET,
                "§aChapéus").setLore(
                        "§eClique para expandir."
        ).build());

        inventory.setItem(30, new ItemCreator(Material.PISTON_STICKY_BASE,
                "§aEngenhocas").setLore(
                "§eClique para expandir."
        ).build());

        inventory.setItem(32, new ItemCreator(Material.LEATHER_LEGGINGS,
                "§aRoupas").setLore(
                "§eClique para expandir."
        ).build());

        inventory.setItem(33, new ItemCreator(Material.BLAZE_POWDER,
                "§aPartículas").setLore(
                "§eClique para expandir."
        ).build());

        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        LothPlayer lothPlayer = Core.getPlayerController().get(player.getUniqueId());
        Inventory inventory = event.getClickedInventory();
        ItemStack itemStack = event.getCurrentItem();

        if (inventory ==null)return;
        if (itemStack ==null)return;
        if (itemStack.getType() == Material.AIR)return;

        if (lothPlayer ==null)return;

        if (!inventory.getName().equalsIgnoreCase("Cosméticos"))return;

        event.setCancelled(true);

        player.playSound(player.getLocation(), Sound.CLICK, 2.0f, 2.0f);

        if (event.getRawSlot() == 30) {
            GadgetMenu.open(player);
            return;
        }

        if (event.getRawSlot() == 29) {
            HatMenu.open(player);
            return;
        }

        if (event.getRawSlot() == 32) {
            ClothesMenu.open(player);
            return;
        }
        if (event.getRawSlot() == 33) {
            ParticleMenu.open(player);
            return;
        }
    }
}
