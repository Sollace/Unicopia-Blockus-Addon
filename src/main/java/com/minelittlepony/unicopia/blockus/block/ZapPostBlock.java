package com.minelittlepony.unicopia.blockus.block;

import com.brand.blockus.blocks.base.PostBlock;
import com.minelittlepony.unicopia.block.zap.ElectrifiedBlock;
import com.mojang.serialization.MapCodec;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

public class ZapPostBlock extends PostBlock implements ElectrifiedBlock {
    private static final MapCodec<PostBlock> CODEC = Block.createCodec(ZapPostBlock::new);

    public ZapPostBlock(Settings settings) {
        super(settings);
    }

    @Override
    public MapCodec<PostBlock> getCodec() {
        return CODEC;
    }

    @Deprecated
    @Override
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        triggerLightning(state, world, pos);
    }
}
