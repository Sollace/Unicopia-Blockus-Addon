package com.minelittlepony.unicopia.blockus;

import net.minecraft.block.Block;

public interface Registrar {
    default Block register(String name, Block value) {
        return register(name, value, true);
    }

    Block register(String name, Block value, boolean addItem);
}
