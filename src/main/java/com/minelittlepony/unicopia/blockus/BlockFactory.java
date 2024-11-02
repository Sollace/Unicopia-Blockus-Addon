package com.minelittlepony.unicopia.blockus;

import java.util.function.Function;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public interface BlockFactory<T extends Block> {
    T create(BlockState baseState, Block.Settings settings);

    default T create(Block base) {
        return create(base.getDefaultState(), Block.Settings.copy(base));
    }

    static <T extends Block> BlockFactory<T> of(Function<Block.Settings, T> function) {
        return (base, settings) -> function.apply(settings);
    }
}
