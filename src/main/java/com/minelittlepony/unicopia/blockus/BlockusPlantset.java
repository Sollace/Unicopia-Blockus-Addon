package com.minelittlepony.unicopia.blockus;

import net.minecraft.block.Block;

public record BlockusPlantset(
        String id,
        Block leaves, Block sapling,
        Block smallLogs,
        Block smallHedge,
        Block flowerPot) {
    public BlockusPlantset(String id, Block leaves, Block sapling, Block smallLogs, BlockFactories factory, Registrar registrar) {
        this(
            id,
            leaves, sapling, smallLogs,
            registrar.alias(id + "_small_hedge", id + "_hedge").register(id + "_hedge", factory.hedge().create(leaves)),
            registrar.alias("potted_large_" + id, "potted_" + id).register("potted_" + id, factory.largeFlowerPot().create(sapling), false)
        );
    }
}
