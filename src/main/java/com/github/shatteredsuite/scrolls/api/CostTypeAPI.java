package com.github.shatteredsuite.scrolls.api;

import com.github.shatteredsuite.scrolls.data.scroll.cost.CostType;

public interface CostTypeAPI {

    CostType get(String id);

    void register(CostType costType);

    Iterable<CostType> getAll();
}
