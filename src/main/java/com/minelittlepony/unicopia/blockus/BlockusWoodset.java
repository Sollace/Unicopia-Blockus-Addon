package com.minelittlepony.unicopia.blockus;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import net.minecraft.block.Block;
import net.minecraft.util.Pair;

public record BlockusWoodset (
        String id,
        Block planks, Block slab, Block log,

        List<Block> mosaics,
        List<Block> mossy,
        List<Block> timberFrames,

        Block herringbonePlanks,
        Block smallLogs,

        Optional<BlockusWoodset> waxedSet) {

    public BlockusWoodset(String id, Block planks, Block slab, Block log, BlockFactories factory, Registrar registrar) {
        this(id, planks, slab, log, factory, registrar, Optional.empty());
    }

    public BlockusWoodset(String id, Block planks, Block slab, Block log, BlockFactories factory, Registrar registrar, BlockusWoodset waxedSet) {
        this(id, planks, slab, log, factory, registrar, Optional.of(waxedSet));
    }

    public BlockusWoodset(String id, Block planks, Block slab, Block log, BlockFactories factory, Registrar registrar, Optional<BlockusWoodset> waxedSet) {
        this(
            id,
            planks, slab, log,
            BlockTrio.createBSS(id + "_mosaic", "", planks, factory, registrar),
            BlockTrio.createBSS("mossy_" + id, "_planks", planks, factory, registrar),
            BlockTrio.createTimber(id, planks, factory, registrar),
            registrar.register("herringbone_" + id + "_planks", factory.block().create(planks)),
            registrar.register(id + "_small_logs", factory.pillar().create(log)),
            waxedSet
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

    public Stream<Pair<Block, Block>> waxablePairs() {
        return waxedSet().stream().flatMap(waxedset -> {
            List<Block> normalBlocks = blocks().toList();
            List<Block> waxedBlocks = waxedset.blocks().toList();
            return IntStream
                    .range(0, normalBlocks.size())
                    .mapToObj(i -> new Pair<>(normalBlocks.get(i), waxedBlocks.get(i)));
        });
    }
}
