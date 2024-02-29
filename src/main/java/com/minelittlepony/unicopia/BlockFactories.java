package com.minelittlepony.unicopia;

import com.brand.blockus.blocks.base.OrientableBlockBase;
import com.brand.blockus.blocks.base.SmallHedgeBlock;
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
        BlockFactory<?> orientable
) {
    public static final BlockFactories DEFAULT = new BlockFactories(
            BlockFactory.of(Block::new),
            BlockFactory.of(SlabBlock::new),
            StairsBlock::new,
            BlockFactory.of(PillarBlock::new),
            (base, settings) -> new SmallHedgeBlock(settings.allowsSpawning(BlockFactories::canSpawnOnLeaves).suffocates(BlockFactories::never).blockVision(BlockFactories::never)),
            BlockFactory.of(OrientableBlockBase::new)
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