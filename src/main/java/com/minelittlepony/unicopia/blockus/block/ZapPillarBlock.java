package com.minelittlepony.unicopia.blockus.block;

import com.minelittlepony.unicopia.block.zap.ElectrifiedBlock;
import com.mojang.serialization.MapCodec;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

public class ZapPillarBlock extends PillarBlock implements ElectrifiedBlock {
    private static final MapCodec<PillarBlock> CODEC = Block.createCodec(ZapPillarBlock::new);

    public ZapPillarBlock(Settings settings) {
        super(settings);
    }

    @Override
    public MapCodec<PillarBlock> getCodec() {
        return CODEC;
    }

    @Deprecated
    @Override
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        triggerLightning(state, world, pos);
    }
}
