package com.github.shatteredsuite.scrolls.data.scroll;

public enum BindingType {
    UNBOUND,
    WARP,
    LOCATION;

    public static BindingType fromString(String value) {
        if(value.equalsIgnoreCase("unbound")) {
            return UNBOUND;
        }
        if(value.equalsIgnoreCase("warp")) {
            return WARP;
        }
        if(value.equalsIgnoreCase("location")) {
            return LOCATION;
        }
        return null;
    }
}
