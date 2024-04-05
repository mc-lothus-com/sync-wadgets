package com.lothus.wadgets.sync.collectibles.gadgets.register;

import com.lothus.core.player.group.rank.Rank;
import com.lothus.core.utils.bukkit.ItemCreator;
import com.lothus.wadgets.sync.SyncPlatform;
import com.lothus.wadgets.sync.collectibles.gadgets.Gadget;
import com.lothus.wadgets.sync.player.CosmeticPlayer;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Teleporter extends Gadget {

    public Teleporter() {
        super(
                "Pérola do Fim", "EnderPearl", Rank.PRO, "cosmetic.gadget.enderpearl"
        );
    }

    @Override
    public ItemStack getIcon() {
        net.minecraft.server.v1_8_R3.ItemStack nms = CraftItemStack.asNMSCopy(new ItemStack(Material.ENDER_PEARL));

        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("gadget", getIdentify());
        nms.setTag(tag);

        ItemStack stack = CraftItemStack.asBukkitCopy(nms);
        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName("§a" + getName());
        meta.setLore(Arrays.asList("§7Exclusivo para " + getRank().getColor() + getRank().getName() + "§7."));
        stack.setItemMeta(meta);
        return stack;
    }

    @Override
    public ItemStack getGadget() {
        return new ItemCreator(Material.ENDER_PEARL, "§a" + getName()).build();
    }

    private HashMap<UUID, Long> cooldown = new HashMap<>();

    @EventHandler
    public void onLeaveVehicle(VehicleExitEvent event) {
        Entity entity = event.getExited();

        if (!(entity instanceof Player))return;

        if (event.getVehicle() == null)return;
        if (!(event.getVehicle() instanceof EnderPearl))return;

        Player player = (Player) entity;

        player.getWorld().getEntities().forEach(e -> {
            if (e.getEntityId() == event.getVehicle().getEntityId()) {
                e.remove();
            }
        });
    }

    @EventHandler
    public void onProjectile(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null) return;
        if (item.getType() == Material.AIR)return;
        if (item.getType() != Material.ENDER_PEARL)return;

        CosmeticPlayer cosmeticPlayer = SyncPlatform.getCosmeticPlayerManager().get(player.getUniqueId());

        if (cosmeticPlayer == null)return;
        if (cosmeticPlayer.getGadget() == null)return;

        if (!cosmeticPlayer.getGadget().equals(getIdentify()))return;

        event.setCancelled(true);

        if (cooldown.containsKey(player.getUniqueId())) {
            if (cooldown.get(player.getUniqueId()) > System.currentTimeMillis()) {
                player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
                player.sendMessage("§cVocê deve aguardar para utilizar esse gadget novamente.");
                return;
            }
        }

        cooldown.put(player.getUniqueId(), System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(3));

        EnderPearl pearl = player.launchProjectile(EnderPearl.class);

        pearl.setPassenger(player);

        player.setItemInHand(item);
    }

}
