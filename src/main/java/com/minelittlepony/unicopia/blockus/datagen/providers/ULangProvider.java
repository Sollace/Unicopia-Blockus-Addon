package com.minelittlepony.unicopia.blockus.datagen.providers;

import com.minelittlepony.unicopia.blockus.UBlockusBlocks;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;

public class ULangProvider extends LangProvider {

    public ULangProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    protected String getDefaultNamespace() {
        return "unicopiablockus";
    }

    @Override
    protected void generate(TranslationGenerator generator) {
        UBlockusBlocks.WOOD_SETS.forEach(woodset -> {
            generator.addTranslation(woodset.blocks()
                    .filter(b -> b != woodset.smallLogs() && b != woodset.smallHedge())
                    .toArray(Block[]::new));
            String id = woodset.id().replace(woodset.id().charAt(0), Character.toUpperCase(woodset.id().charAt(0)));
            generator.addTranslation(woodset.smallLogs().getTranslationKey(), "Small " + id + " Logs");
            generator.addTranslation(woodset.smallHedge().getTranslationKey(), "Small " + id + " Hedge");
        });
    }
}
