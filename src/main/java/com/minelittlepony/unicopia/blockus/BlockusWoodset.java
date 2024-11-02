package com.minelittlepony.unicopia.blockus;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import net.minecraft.block.Block;
import net.minecraft.util.Pair;

public record BlockusWoodset (
        String id,
        Block planks, Block slab, Block log, Block strippedLog,

        List<Block> mosaics,
        List<Block> mossy,
        List<Block> timberFrames,
        List<Block> posts,

        Block herringbonePlanks,
        Block smallLogs,

        Optional<BlockusWoodset> waxedSet) {

    public BlockusWoodset(String id, Block planks, Block slab, Block log, Block strippedLog, BlockFactories factory, Registrar registrar) {
        this(id, planks, slab, log, strippedLog, factory, registrar, Optional.empty());
    }

    public BlockusWoodset(String id, Block planks, Block slab, Block log, Block strippedLog, BlockFactories factory, Registrar registrar, BlockusWoodset waxedSet) {
        this(id, planks, slab, log, strippedLog, factory, registrar, Optional.of(waxedSet));
    }

    public BlockusWoodset(String id, Block planks, Block slab, Block log, Block strippedLog, BlockFactories factory, Registrar registrar, Optional<BlockusWoodset> waxedSet) {
        this(
            id,
            planks, slab, log, strippedLog,
            BlockTrio.createBSS(id + "_mosaic", "", planks, factory, registrar),
            BlockTrio.createBSS("mossy_" + id, "_planks", planks, factory, registrar),
            BlockTrio.createTimber(id, planks, factory, registrar),
            BlockTrio.createPosts(id, log, factory, registrar),
            registrar.register("herringbone_" + id + "_planks", factory.block().create(planks)),
            registrar.register(id + "_small_logs", factory.pillar().create(log)),
            waxedSet
        );
    }

    public Stream<Block> blocks() {
        return Stream.of(mosaics, mossy, List.of(herringbonePlanks, smallLogs), timberFrames, posts).flatMap(List::stream);
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

    public Stream<Pair<Block, Block>> strippablePairs() {
        return Stream.concat(
                Stream.of(new Pair<>(posts.get(0), posts.get(1))),
                waxedSet.stream().flatMap(BlockusWoodset::strippablePairs)
        );
    }
}
