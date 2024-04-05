package com.lothus.wadgets.sync.player;

import com.lothus.core.Core;
import com.lothus.core.player.LothPlayer;
import com.lothus.wadgets.sync.SyncPlatform;
import com.lothus.wadgets.sync.collectibles.clothes.Clothe;
import com.lothus.wadgets.sync.collectibles.gadgets.Gadget;
import com.lothus.wadgets.sync.collectibles.hat.Hat;
import com.lothus.wadgets.sync.collectibles.particles.Particle;
import com.lothus.wadgets.sync.collectibles.status.Status;
import com.lothus.wadgets.sync.player.status.StatusInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class CosmeticPlayer {

    private UUID uniqueId;

    private String hat;
    private String gadget;
    private String clothe;
    private StatusInfo status;

    private String particle;

    public CosmeticPlayer(UUID uniqueId) {
        this.uniqueId = uniqueId;

        this.hat = "None";
        this.gadget = "None";
        this.clothe = "None";
        this.status = new StatusInfo(Status.Color.DEFAULT, "None");
        this.particle = "None";
    }

    public int getAvaiableGadgets() {
        int avaiable = 0;
        LothPlayer lothPlayer = Core.getPlayerController().get(uniqueId);

        for (Gadget g : SyncPlatform.getGadgetManager().getGadgets()) {
            if (g.getRank().ordinal() <= lothPlayer.getGroup().getRank().ordinal()) {
                avaiable++;
                continue;
            }

            if (lothPlayer.getGroup().containsPermission(g.getPermission())) {
                avaiable++;
            }
        }
        return avaiable;
    }

    public int getAvaiableHats() {
        int avaiable = 0;
        LothPlayer lothPlayer = Core.getPlayerController().get(uniqueId);

        for (Hat g : SyncPlatform.getHatManager().getHats()) {
            if (g.getRank().ordinal() <= lothPlayer.getGroup().getRank().ordinal()) {
                avaiable++;
                continue;
            }

            if (lothPlayer.getGroup().containsPermission(g.getPermission())) {
                avaiable++;
            }
        }
        return avaiable;
    }

    public boolean isHat(Hat hat) {
        LothPlayer lothPlayer = Core.getPlayerController().get(uniqueId);

        if (lothPlayer.getGroup().getRank().ordinal() <= hat.getRank().ordinal()) {
            return true;
        }

        if (lothPlayer.getGroup().containsPermission(hat.getPermission())) {
            return true;
        }

        return false;
    }

    public boolean isGadget(Gadget gadget) {
        LothPlayer lothPlayer = Core.getPlayerController().get(uniqueId);

        if (lothPlayer.getGroup().getRank().ordinal() <= gadget.getRank().ordinal()) {
            return true;
        }

        if (lothPlayer.getGroup().containsPermission(gadget.getPermission())) {
            return true;
        }

        return false;
    }

    public boolean isClothe(Clothe c) {
        LothPlayer lothPlayer = Core.getPlayerController().get(uniqueId);

        if (lothPlayer.getGroup().getRank().ordinal() <= c.getRank().ordinal()) {
            return true;
        }

        if (lothPlayer.getGroup().containsPermission(c.getPermission())) {
            return true;
        }

        return false;
    }


    public boolean isStatus(Status status) {
        LothPlayer lothPlayer = Core.getPlayerController().get(uniqueId);

        if (lothPlayer.getGroup().getRank().ordinal() <= status.getRank().ordinal()) {
            return true;
        }

        if (lothPlayer.getGroup().containsPermission(status.getPermission())) {
            return true;
        }

        return false;
    }

    public boolean hasParticle(Particle particle) {
        LothPlayer lothPlayer = Core.getPlayerController().get(uniqueId);

        if (lothPlayer.getGroup().getRank().ordinal() <= particle.getRank().ordinal()) {
            return true;
        }

        if (lothPlayer.getGroup().containsPermission(particle.getPermission())) {
            return true;
        }

        return false;
    }
}
