package com.lothus.wadgets.sync.managers.particle;

import com.lothus.wadgets.sync.collectibles.particles.Particle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ParticleManager {

    private HashMap<String, Particle> particleHashMap = new HashMap<>();

    public void load(Particle particle) {
        particleHashMap.put(particle.getIdentify(), particle);
    }

    public void unload(String name) {
        particleHashMap.remove(name);
    }

    public Particle get(String name) {
        return particleHashMap.get(name);
    }

    public List<Particle> getAll() {
        List<Particle> p = new ArrayList<>();
        for (Particle particle : particleHashMap.values()) {
            p.add(particle);
        }
        return p;
    }
}
