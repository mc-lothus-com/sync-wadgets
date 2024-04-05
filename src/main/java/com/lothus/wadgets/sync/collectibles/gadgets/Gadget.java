package com.lothus.wadgets.sync.collectibles.gadgets;

import com.lothus.core.player.group.rank.Rank;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

@Getter @Setter
public abstract class Gadget implements Listener {

    private String name;
    private String identify;

    private Rank rank;
    private String permission;

    public Gadget(String identify) {
        this.identify = identify;
    }

    public Gadget(String name, String identify, Rank rank, String permission) {
        this.name = name;
        this.identify = identify;
        this.rank = rank;
        this.permission = permission;
    }

    public Gadget(String identify, Rank rank) {
        this.identify = identify;
        this.rank = rank;
    }

    public Gadget(String identify, Rank rank, String permission) {
        this.identify = identify;
        this.rank = rank;
        this.permission = permission;
    }

    public abstract ItemStack getIcon();
    public abstract ItemStack getGadget();
}
