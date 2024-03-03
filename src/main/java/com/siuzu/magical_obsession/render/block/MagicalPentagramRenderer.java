package com.siuzu.magical_obsession.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.siuzu.magical_obsession.block.tile.MagicalPentagramBlockEntity;
import com.siuzu.magical_obsession.init.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;

public class MagicalPentagramRenderer implements BlockEntityRenderer<MagicalPentagramBlockEntity> {
    private final BlockEntityRendererProvider.Context context;
    public MagicalPentagramRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(MagicalPentagramBlockEntity entity, float pticks, PoseStack stack, MultiBufferSource buffer, int coverlay, int plight) {
        final BlockRenderDispatcher block_renderer = this.context.getBlockRenderDispatcher();

        //ItemStack item = Minecraft.getInstance().player.getMainHandItem();
        final ItemRenderer item_renderer = this.context.getItemRenderer();

        BlockPos pos = entity.getBlockPos();
        BlockState state = entity.getBlockState();

        if (state.getValue(HorizontalDirectionalBlock.FACING).equals(Direction.NORTH)) {
            stack.pushPose();
            stack.translate(0.5d, 0.35d, 0.5d);
            stack.scale(0.59f, 0.59f, 0.59f);
            stack.mulPose(Vector3f.YN.rotationDegrees(180 - entity.getProgress() / 2));
            stack.mulPose(Vector3f.XN.rotationDegrees(90));

            item_renderer.renderStatic(Minecraft.getInstance().player, entity.itemStackHandler.getStackInSlot(0), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                    Minecraft.getInstance().level, coverlay, plight, plight);

            stack.popPose();

            stack.pushPose();
            stack.translate(0.5d, 0.1d + entity.getProgress() / 2100f, 0.5d);
            stack.scale(1.19f, 0.00000001f, 1.19f);
            stack.mulPose(Vector3f.XP.rotationDegrees(90));
            stack.mulPose(Vector3f.ZN.rotationDegrees(0 - entity.getProgress() / 3.15f));

            item_renderer.renderStatic(Minecraft.getInstance().player, ModBlocks.MAGICAL_PENTAGRAM.get().asItem().getDefaultInstance(), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                    Minecraft.getInstance().level, coverlay, plight, plight);
            stack.popPose();
        } else if (state.getValue(HorizontalDirectionalBlock.FACING).equals(Direction.SOUTH)) {
            stack.pushPose();
            stack.translate(0.5d, 0.35d, 0.5d);
            stack.scale(0.59f, 0.59f, 0.59f);
            stack.mulPose(Vector3f.YN.rotationDegrees(0 - entity.getProgress() / 2));
            stack.mulPose(Vector3f.XN.rotationDegrees(90));

            item_renderer.renderStatic(Minecraft.getInstance().player, entity.itemStackHandler.getStackInSlot(0), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                    Minecraft.getInstance().level, coverlay, plight, plight);
            stack.popPose();

            stack.pushPose();
            stack.translate(0.5d, 0.1d + entity.getProgress() / 2100f, 0.5d);
            stack.scale(1.19f, 0.00000001f, 1.19f);
            stack.mulPose(Vector3f.XP.rotationDegrees(90));
            stack.mulPose(Vector3f.ZN.rotationDegrees(180 - entity.getProgress() / 3.15f));

            item_renderer.renderStatic(Minecraft.getInstance().player, ModBlocks.MAGICAL_PENTAGRAM.get().asItem().getDefaultInstance(), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                    Minecraft.getInstance().level, coverlay, plight, plight);
            stack.popPose();
        } else if (state.getValue(HorizontalDirectionalBlock.FACING).equals(Direction.EAST)) {
            stack.pushPose();
            stack.translate(0.5d, 0.35d, 0.5d);
            stack.scale(0.59f, 0.59f, 0.59f);
            stack.mulPose(Vector3f.YN.rotationDegrees(270 - entity.getProgress() / 2));
            stack.mulPose(Vector3f.XN.rotationDegrees(90));

            item_renderer.renderStatic(Minecraft.getInstance().player, entity.itemStackHandler.getStackInSlot(0), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                    Minecraft.getInstance().level, coverlay, plight, plight);
            stack.popPose();

            stack.pushPose();
            stack.translate(0.5d, 0.1d + entity.getProgress() / 2100f, 0.5d);
            stack.scale(1.19f, 0.00000001f, 1.19f);
            stack.mulPose(Vector3f.XP.rotationDegrees(90));
            stack.mulPose(Vector3f.ZN.rotationDegrees(270 - entity.getProgress() / 3.15f));

            item_renderer.renderStatic(Minecraft.getInstance().player, ModBlocks.MAGICAL_PENTAGRAM.get().asItem().getDefaultInstance(), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                    Minecraft.getInstance().level, coverlay, plight, plight);
            stack.popPose();
        } else if (state.getValue(HorizontalDirectionalBlock.FACING).equals(Direction.WEST)) {
            stack.pushPose();
            stack.translate(0.5d, 0.35d, 0.5d);
            stack.scale(0.59f, 0.59f, 0.59f);
            stack.mulPose(Vector3f.YN.rotationDegrees(90 - entity.getProgress() / 2));
            stack.mulPose(Vector3f.XN.rotationDegrees(90));

            item_renderer.renderStatic(Minecraft.getInstance().player, entity.itemStackHandler.getStackInSlot(0), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                    Minecraft.getInstance().level, coverlay, plight, plight);
            stack.popPose();

            stack.pushPose();
            stack.translate(0.5d, 0.1d + entity.getProgress() / 2100f, 0.5d);
            stack.scale(1.19f, 0.00000001f, 1.19f);
            stack.mulPose(Vector3f.XP.rotationDegrees(90));
            stack.mulPose(Vector3f.ZN.rotationDegrees(90 - entity.getProgress() / 3.15f));

            item_renderer.renderStatic(Minecraft.getInstance().player, ModBlocks.MAGICAL_PENTAGRAM.get().asItem().getDefaultInstance(), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                    Minecraft.getInstance().level, coverlay, plight, plight);
            stack.popPose();
        }

        // Render Lines

//        stack.pushPose();
//
//        MultiBufferSource.BufferSource buffer1 = Minecraft.getInstance().renderBuffers().bufferSource();
//        VertexConsumer builder = buffer1.getBuffer(RenderType.LINES);
//
//        stack.translate(0d, 0d, 0d);
//
//        Matrix4f mat = stack.last().pose();
//        Matrix3f mat1 = stack.last().normal();
//        builder.vertex(mat, 0.5f, 0.5f, 0.5f).color(201, 135, 227, 200).normal(mat1, 0.5f, 0.5f, 0.5f).endVertex();
//        builder.vertex(mat, -entity.getCords(0) + 0.5f, 0.75f, -entity.getCords(2) + 0.5f).color(255, 255, 255, 200)
//                .normal(mat1, -entity.getCords(0) + 0.5f, 0.75f, -entity.getCords(2) + 0.5f).endVertex();
//        stack.popPose();
//
//        buffer1.endBatch();
    }
}
