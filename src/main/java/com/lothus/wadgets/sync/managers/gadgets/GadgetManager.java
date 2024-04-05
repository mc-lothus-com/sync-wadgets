package com.lothus.wadgets.sync.managers.gadgets;

import com.lothus.wadgets.sync.collectibles.gadgets.Gadget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GadgetManager {

    private HashMap<String, Gadget> gads = new HashMap<>();

    public void load(Gadget gad) {
        gads.put(gad.getIdentify(), gad);
    }

    public void unload(Gadget gad) {
        gads.remove(gad.getIdentify());
    }

    public Gadget getGadget(String name) {
        return gads.get(name);
    }

    public List<Gadget> getGadgets() {
        List<Gadget> g = new ArrayList<>();
        for (Gadget gad : gads.values()) {
            g.add(gad);
        }
        return g;
    }
}
