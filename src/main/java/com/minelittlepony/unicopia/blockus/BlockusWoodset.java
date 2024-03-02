package com.minelittlepony.unicopia.blockus;

import java.util.List;
import java.util.stream.Stream;

import net.minecraft.block.Block;

public record BlockusWoodset (
        String id,
        Block planks, Block slab, Block log,

        List<Block> mosaics,
        List<Block> mossy,
        List<Block> timberFrames,

        Block herringbonePlanks,
        Block smallLogs) {

    public BlockusWoodset(String id, Block planks, Block slab, Block log, BlockFactories factory, Registrar registrar) {
        this(
            id,
            planks, slab, log,
            BlockTrio.createBSS(id + "_mosaic", "", planks, factory, registrar),
            BlockTrio.createBSS("mossy_" + id, "_planks", planks, factory, registrar),
            BlockTrio.createTimber(id, planks, factory, registrar),
            registrar.register("herringbone_" + id + "_planks", factory.block().create(planks)),
            registrar.register(id + "_small_logs", factory.pillar().create(log))
        );
    }

    public Stream<Block> blocks() {
        return Stream.concat(
                Stream.concat(
                    Stream.concat(mosaics.stream(), mossy.stream()),
                    Stream.of(herringbonePlanks, smallLogs)
                ),
                timberFrames.stream()
        );
    }
}
