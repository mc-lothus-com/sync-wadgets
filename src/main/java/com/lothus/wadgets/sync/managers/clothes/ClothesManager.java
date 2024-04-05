package com.lothus.wadgets.sync.managers.clothes;

import com.lothus.wadgets.sync.collectibles.clothes.Clothe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClothesManager {

    private HashMap<String, Clothe> gads = new HashMap<>();

    public void load(Clothe gad) {
        gads.put(gad.getIdentify(), gad);
    }

    public void unload(Clothe gad) {
        gads.remove(gad.getIdentify());
    }

    public Clothe getGadget(String name) {
        return gads.get(name);
    }

    public List<Clothe> getClothes() {
        List<Clothe> g = new ArrayList<>();
        for (Clothe gad : gads.values()) {
            g.add(gad);
        }
        return g;
    }
}
