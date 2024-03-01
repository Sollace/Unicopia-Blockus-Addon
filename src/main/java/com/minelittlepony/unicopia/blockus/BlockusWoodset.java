package com.minelittlepony.unicopia.blockus;

import java.util.List;
import java.util.stream.Stream;

import com.brand.blockus.content.BlocksRegistration;
import net.minecraft.block.Block;

public record BlockusWoodset (
        String id,
        Block planks, Block slab, Block log, Block leaves, Block sapling,

        List<Block> mosaics,
        List<Block> mossy,
        List<Block> timberFrames,

        Block herringbonePlanks,
        Block smallLogs,
        Block smallHedge,
        Block flowerPot) {

    public BlockusWoodset(String id, Block planks, Block slab, Block log, Block leaves, Block sapling, BlockFactories factory, Registrar registrar) {
        this(
            id,
            planks, slab, log, leaves, sapling,
            BlockTrio.createBSS(id + "_mosaic", "", planks, factory, registrar),
            BlockTrio.createBSS("mossy_" + id, "_planks", planks, factory, registrar),
            BlockTrio.createTimber(id, planks, factory, registrar),
            registrar.register("herringbone_" + id + "_planks", factory.block().create(planks)),
            registrar.register(id + "_small_logs", factory.pillar().create(log)),
            registrar.register(id + "_small_hedge", factory.hedge().create(leaves)),
            registrar.register("potted_large_" + id, BlocksRegistration.createLargeFlowerPot(sapling), false)
        );
    }

    public Stream<Block> buildingBlocks() {
        return Stream.concat(
                Stream.concat(
                    Stream.concat(mosaics.stream(), mossy.stream()),
                    Stream.of(herringbonePlanks, smallLogs)
                ),
                timberFrames.stream()
        );
    }

    public Stream<Block> blocks() {
        return Stream.concat(buildingBlocks(), Stream.of(smallHedge, flowerPot));
    }
}
