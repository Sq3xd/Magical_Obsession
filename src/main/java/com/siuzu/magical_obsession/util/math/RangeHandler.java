package com.siuzu.magical_obsession.util.math;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class RangeHandler {
    public Vec2 locate2d(int x, int y, Level level, BlockPos pos, Block block) {
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if (level.getBlockState(pos).is(block))
                    return new Vec2(x, y);
            }
        }
        return new Vec2(pos.getX(), pos.getZ());
    }

    public boolean isIn2d(int x, int y, Level level, BlockPos pos, Block block) {
        boolean isFound = false;

        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if (level.getBlockState(pos).is(block)) {
                    isFound = true;
                }
            }
        }
        return isFound;
    }

    public Vec3 locate2dOffset(int x, int y, Level level, BlockPos pos, Block block) {
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if (level.getBlockState(pos).is(block))
                    return new Vec3(x + i, pos.getY(), y + j);
            }
        }
        return new Vec3(pos.getX(), pos.getY(), pos.getZ());
    }
}
