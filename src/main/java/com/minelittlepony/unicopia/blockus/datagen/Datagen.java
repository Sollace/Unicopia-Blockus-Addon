package com.minelittlepony.unicopia.blockus.datagen;

import com.minelittlepony.unicopia.blockus.Main;
import com.minelittlepony.unicopia.blockus.datagen.providers.UBlockusBlockTagProvider;
import com.minelittlepony.unicopia.blockus.datagen.providers.UBlockusItemTagProvider;
import com.minelittlepony.unicopia.blockus.datagen.providers.UBlockusLootTableProvider;
import com.minelittlepony.unicopia.blockus.datagen.providers.UBlockusModelProvider;
import com.minelittlepony.unicopia.blockus.datagen.providers.UBlockusRecipeProvider;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class Datagen implements DataGeneratorEntrypoint {
    @Override
    public String getEffectiveModId() {
        return Main.DEFAULT_NAMESPACE;
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        final FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(UBlockusRecipeProvider::new);

        UBlockusBlockTagProvider blockTags = pack.addProvider(UBlockusBlockTagProvider::new);
        pack.addProvider((output, registries) -> new UBlockusItemTagProvider(output, registries, blockTags));

        pack.addProvider(UBlockusLootTableProvider::new);
        //pack.addProvider(BlockusWorldgenProvider::new);
        pack.addProvider(UBlockusModelProvider::new);
    }
}
