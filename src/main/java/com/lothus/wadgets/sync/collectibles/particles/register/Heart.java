package com.lothus.wadgets.sync.collectibles.particles.register;

import com.lothus.core.player.group.rank.Rank;
import com.lothus.wadgets.sync.collectibles.particles.Particle;
import org.bukkit.Effect;
public class Heart extends Particle {

    public Heart() {
        super(
                "Heart",
                Effect.HEART,
                Rank.PRO,
                "cosmetic.particle.heart"
        );
    }
}
