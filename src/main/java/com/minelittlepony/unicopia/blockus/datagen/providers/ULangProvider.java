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
                    .filter(b -> b != woodset.smallLogs())
                    .toArray(Block[]::new));
            generator.addTranslation(woodset.smallLogs().getTranslationKey(), "Small " + formatToTitleCase(woodset.id()) + " Logs");
        });
        generator.addTranslation(UBlockusBlocks.GOLDEN_OAK_SMALL_LOGS.getTranslationKey(), "Small Golden Oak Logs");
        UBlockusBlocks.PLANT_SETS.forEach(plantset -> {
            String id = formatToTitleCase(plantset.id().replace("green_apple", "granny_smith"));
            generator.addTranslation(plantset.flowerPot().getTranslationKey(), "Potted Large " + id);
            generator.addTranslation(plantset.smallHedge().getTranslationKey(), "Small " + id + " Hedge");
        });
    }
}
