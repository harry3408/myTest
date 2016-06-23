package com.harry.designpattrn.observer;

import java.util.ArrayList;
import java.util.List;

public class ConcreteWatched implements Watched {

    public List<Watcher> watchers = new ArrayList<Watcher>();

    @Override
    public void addWatcher(Watcher watcher) {
        watchers.add(watcher);
    }

    @Override
    public void removeWatcher(Watcher watcher) {
        watchers.remove(watcher);
    }

    @Override
    public void notifyWatcher(String str) {
        for (Watcher watcher : watchers) {
            watcher.update(str);
        }
    }
}
