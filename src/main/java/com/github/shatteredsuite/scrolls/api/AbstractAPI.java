package com.github.shatteredsuite.scrolls.api;

import com.github.shatteredsuite.core.util.Identified;
import com.github.shatteredsuite.scrolls.data.warp.Warp;
import org.bukkit.Location;

public interface AbstractAPI<T extends Identified> {

    T get(String id);

    void register(T item);

    Iterable<T> getAll();

    void delete(T item);

    void delete(String id);

    Iterable<String> getIds();
}
