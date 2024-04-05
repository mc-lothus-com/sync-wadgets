package com.lothus.wadgets.sync.collectibles.gadgets.register;

import com.lothus.core.player.group.rank.Rank;
import com.lothus.core.utils.bukkit.ItemCreator;
import com.lothus.wadgets.sync.collectibles.gadgets.Gadget;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Thor extends Gadget {

    private HashMap<UUID, Long> cooldown = new HashMap<>();

    public Thor() {
        super( "Thor");

        setName("Thor");
        setRank(Rank.VIP);
        setPermission("cosmetic.gadget.thor");
    }


    @Override
    public ItemStack getIcon() {
        net.minecraft.server.v1_8_R3.ItemStack nms = CraftItemStack.asNMSCopy(new ItemStack(Material.BLAZE_ROD));

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
        return new ItemCreator(Material.BLAZE_ROD, "§aThor").build();
    }


    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null)return;

        ItemMeta meta = item.getItemMeta();

        if (item.getType() != Material.BLAZE_ROD)return;
        if (meta.getDisplayName().equalsIgnoreCase("§a" + getIdentify())) {
            if (cooldown.get(player.getUniqueId()) != null && cooldown.get(player.getUniqueId()) > System.currentTimeMillis()) {
                player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
                player.sendMessage("§cVocê deve aguardar para usar este gadget novamente.");
                return;
            }

            cooldown.put(player.getUniqueId(), System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(30));

            Location location = (event.getClickedBlock() == null ? player.getLocation() : event.getClickedBlock().getLocation());
            World world = player.getWorld();

            world.strikeLightning(location);
            world.playSound(location, Sound.EXPLODE, 10f, 10f);
        }
    }
}
