package com.lothus.wadgets.sync.managers.status;

import com.lothus.wadgets.sync.collectibles.status.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatusManager {

    private HashMap<String, Status> status = new HashMap<>();

    public void load(Status s) {
        status.put(s.getIdentify(), s);
    }

    public void unload(String name) {
        status.remove(name);
    }

    public Status get(String name) {
        return status.get(name);
    }

    public List<Status> getAll() {
        return new ArrayList<>(status.values());
    }
}
