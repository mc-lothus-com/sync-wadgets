package com.lothus.wadgets.sync.collectibles.clothes.register;

import com.lothus.core.player.group.rank.Rank;
import com.lothus.wadgets.sync.collectibles.clothes.Clothe;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;

public class Red extends Clothe {

    public Red() {
        super(
                "Vermelho",
                Rank.VIP,
                "cosmetic.clothe.red"
        );
    }

    @Override
    public ItemStack getIcon() {
        List<String> lore = new ArrayList<>();

        lore.add("§7Exclusivo para " + getRank().getColor() + getRank().getName() + "§7.");

        net.minecraft.server.v1_8_R3.ItemStack nms = CraftItemStack.asNMSCopy(new ItemStack(Material.LEATHER_CHESTPLATE));
        NBTTagCompound t = new NBTTagCompound();

        t.setString("clothe", getIdentify());

        nms.setTag(t);

        ItemStack item = CraftItemStack.asBukkitCopy(nms);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();

        meta.setDisplayName("§c" + getIdentify());
        meta.setLore(lore);

        meta.setColor(Color.RED);

        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void applyClothes(Player player) {
        PlayerInventory inventory = player.getInventory();

        for (int i = 0; i < 3; i++) {
            ItemStack item = new ItemStack((i == 0 ? Material.LEATHER_CHESTPLATE : (i == 1 ? Material.LEATHER_LEGGINGS : Material.LEATHER_BOOTS)));
            LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
            meta.setColor(Color.RED);
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS);
            item.setItemMeta(meta);
            inventory.setItem(38 - i, item);
        }
    }
}
