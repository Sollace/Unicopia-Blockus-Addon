package com.minelittlepony.unicopia.blockus;

import com.brand.blockus.blocks.base.LargeFlowerPotBlock;
import com.brand.blockus.blocks.base.OrientableBlockBase;
import com.brand.blockus.blocks.base.PostBlock;
import com.brand.blockus.blocks.base.SmallHedgeBlock;
import com.brand.blockus.registry.content.BlockusBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public record BlockFactories(
        BlockFactory<?> block,
        BlockFactory<?> slab,
        BlockFactory<?> stairs,
        BlockFactory<?> pillar,
        BlockFactory<?> hedge,
        BlockFactory<?> orientable,
        BlockFactory<?> post,
        BlockFactory<?> largeFlowerPot
) {
    public static final BlockFactories DEFAULT = new BlockFactories(
            BlockFactory.of(Block::new),
            BlockFactory.of(SlabBlock::new),
            BlockFactory.of(StairsBlock::new),
            BlockFactory.of(PillarBlock::new),
            (base, settings) -> key -> new SmallHedgeBlock(settings.allowsSpawning(BlockFactories::canSpawnOnLeaves).suffocates(BlockFactories::never).blockVision(BlockFactories::never)),
            BlockFactory.of(OrientableBlockBase::new),
            BlockFactory.of(PostBlock::new),
            (base, settings) -> key -> new LargeFlowerPotBlock(base.getBlock(), BlockusBlocks.largeFlowerPotSettings())
    );

    public static boolean always(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    public static Boolean always(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return true;
    }

    public static boolean never(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    public static Boolean never(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return false;
    }

    public static Boolean canSpawnOnLeaves(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return type == EntityType.OCELOT || type == EntityType.PARROT;
    }
}