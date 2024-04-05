package com.lothus.wadgets.sync.collectibles.hat.register;

import com.lothus.wadgets.sync.collectibles.hat.Hat;
import com.lothus.core.player.group.rank.Rank;
import com.lothus.core.utils.bukkit.ItemCreator;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class Biscoito extends Hat {

    public Biscoito() {
        super(
                "DoutorBiscoito",
                "DoutorBiscoito",
                Rank.BETA,
                "cosmetic.head.biscoito"
        );
    }

    @Override
    public ItemStack getHat() {
        return new ItemCreator(Material.SKULL_ITEM, getIdentify()).setId(3).setAmount(1).withSkullOwner(getValue()).build();
    }

    @Override
    public ItemStack getIcon() {
        List<String> lore = new ArrayList<>();
        lore.add("§7Exclusivo para " + getRank().getColor() + getRank().getName() + "§7.");

        net.minecraft.server.v1_8_R3.ItemStack nms = CraftItemStack.asNMSCopy(new ItemStack(Material.SKULL_ITEM, 1, (short)3));

        NBTTagCompound tag = new NBTTagCompound();

        tag.setString("hat", getIdentify());

        nms.setTag(tag);

        ItemStack stack = CraftItemStack.asBukkitCopy(nms);
        SkullMeta meta = (SkullMeta) stack.getItemMeta();

        meta.setDisplayName("§a" + getIdentify());
        meta.setLore(lore);
        meta.setOwner(getValue());
        stack.setItemMeta(meta);
        return stack;
    }
}
