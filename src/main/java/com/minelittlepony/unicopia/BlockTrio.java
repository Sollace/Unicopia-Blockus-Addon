package com.minelittlepony.unicopia;

import java.util.List;
import net.minecraft.block.Block;

public class BlockTrio {
    public static List<Block> createBSS(String type, String plankSuffex, Block base, BlockFactories factory, Registrar registrar) {
        return List.of(
                registrar.register(type + plankSuffex, factory.block().create(base)),
                registrar.register(type + "_slab", factory.slab().create(base)),
                registrar.register(type + "_stairs", factory.stairs().create(base))
        );
    }

    public static List<Block> createTimber(String type, Block base, BlockFactories factory, Registrar registrar) {
        return List.of(
                registrar.register(type + "_timber_frame", factory.block().create(base)),
                registrar.register(type + "_diagonal_timber_frame", factory.orientable().create(base)),
                registrar.register(type + "_cross_timber_frame", factory.block().create(base))
        );
    }
}
