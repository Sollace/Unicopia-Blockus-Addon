package com.minelittlepony.unicopia.blockus.block;

import com.brand.blockus.blocks.base.OrientableBlockBase;
import com.minelittlepony.unicopia.block.zap.ElectrifiedBlock;
import com.mojang.serialization.MapCodec;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

public class ZapOrientedBlock extends OrientableBlockBase implements ElectrifiedBlock {
    private static final MapCodec<OrientableBlockBase> CODEC = Block.createCodec(ZapOrientedBlock::new);

    public ZapOrientedBlock(Settings settings) {
        super(settings);
        this.getCodec();
    }

    @Override
    public MapCodec<OrientableBlockBase> getCodec() {
        return CODEC;
    }

    @Deprecated
    @Override
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        triggerLightning(state, world, pos);
    }
}
