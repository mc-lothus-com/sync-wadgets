package com.lothus.wadgets.sync.collectibles.status.register;

import com.lothus.core.Core;
import com.lothus.core.api.level.LevelColor;
import com.lothus.core.player.LothPlayer;
import com.lothus.core.player.group.rank.Rank;
import com.lothus.wadgets.sync.collectibles.status.Status;
import com.lothus.wadgets.sync.collectibles.status.hologram.HologramStatus;
import org.bukkit.entity.Player;

public class Level extends Status {

    public Level() {
        super(
                "Nível",
                "level",
                Color.GREEN,
                Rank.VIP,
                "cosmetic.status.level",
                "§bNível: {color} {level}{symbol}",
                null
        );
    }

    @Override
    public HologramStatus configure(Player player) {
        LothPlayer lothPlayer = Core.getPlayerController().get(player.getUniqueId());
        String message = getMessage()
                .replace("{color}", LevelColor.getLevelColor(lothPlayer.getLevel()).getColor())
                .replace("{level}", String.valueOf(lothPlayer.getLevel()))
                .replace("{symbol}", LevelColor.getLevelColor(lothPlayer.getLevel()).getSymbol()
       );

        return new HologramStatus(player, message);
    }

    @Override
    public String replacedMessage(Player player) {
        LothPlayer lothPlayer = Core.getPlayerController().get(player.getUniqueId());
        return  getMessage()
                .replace("{color}", LevelColor.getLevelColor(lothPlayer.getLevel()).getColor())
                .replace("{level}", String.valueOf(lothPlayer.getLevel()))
                .replace("{symbol}", LevelColor.getLevelColor(lothPlayer.getLevel()).getSymbol()
                );
    }
}
