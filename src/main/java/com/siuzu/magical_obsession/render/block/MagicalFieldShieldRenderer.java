package com.siuzu.magical_obsession.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.siuzu.magical_obsession.block.tile.MagicalFieldShieldBlockEntity;
import com.siuzu.magical_obsession.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.opengl.GL11;

public class MagicalFieldShieldRenderer implements BlockEntityRenderer<MagicalFieldShieldBlockEntity> {
    private final BlockEntityRendererProvider.Context context;
    public MagicalFieldShieldRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(MagicalFieldShieldBlockEntity entity, float pticks, PoseStack stack, MultiBufferSource buffer, int coverlay, int plight) {
        final BlockRenderDispatcher block_renderer = this.context.getBlockRenderDispatcher();

        //ItemStack item = Minecraft.getInstance().player.getMainHandItem();
        final ItemRenderer item_renderer = this.context.getItemRenderer();

        BlockPos pos = entity.getBlockPos();
        BlockState state = entity.getBlockState();

        // Render Crystal
//        stack.pushPose();
//        stack.translate(0.65d, 2d, 0.65d);
//        stack.scale(0.75f, 0.75f, 0.75f);
//        stack.mulPose(Vector3f.YN.rotation(0));
//        item_renderer.renderStatic(Minecraft.getInstance().player, ModItems.CRYSTAL_ITEM.get().getDefaultInstance(), ItemTransforms.TransformType.FIXED, false, stack, buffer,
//                Minecraft.getInstance().level, coverlay, plight, plight);
//        stack.popPose();

        // Render Lines


        stack.pushPose();

        MultiBufferSource.BufferSource buffer1 = Minecraft.getInstance().renderBuffers().bufferSource();
        VertexConsumer builder = buffer1.getBuffer(RenderType.LINES);

        stack.translate(0d, 0d, 0d);

        Matrix4f mat = stack.last().pose();
        Matrix3f mat1 = stack.last().normal();
        builder.vertex(mat, 0.5f, 0.5f, 0.5f).color(201, 135, 227, 200).normal(mat1, 0.5f, 0.5f, 0.5f).endVertex();
        builder.vertex(mat, entity.getCords(0) + 0.5f, 0.75f, entity.getCords(2) + 0.5f).color(255, 255, 255, 200).normal(mat1, -entity.getCords(0) + 0.5f, 0.75f, -entity.getCords(2) + 0.5f).endVertex();

        stack.popPose();

        buffer1.endBatch();
    }


    @Override
    public boolean shouldRender(MagicalFieldShieldBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }

    @Override
    public boolean shouldRenderOffScreen(MagicalFieldShieldBlockEntity pBlockEntity) {
        return true;
    }

    @Override
    public int getViewDistance() {
        return 12;
    }
}
