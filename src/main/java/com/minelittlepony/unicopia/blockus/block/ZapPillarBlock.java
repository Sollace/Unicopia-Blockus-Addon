package com.minelittlepony.unicopia.blockus.block;

import com.minelittlepony.unicopia.block.zap.ElectrifiedBlock;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

public class ZapPillarBlock extends PillarBlock implements ElectrifiedBlock {
    public ZapPillarBlock(Settings settings) {
        super(settings);
    }

    @Deprecated
    @Override
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        triggerLightning(state, world, pos);
    }
}
