package com.lothus.wadgets.sync.collectibles.particles;

import com.lothus.core.player.group.rank.Rank;
import com.lothus.core.utils.bukkit.ItemCreator;
import com.lothus.wadgets.sync.SyncPlatform;
import com.lothus.wadgets.sync.player.CosmeticPlayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

@Getter @Setter
@AllArgsConstructor
public abstract class Particle {

    private String identify;

    private Effect effect;

    private Rank rank;
    private String permission;

    public ItemStack icon(Player player) {
        CosmeticPlayer cosmeticPlayer = SyncPlatform.getCosmeticPlayerManager().get(player.getUniqueId());
        if (cosmeticPlayer != null && cosmeticPlayer.getParticle().equalsIgnoreCase(identify)) {
            return new ItemCreator(Material.INK_SACK, "§a" + identify).setLore(
                    (
                            !cosmeticPlayer.hasParticle(this) ?
                                    "§eCusto: §a500 coins §eou §6250 cash§e." :
                                    (cosmeticPlayer.getParticle().equals(identify) ? "§aSelecionado." : "§eClique para selecionar")
                    )
            ).addEnchant(Enchantment.LURE, 1).addItemFlag(ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_UNBREAKABLE).setAmount(1).setId(5).build();
        } else {
            return new ItemCreator(Material.INK_SACK, "§a" + identify).setLore(
                    (
                            !cosmeticPlayer.hasParticle(this) ?
                                    "§eCusto: §a500 coins §eou §6250 cash§e." :
                                    (cosmeticPlayer.getParticle() != null && cosmeticPlayer.getParticle().equals(identify) ? "§aSelecionado." : "§eClique para selecionar")
                    )
            ).setAmount(1).setId((cosmeticPlayer.hasParticle(this) ? 10 : 1)).addItemFlag(ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_UNBREAKABLE).build();
        }
    }

    public void playEffect(Location location) {
        World world = location.getWorld();

        world.playEffect(
                location,
                getEffect(),
                Integer.MAX_VALUE
        );
    }
}
