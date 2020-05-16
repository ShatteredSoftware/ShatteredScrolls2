package com.github.shatteredsuite.scrolls.items;

public enum NBTVersion {
    VERSION_2(2, null),
    VERSION_1(1, VersionConverter::createScrollInstanceFromV1),
    NONE(0, null);

    public final int nbtSpecifier;
    public final VersionConversion conversion;

    NBTVersion(int nbt_version, VersionConversion conversion) {
        this.nbtSpecifier = nbt_version;
        this.conversion = conversion;
    }
}
