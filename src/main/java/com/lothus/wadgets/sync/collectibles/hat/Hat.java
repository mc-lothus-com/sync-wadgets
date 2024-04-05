package com.lothus.wadgets.sync.collectibles.hat;

import com.lothus.core.player.group.rank.Rank;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@Getter @Setter
public abstract class Hat {

    private String identify;

    private String value;

    private Rank rank;
    private String permission;

    public Hat(String identify) {
        this.identify = identify;
    }

    public Hat(String identify, String value) {
        this.identify = identify;
        this.value = value;
    }

    public Hat(String identify, String value, Rank rank) {
        this.identify = identify;
        this.value = value;
        this.rank = rank;
    }

    public Hat(String identify, String value, Rank rank, String permission) {
        this.identify = identify;
        this.value = value;
        this.rank = rank;
        this.permission = permission;
    }

    public abstract ItemStack getHat();
    public abstract ItemStack getIcon();
}
