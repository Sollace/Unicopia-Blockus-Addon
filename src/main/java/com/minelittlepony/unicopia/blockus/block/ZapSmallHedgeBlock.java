package com.minelittlepony.unicopia.blockus.block;

import com.brand.blockus.blocks.base.SmallHedgeBlock;
import com.minelittlepony.unicopia.block.zap.ElectrifiedBlock;
import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ZapSmallHedgeBlock extends SmallHedgeBlock implements ElectrifiedBlock {
    private static final MapCodec<SmallHedgeBlock> CODEC = Block.createCodec(ZapSmallHedgeBlock::new);

    public ZapSmallHedgeBlock(Settings settings) {
        super(settings);
    }

    @Override
    public MapCodec<SmallHedgeBlock> getCodec() {
        return CODEC;
    }

    @Deprecated
    @Override
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        triggerLightning(state, world, pos);
    }

}
