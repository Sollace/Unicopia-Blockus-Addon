package com.minelittlepony.unicopia;

import com.brand.blockus.content.BlocksRegistration;
import com.brand.blockus.content.types.BSSTypes;
import com.brand.blockus.content.types.PottedLargeTypes;
import com.brand.blockus.content.types.TimberFrameTypes;
import com.minelittlepony.unicopia.block.UBlocks;
import com.minelittlepony.unicopia.server.world.UTreeGen;

import net.minecraft.block.Block;

public interface UBlockusBlocks {
    BSSTypes PALM_MOSAIC = new BSSTypes("palm_mosaic", UBlocks.PALM_PLANKS);

    BSSTypes MOSSY_PALM_PLANKS = new BSSTypes("mossy_palm_planks", UBlocks.PALM_PLANKS);

    Block HERRINGBONE_PALM_PLANKS = BlocksRegistration.register("herringbone_palm_planks", BlocksRegistration.createCopy(UBlocks.PALM_PLANKS));
    Block PALM_SMALL_LOGS = BlocksRegistration.register("palm_small_logs", BlocksRegistration.createPillarCopy(UBlocks.PALM_LOG));
    TimberFrameTypes PALM_TIMBER_FRAME = new TimberFrameTypes("palm", UBlocks.PALM_PLANKS);
    Block PALM_SMALL_HEDGE = BlocksRegistration.registerSmallHedge("palm_small_hedge", UBlocks.PALM_LEAVES);

    PottedLargeTypes POTTED_PALM = new PottedLargeTypes("potted_palm", UTreeGen.BANANA_TREE.sapling().get());

    static void bootstrap() {
    }
}
