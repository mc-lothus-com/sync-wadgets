package com.lothus.wadgets.sync.collectibles.clothes;

import com.lothus.core.player.group.rank.Rank;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter @Setter
public abstract class Clothe {

    private String identify;

    private Rank rank;
    private String permission;

    public Clothe(String identify) {
        this.identify = identify;
    }

    public Clothe(String identify, Rank rank) {
        this.identify = identify;
        this.rank = rank;
    }

    public Clothe(String identify, Rank rank, String permission) {
        this.identify = identify;
        this.rank = rank;
        this.permission = permission;
    }

    public abstract ItemStack getIcon();

    public abstract void applyClothes(Player player);
}
