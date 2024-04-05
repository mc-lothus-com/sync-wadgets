package com.lothus.wadgets.sync.collectibles.gadgets.register;

import com.lothus.core.player.group.rank.Rank;
import com.lothus.core.utils.bukkit.ItemCreator;
import com.lothus.wadgets.sync.collectibles.gadgets.Gadget;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Fireball extends Gadget {

    private HashMap<UUID, Long> cooldown = new HashMap<>();

    public Fireball() {
        super("Fireball");

        setRank(Rank.VIP);
        setName("§aBola de Fogo");
        setPermission("cosmetic.gadget.fireball");
    }


    @Override
    public ItemStack getIcon() {
        List<String> lore = new ArrayList<>();

        lore.add("§7Exclusivo para " + getRank().getColor() + getRank().getName() + "§7.");

        net.minecraft.server.v1_8_R3.ItemStack nms = CraftItemStack.asNMSCopy(new ItemStack(Material.FIREBALL));

        NBTTagCompound tag = new NBTTagCompound();

        tag.setString("gadget", getIdentify());

        nms.setTag(tag);

        ItemStack stack = CraftItemStack.asBukkitCopy(nms);
        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName("§a" + getName());
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    @Override
    public ItemStack getGadget() {
        return new ItemCreator(Material.FIREBALL, "§a" + getIdentify()).build();
    }


    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null)return;

        ItemMeta meta = item.getItemMeta();

        if (item.getType() != Material.FIREBALL)return;
        if (meta.getDisplayName().equalsIgnoreCase("§a" + getIdentify())) {
            if (cooldown.get(player.getUniqueId()) != null && cooldown.get(player.getUniqueId()) > System.currentTimeMillis()) {
                player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
                player.sendMessage("§cVocê deve aguardar para usar este gadget novamente.");
                return;
            }

            cooldown.put(player.getUniqueId(), System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(45));
            org.bukkit.entity.Fireball fireball = player.launchProjectile(org.bukkit.entity.Fireball.class);

            Location location = player.getLocation();

            fireball.setDirection(location.getDirection());
            fireball.setFireTicks(0);
            fireball.setIsIncendiary(false);
            fireball.setYield(2.0f);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.getEntity() instanceof org.bukkit.entity.Fireball) {
            event.setCancelled(true);

            for (Entity entity : event.getEntity().getNearbyEntities(3, 3, 3)) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    Location location = player.getLocation();
                    try {
                        Vector direction = player.getLocation()
                                .getDirection()
                                .clone()
                                .multiply(-1)
                                .multiply(1.2);

                        entity.setVelocity(direction);
                        location.getWorld().playEffect(location, Effect.EXPLOSION_LARGE, null);
                        player.setVelocity(new Vector(player.getVelocity().getX(), 1.0D, player.getVelocity().getZ()));
                    } catch (Exception ignored) {}
                }
            }
        }
    }
}
