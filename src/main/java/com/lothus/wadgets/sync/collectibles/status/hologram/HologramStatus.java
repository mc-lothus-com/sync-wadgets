package com.lothus.wadgets.sync.collectibles.status.hologram;

import com.lothus.core.api.hologram.Hologram;
import com.lothus.core.api.hologram.HologramRow;
import lombok.NonNull;
import net.minecraft.server.v1_8_R3.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class HologramStatus extends HologramRow {


    public HologramStatus(Player player, String message) {
        super(player.getLocation().clone(), message);
    }

    @Override
    public void spawn(Player player) {
        super.spawn(player);
        PacketPlayOutAttachEntity attach = new PacketPlayOutAttachEntity(0, getArmorStand(), ((CraftPlayer) player).getHandle());
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(attach);
    }
}
