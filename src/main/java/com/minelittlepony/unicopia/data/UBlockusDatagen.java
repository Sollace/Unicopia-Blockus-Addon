package com.minelittlepony.unicopia.data;

import com.minelittlepony.unicopia.UnicopiaBlockus;
import com.minelittlepony.unicopia.data.blockus.providers.UBlockusBlockTagProvider;
import com.minelittlepony.unicopia.data.blockus.providers.UBlockusItemTagProvider;
import com.minelittlepony.unicopia.data.blockus.providers.UBlockusLootTableProvider;
import com.minelittlepony.unicopia.data.blockus.providers.UBlockusModelProvider;
import com.minelittlepony.unicopia.data.blockus.providers.UBlockusRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class UBlockusDatagen implements DataGeneratorEntrypoint {
    @Override
    public String getEffectiveModId() {
        return UnicopiaBlockus.DEFAULT_NAMESPACE;
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
