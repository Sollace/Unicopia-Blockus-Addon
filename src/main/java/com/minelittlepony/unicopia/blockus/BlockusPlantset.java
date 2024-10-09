package com.minelittlepony.unicopia.blockus;

import com.brand.blockus.registry.content.BlockusBlocks;

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
            registrar.register(id + "_small_hedge", factory.hedge().create(leaves)),
            registrar.register("potted_large_" + id, BlockusBlocks.createLargeFlowerPot(sapling), false)
        );
    }
}
