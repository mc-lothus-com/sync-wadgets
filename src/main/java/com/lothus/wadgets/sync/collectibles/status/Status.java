package com.lothus.wadgets.sync.collectibles.status;

import com.lothus.core.api.hologram.HologramRow;
import com.lothus.core.player.group.rank.Rank;
import com.lothus.core.utils.bukkit.ItemCreator;
import com.lothus.wadgets.sync.SyncPlatform;
import com.lothus.wadgets.sync.collectibles.status.hologram.HologramStatus;
import com.lothus.wadgets.sync.player.CosmeticPlayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

@Getter @Setter
@AllArgsConstructor
public abstract class Status {

    private String name;
    private String identify;

    private Color colorDefault;

    private Rank rank;
    private String permission;

    private String message;

    private HologramRow status;

    public ItemStack icon(Player player) {
        CosmeticPlayer cosmeticPlayer = SyncPlatform.getCosmeticPlayerManager().get(player.getUniqueId());
        NBTTagCompound tagCompound = new NBTTagCompound();

        tagCompound.setString("identify", identify);

        if (cosmeticPlayer != null && cosmeticPlayer.getStatus().getStatus().equalsIgnoreCase(identify)) {
            return new ItemCreator(Material.INK_SACK, "§a" + name).setLore(
                    (
                            !cosmeticPlayer.isStatus(this) ?
                                    "§eCusto: §a500 coins §eou §6250 cash§e." :
                                    (cosmeticPlayer.getStatus().getStatus().equals(identify) ? "§aSelecionado." : "§eClique para selecionar")
                    )
            ).addEnchant(Enchantment.LURE, 1).addItemFlag(ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_UNBREAKABLE).setAmount(1).setId(5).addNBTTag(tagCompound).build();
        } else {
            return new ItemCreator(Material.INK_SACK, "§a" + name).setLore(
                    (
                            !cosmeticPlayer.isStatus(this) ?
                                    "§eCusto: §a500 coins §eou §6250 cash§e." :
                                    (cosmeticPlayer.getStatus().getStatus() != null && cosmeticPlayer.getStatus().getStatus().equals(identify) ? "§aSelecionado." : "§eClique para selecionar")
                    )
            ).setAmount(1).setId((cosmeticPlayer.isStatus(this) ? 10 : 1)).addItemFlag(ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_UNBREAKABLE).addNBTTag(tagCompound).build();
        }
    }

    public abstract HologramStatus configure(Player player);
    public abstract String replacedMessage(Player player);

    public void apply(Player player) {
        HologramStatus armor = configure(player);
        armor.getArmorStand().setSmall(true);
        armor.spawn(player);
    }

    @Getter
    @AllArgsConstructor
    public enum Color {

        PURPLE("§5"),
        PINK("§d"),
        YELLOW("§e"),
        RED("§c"),
        BLUE("§9"),
        GREEN("§2"),
        ORANGE("§6"),
        WHITE("§f"),
        GRAY("§7"),

        DEFAULT("§a");

        private final String prefix;
    }
}
