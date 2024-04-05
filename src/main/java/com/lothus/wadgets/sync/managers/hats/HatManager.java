package com.lothus.wadgets.sync.managers.hats;

import com.lothus.wadgets.sync.collectibles.hat.Hat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HatManager {

    private HashMap<String, Hat> hats = new HashMap<>();

    public void load(Hat h) {
        hats.put(h.getIdentify(), h);
    }

    public void unload(Hat hat) {
        hats.remove(hat.getIdentify());
    }

    public Hat get(String identify) {
        return hats.get(identify);
    }

    public List<Hat> getHats() {
        return new ArrayList<>(hats.values());
    }
}
