package com.lothus.wadgets.sync.collectibles.particles.register;

import com.lothus.core.player.group.rank.Rank;
import com.lothus.wadgets.sync.collectibles.particles.Particle;
import org.bukkit.Effect;

public class Song extends Particle {

    public Song() {
        super(
                "Song",
                Effect.NOTE,
                Rank.MASTER,
                "cosmetic.particle.song"
        );
    }
}
