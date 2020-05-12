package com.github.shatteredsuite.scrolls.data.warp;

import com.github.shatteredsuite.core.util.StringUtil;
import com.github.shatteredsuite.scrolls.api.WarpAPI;
import java.util.LinkedHashMap;


public class WarpManager implements WarpAPI {
    LinkedHashMap<String, Warp> warps = new LinkedHashMap<>();

    @Override
    public Warp get(String id) {
        return warps.get(id);
    }

    @Override
    public void register(String id, Warp warp) {
        if(warp == null) {
            return;
        }
        if(StringUtil.isEmptyOrNull(id)) {
            return;
        }
        this.warps.put(id, warp);
    }
}
