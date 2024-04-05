package com.lothus.wadgets.sync.menus.hat;

import com.lothus.core.utils.bukkit.ItemCreator;
import com.lothus.wadgets.sync.SyncPlatform;
import com.lothus.wadgets.sync.collectibles.hat.Hat;
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

public class HatMenu implements Listener {

    public static void open(Player player) {
        CosmeticPlayer cosmeticPlayer = SyncPlatform.getCosmeticPlayerManager().get(player.getUniqueId());
        Inventory inventory = Bukkit.createInventory(null, 9*6, "Chapéus");

        int slot = 9;
        for (Hat hat : SyncPlatform.getHatManager().getHats()) {
            if (hat.getIdentify() == "None")continue;
            slot++;

            if (slot == 17) slot+=2;
            if (slot == 26) slot+=2;
            if (slot == 35) slot+=2;

            ItemStack icon = hat.getIcon();

            if (!cosmeticPlayer.isHat(hat)) {
                inventory.setItem(slot, new ItemCreator(Material.INK_SACK, icon.getItemMeta().getDisplayName()).setLore(icon.getItemMeta().getLore()).setId(1).build());
            } else {
                inventory.setItem(slot, icon);
            }
        }


        inventory.setItem(48, new ItemCreator(Material.BARRIER,"§cRemover Chapéu").build());
        inventory.setItem(49, new ItemCreator(Material.ARROW, "§cVoltar").build());

        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        ItemStack currentItem = event.getCurrentItem();

        CosmeticPlayer cosmeticPlayer = SyncPlatform.getCosmeticPlayerManager().get(player.getUniqueId());
        if (inventory == null)return;
        if (currentItem == null)return;

        if (!inventory.getName().equalsIgnoreCase("Chapéus"))return;

        event.setCancelled(true);

        if (currentItem.getType().equals(Material.AIR))return;

        if (event.getRawSlot() == 48) {
            player.playSound(player.getLocation(), Sound.CLICK, 2.0f, 2.0f);
            cosmeticPlayer.setHat("None");
            player.getInventory().setHelmet(null);
            player.closeInventory();
            SyncPlatform.getDataCosmeticPlayer().update(cosmeticPlayer);
            return;
        }

        if (event.getRawSlot() == 49) {
            player.playSound(player.getLocation(), Sound.CLICK, 2.0f, 2.0f);
            CosmeticMenu.open(player);
            return;
        }

        if (currentItem.getType().equals(Material.INK_SACK)) {
            player.sendMessage("§cVocê não tem permissão para utilizar este chapéu.");
            return;
        }
        player.closeInventory();
        for (Hat g : SyncPlatform.getHatManager().getHats()) {
            net.minecraft.server.v1_8_R3.ItemStack nms = CraftItemStack.asNMSCopy(currentItem);
            if (nms.hasTag()) {
                NBTTagCompound tag = nms.getTag();
                if (tag.getString("hat").equalsIgnoreCase(g.getIdentify())) {
                    if (cosmeticPlayer.getHat() == g.getIdentify()) {
                        player.sendMessage("§cVocê já está usando este chapéu.");
                        return;
                    }
                    cosmeticPlayer.setHat(g.getIdentify());
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 5, 5);
                    player.sendMessage("§eVocê selecionou §b" + g.getIdentify() + "§e.");
                    player.getInventory().setHelmet(g.getHat());
                    SyncPlatform.getDataCosmeticPlayer().update(cosmeticPlayer);
                    return;
                }
            }
        }

    }

}
