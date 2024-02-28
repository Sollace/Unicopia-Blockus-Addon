package com.minelittlepony.unicopia;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.brand.blockus.content.types.BSSTypes;
import com.brand.blockus.content.types.TimberFrameTypes;
import com.brand.blockus.itemgroups.BlockusItemGroups;

public class UnicopiaBlockus implements ModInitializer {
    public static final String DEFAULT_NAMESPACE = "unicopia";
    public static final Logger LOGGER = LogManager.getLogger();

    public static boolean registering = false;

    public static Identifier id(String name) {
        return new Identifier(DEFAULT_NAMESPACE, name);
    }

    @Override
    public void onInitialize() {
        registering = true;
        UBlockusBlocks.bootstrap();
        registering = false;

        ItemGroupEvents.modifyEntriesEvent(BlockusItemGroups.BLOCKUS_BUILDING_BLOCKS).register(event -> {
            event.addAll(getStacks(UBlockusBlocks.PALM_MOSAIC));
            event.addAll(getStacks(UBlockusBlocks.MOSSY_PALM_PLANKS));
            event.add(UBlockusBlocks.HERRINGBONE_PALM_PLANKS);
            event.add(UBlockusBlocks.PALM_SMALL_LOGS);
            event.addAll(getStacks(UBlockusBlocks.PALM_TIMBER_FRAME));
        });
        ItemGroupEvents.modifyEntriesEvent(BlockusItemGroups.BLOCKUS_NATURAL).register(event -> {
            event.add(UBlockusBlocks.PALM_SMALL_HEDGE);
        });
    }

    static List<ItemStack> getStacks(BSSTypes set) {
        return List.of(
            set.block.asItem().getDefaultStack(),
            set.slab.asItem().getDefaultStack(),
            set.stairs.asItem().getDefaultStack()
        );
    }

    static List<ItemStack> getStacks(TimberFrameTypes set) {
        return List.of(
            set.block.asItem().getDefaultStack(),
            set.cross.asItem().getDefaultStack(),
            set.diagonal.asItem().getDefaultStack()
        );
    }
}
