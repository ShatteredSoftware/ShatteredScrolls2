package com.github.shatteredsuite.scrolls.api;

import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingType;

public interface BindingTypeAPI {
    BindingType get(String id);
    Iterable<BindingType> getAll();
    void register(BindingType bindingType);
}
