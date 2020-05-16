package com.github.shatteredsuite.scrolls.data.scroll;

import com.github.shatteredsuite.core.include.nbt.NBTCompound;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class UnboundBindingType extends BindingType {
    public UnboundBindingType(String key) {
        super("location");
    }

    @Override
    public BindingData deserialize(Map<String, Object> map) {
        return null;
    }

    @Override
    public BindingData fromNBT(NBTCompound compound) {
        return new UnboundBindingData();
    }
}
