package com.lothus.wadgets.sync.menus.status;

import com.lothus.core.utils.bukkit.ItemCreator;
import com.lothus.wadgets.sync.SyncPlatform;
import com.lothus.wadgets.sync.collectibles.status.Status;
import com.lothus.wadgets.sync.player.CosmeticPlayer;
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

public class StatusMenu implements Listener  {

    public static void open(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9*6, "Status");

        int slot = 9;
        for (Status status : SyncPlatform.getStatusManager().getAll()) {
            if (status.getIdentify() == "None")continue;
            slot++;

            if (slot == 17) slot+=2;
            if (slot == 26) slot+=2;
            if (slot == 35) slot+=2;

            ItemStack icon = status.icon(player);

            inventory.setItem(slot, icon);
        }

        inventory.setItem(45, new ItemCreator(Material.WOOL, "§aSelecionar cor")
                .setLore("§eClique para expandir.").build());

        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        ItemStack itemStack = event.getCurrentItem();

        if (inventory == null) return;
        if (itemStack == null) return;
        if (itemStack.getType() == Material.AIR) return;

        if (!inventory.getName().equalsIgnoreCase("Status"))return;

        event.setCancelled(true);

        if (event.getRawSlot() == 45) {
            return;
        }

        net.minecraft.server.v1_8_R3.ItemStack i = CraftItemStack.asNMSCopy(itemStack);

        if (!i.hasTag())return;

        CosmeticPlayer cosmeticPlayer = SyncPlatform.getCosmeticPlayerManager().get(player.getUniqueId());
        Status status = SyncPlatform.getStatusManager().getAll().stream().filter(s -> s.getName().endsWith(itemStack.getItemMeta().getDisplayName().split("§a")[1])).findFirst().orElse(null);

        if (status == null) {
            player.sendMessage("§cO status selecionado não foi encontrado.");
            return;
        }

        if (cosmeticPlayer.getStatus().getStatus().equalsIgnoreCase(status.getIdentify())) {
            cosmeticPlayer.getStatus().setStatus("None");
            status.getStatus().despawn(player);
            return;
        }

        player.playSound(player.getLocation(), Sound.NOTE_PLING, 5, 5);
        cosmeticPlayer.getStatus().setStatus(status.getIdentify());
        status.apply(player);

        SyncPlatform.getDataCosmeticPlayer().update(cosmeticPlayer);
    }
}
