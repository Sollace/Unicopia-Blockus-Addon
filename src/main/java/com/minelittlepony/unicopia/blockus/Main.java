package com.minelittlepony.unicopia.blockus;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.brand.blockus.itemgroups.BlockusItemGroups;

public class Main implements ModInitializer {
    public static final String DEFAULT_NAMESPACE = "unicopia";
    public static final Logger LOGGER = LogManager.getLogger();

    public static Identifier id(String name) {
        return new Identifier(DEFAULT_NAMESPACE, name);
    }

    @Override
    public void onInitialize() {
        UBlockusBlocks.bootstrap();
        ItemGroupEvents.modifyEntriesEvent(BlockusItemGroups.BLOCKUS_BUILDING_BLOCKS).register(event -> {
            UBlockusBlocks.WOOD_SETS.forEach(set -> {
                event.addAll(set.buildingBlocks().map(ItemConvertible::asItem).map(Item::getDefaultStack).toList());
            });
        });
        ItemGroupEvents.modifyEntriesEvent(BlockusItemGroups.BLOCKUS_NATURAL).register(event -> {
            UBlockusBlocks.WOOD_SETS.forEach(set -> {
                event.add(set.smallHedge());
            });
        });
    }

}
