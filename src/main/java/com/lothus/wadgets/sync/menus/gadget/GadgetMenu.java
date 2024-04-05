package com.lothus.wadgets.sync.menus.gadget;

import com.lothus.core.Core;
import com.lothus.core.utils.bukkit.ItemCreator;
import com.lothus.wadgets.sync.SyncPlatform;
import com.lothus.wadgets.sync.menus.CosmeticMenu;
import com.lothus.wadgets.sync.player.CosmeticPlayer;
import com.lothus.wadgets.sync.collectibles.gadgets.Gadget;
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

public class GadgetMenu implements Listener {

    public static void open(Player player) {
        CosmeticPlayer cosmeticPlayer = SyncPlatform.getCosmeticPlayerManager().get(player.getUniqueId());
        Inventory inventory = Bukkit.createInventory(null, 9*6, "Engenhocas");

        int slot = 9;
        for (Gadget g : SyncPlatform.getGadgetManager().getGadgets()) {
            if (g.getIdentify() == "None") continue;

            slot++;

            if (slot == 17) slot+=2;
            if (slot == 26) slot+=2;
            if (slot == 35) slot+=2;

            ItemStack icon = g.getIcon();

            if (!cosmeticPlayer.isGadget(g)) {
                inventory.setItem(slot, new ItemCreator(Material.INK_SACK, icon.getItemMeta().getDisplayName()).setLore(icon.getItemMeta().getLore()).setId(1).build());
            } else {
                inventory.setItem(slot, icon);
            }
        }

        inventory.setItem(48, new ItemCreator(Material.BARRIER,"§cRemover Engenhoca").build());
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

        if (!inventory.getName().equalsIgnoreCase("Engenhocas")) return;

        event.setCancelled(true);

        if (event.getRawSlot() == 48) {
            cosmeticPlayer.setGadget("None");
            player.closeInventory();
            player.getInventory().setItem((Core.getServerInfo().getType().name().startsWith("LOBBY_") ? 5 : 2), new ItemStack(Material.AIR));
            SyncPlatform.getDataCosmeticPlayer().update(cosmeticPlayer);
            return;
        }

        if (event.getRawSlot() == 49) {
            CosmeticMenu.open(player);
            return;
        }

        if (currentItem.getType().equals(Material.INK_SACK)) {
            player.sendMessage("§cVocê não tem permissão para utilizar essa engenhoca.");
            return;
        }

        if (currentItem.getType().equals(Material.AIR))return;

        player.closeInventory();
        for (Gadget g : SyncPlatform.getGadgetManager().getGadgets()) {

            net.minecraft.server.v1_8_R3.ItemStack nms = CraftItemStack.asNMSCopy(currentItem);
            if (nms.hasTag()) {
                NBTTagCompound tag = nms.getTag();
                if (tag.getString("gadget").equalsIgnoreCase(g.getIdentify())) {
                    if (cosmeticPlayer.getGadget() == g.getIdentify()) {
                        player.sendMessage("§cVocê já está usando essa engenhoca.");
                        return;
                    }
                    cosmeticPlayer.setGadget(g.getIdentify());
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 5, 5);
                    player.sendMessage("§eVocê selecionou §b" + g.getName() + "§e.");
                    player.getInventory().setItem((Core.getServerInfo().getType().name().startsWith("LOBBY_") ? 5 : 2), g.getGadget());
                    SyncPlatform.getDataCosmeticPlayer().update(cosmeticPlayer);
                    return;
                }
            }
        }
    }
}
