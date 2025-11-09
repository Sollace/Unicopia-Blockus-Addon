package com.minelittlepony.unicopia.blockus;

import java.util.function.BiFunction;
import java.util.function.Function;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public interface BlockFactory<T extends Block> {
    Registrar.KeyedFactory<T> create(BlockState baseState, Block.Settings settings);

    default Registrar.KeyedFactory<T> create(Block base) {
        return create(base.getDefaultState(), Block.Settings.copy(base));
    }

    static <T extends Block> BlockFactory<T> of(Function<Block.Settings, T> function) {
        return (base, settings) -> key -> function.apply(settings);
    }

    static <T extends Block> BlockFactory<T> of(BiFunction<BlockState, Block.Settings, T> function) {
        return (base, settings) -> key -> function.apply(base, settings);
    }
}
