package com.siuzu.magical_obsession.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.siuzu.magical_obsession.block.tile.SpecialCauldronBlockEntity;
import com.siuzu.magical_obsession.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SpecialCauldronRenderer implements BlockEntityRenderer<SpecialCauldronBlockEntity> {
    private final BlockEntityRendererProvider.Context context;
    public SpecialCauldronRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(SpecialCauldronBlockEntity entity, float pticks, PoseStack stack, MultiBufferSource buffer, int coverlay, int plight) {
        final BlockRenderDispatcher block_renderer = this.context.getBlockRenderDispatcher();

        final ItemRenderer item_renderer = this.context.getItemRenderer();

        BlockPos pos = entity.getBlockPos();
        BlockState state = entity.getBlockState();

        // Render Item inside

        int degress;

        switch (state.getValue(HorizontalDirectionalBlock.FACING)) {
            case NORTH -> degress = 0;
            case SOUTH -> degress = 180;
            case EAST -> degress = 270;
            case WEST -> degress = 90;
            default -> degress = 90;
        }

        stack.pushPose();
        stack.translate(0.5d, 0.3d, 0.5d);
        stack.scale(0.5f, 0.5f, 0.5f);

        if (degress == 270) {
            stack.mulPose(Vector3f.YN.rotationDegrees(degress + 180));
            stack.mulPose(Vector3f.XN.rotationDegrees(270));
        } else if (degress == 180) {
            stack.mulPose(Vector3f.YN.rotationDegrees(degress));
            stack.mulPose(Vector3f.XN.rotationDegrees(270));
        } else if (degress == 90) {
            stack.mulPose(Vector3f.YN.rotationDegrees(degress+180));
            stack.mulPose(Vector3f.XN.rotationDegrees(270));
        } else {
            stack.mulPose(Vector3f.YN.rotationDegrees(degress));
            stack.mulPose(Vector3f.XN.rotationDegrees(270));
        }

        //stack.mulPose(Vector3f.ZN.rotationDegrees(degress));
        //stack.mulPose(Vector3f.YN.rotationDegrees(degress + entity.getProgress() / 2));
        //stack.mulPose(Vector3f.XN.rotationDegrees(degress - entity.getProgress() / 2));

        item_renderer.renderStatic(Minecraft.getInstance().player, entity.inventory.getStackInSlot(0), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                Minecraft.getInstance().level, coverlay, plight, plight);
        stack.popPose();
    }
}
