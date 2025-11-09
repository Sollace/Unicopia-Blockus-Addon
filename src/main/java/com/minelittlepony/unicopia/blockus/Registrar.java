package com.minelittlepony.unicopia.blockus;

import java.util.function.Function;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKey;

public interface Registrar {
    default Block register(String name, KeyedFactory<? extends Block> value) {
        return register(name, value, true);
    }

    default Block register(String name, Function<Block.Settings, Block> value) {
        return register(name, value, true);
    }

    default Block register(String name, Block.Settings settings, Function<Block.Settings, Block> value, boolean addItem) {
        return register(name, (KeyedFactory<Block>)(key -> value.apply(settings)), addItem);
    }

    default Block register(String name, Function<Block.Settings, Block> value, boolean addItem) {
        return register(name, Block.Settings.create(), value, addItem);
    }

    Block register(String name, KeyedFactory<? extends Block> value, boolean addItem);

    Registrar alias(String oldName, String newName);

    public interface KeyedFactory<T> {
        T apply(RegistryKey<? super T> key);
    }
}
