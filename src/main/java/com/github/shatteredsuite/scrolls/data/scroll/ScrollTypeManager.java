package com.github.shatteredsuite.scrolls.data.scroll;

import com.github.shatteredsuite.core.util.StringUtil;
import com.github.shatteredsuite.scrolls.api.ScrollAPI;
import com.github.shatteredsuite.scrolls.items.ScrollInstance;
import java.util.LinkedHashMap;

public class ScrollTypeManager implements ScrollAPI {
    private LinkedHashMap<String, ScrollType> scrollTypes = new LinkedHashMap<>();

    @Override
    public void register(String id, ScrollType type) {
        if(type == null) {
            return;
        }
        if(StringUtil.isEmptyOrNull(id)) {
            return;
        }
        this.scrollTypes.put(id, type);
    }

    @Override
    public ScrollType get(String id) {
        return this.scrollTypes.get(id);
    }

    @Override
    public ScrollInstance createInstance(ScrollType type) {
        return type.createInstance();
    }
}
