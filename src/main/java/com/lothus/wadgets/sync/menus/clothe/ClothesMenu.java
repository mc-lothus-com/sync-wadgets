package com.lothus.wadgets.sync.menus.clothe;

import com.lothus.core.utils.bukkit.ItemCreator;
import com.lothus.wadgets.sync.SyncPlatform;
import com.lothus.wadgets.sync.collectibles.clothes.Clothe;
import com.lothus.wadgets.sync.menus.CosmeticMenu;
import com.lothus.wadgets.sync.player.CosmeticPlayer;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ClothesMenu implements Listener {

    private HashMap<UUID, Long> cooldown = new HashMap<>();

    public static void open(Player player) {
        CosmeticPlayer cosmeticPlayer = SyncPlatform.getCosmeticPlayerManager().get(player.getUniqueId());
        Inventory inventory = Bukkit.createInventory(null, 9*6, "Roupas");

        int slot = 9;
        for (Clothe clothe : SyncPlatform.getClothesManager().getClothes()) {
            slot++;

            if (slot == 17) slot+=2;
            if (slot == 26) slot+=2;
            if (slot == 35) slot+=2;

            ItemStack itemStack = clothe.getIcon();

            if (!cosmeticPlayer.isClothe(clothe)) {
                inventory.setItem(slot, new ItemCreator(Material.INK_SACK, itemStack.getItemMeta().getDisplayName()).setLore(itemStack.getItemMeta().getLore()).setId(1).build());
            } else {
                inventory.setItem(slot, itemStack);
            }
        }

        inventory.setItem(48, new ItemCreator(Material.BARRIER,"§cRemover Roupas").build());
        inventory.setItem(49, new ItemCreator(Material.ARROW, "§cVoltar").build());

        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack itemStack = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        CosmeticPlayer cosmeticPlayer = SyncPlatform.getCosmeticPlayerManager().get(player.getUniqueId());

        if (inventory == null)return;
        if (itemStack == null)return;
        if (itemStack.getType() == Material.AIR)return;

        if (!inventory.getName().equalsIgnoreCase("Roupas"))return;

        event.setCancelled(true);

        if (event.getRawSlot() == 48) {
            if (cooldown.get(player.getUniqueId()) != null && cooldown.get(player.getUniqueId()) > System.currentTimeMillis()) {
                player.sendMessage("§cVocê deve aguardar para interagir novamente.");
                return;
            }

            cooldown.put(player.getUniqueId(), System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(2));
            cosmeticPlayer.setClothe("None");
            player.closeInventory();
            player.getInventory().setChestplate(null);
            player.getInventory().setLeggings(null);
            player.getInventory().setBoots(null);
            SyncPlatform.getDataCosmeticPlayer().update(cosmeticPlayer);
            return;
        }

        if (event.getRawSlot() == 49) {
            CosmeticMenu.open(player);
            return;
        }

        if (itemStack.getType().equals(Material.INK_SACK)) {
            player.sendMessage("§cVocê não possui esta roupa.");
            return;
        }

        if (cooldown.get(player.getUniqueId()) != null && cooldown.get(player.getUniqueId()) > System.currentTimeMillis()) {
            player.sendMessage("§cVocê deve aguardar para interagir novamente.");
            return;
        }

        cooldown.put(player.getUniqueId(), System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(2));

        player.closeInventory();
        for (Clothe g : SyncPlatform.getClothesManager().getClothes()) {

            net.minecraft.server.v1_8_R3.ItemStack nms = CraftItemStack.asNMSCopy(itemStack);

            if (nms.hasTag()) {
                NBTTagCompound tag = nms.getTag();
                if (tag.getString("clothe").equalsIgnoreCase(g.getIdentify())) {
                    if (cosmeticPlayer.getClothe() == g.getIdentify()) {
                        player.sendMessage("§cVocê já está usando essa roupa.");
                        return;
                    }
                    g.applyClothes(player);
                    cosmeticPlayer.setClothe(g.getIdentify());
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 5, 5);
                    player.sendMessage("§eVocê selecionou §b" + g.getIdentify() + "§e.");
                    SyncPlatform.getDataCosmeticPlayer().update(cosmeticPlayer);
                    return;
                }
            }
        }
    }
}
