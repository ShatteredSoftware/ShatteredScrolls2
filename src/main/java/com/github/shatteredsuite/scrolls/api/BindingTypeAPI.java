package com.github.shatteredsuite.scrolls.api;

import com.github.shatteredsuite.scrolls.data.scroll.BindingType;

public interface BindingTypeAPI {
    BindingType get(String id);
    void register(String type, BindingType bindingType);
}
