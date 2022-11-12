package com.sq3xd.magical_obsession.render.block;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import com.sq3xd.magical_obsession.block.tile.MagicalFieldShieldBlockEntity;
import com.sq3xd.magical_obsession.block.tile.SpecialCauldronBlockEntity;
import com.sq3xd.magical_obsession.init.ModBlocks;
import com.sq3xd.magical_obsession.init.ModItems;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;

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

        stack.pushPose();
        stack.translate(0.65d, 2.07d, 0.65d);
        stack.scale(0.75f, 0.75f, 0.75f);
        stack.mulPose(Vector3f.YN.rotation(0));
        item_renderer.renderStatic(Minecraft.getInstance().player, ModItems.CRYSTAL_ITEM.get().getDefaultInstance(), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                Minecraft.getInstance().level, coverlay, plight, plight);
        stack.popPose();

        VertexConsumer builder = buffer.getBuffer(RenderType.LINES); //SpellRender.QUADS is a personal RenderType, of VertexFormat POSITION_COLOR.
        stack.pushPose();

        stack.translate(0d, 0d, 0d);

        //stack.mulPose(Vector3f.ZERO.rotationDegrees(90));
        Matrix4f mat = stack.last().pose();
        Matrix3f mat1 = stack.last().normal();
        builder.vertex(mat, 0.5f, 0.5f, 0.5f).color(201, 135, 227, 200).normal(mat1, 0.5f, 0.5f, 0.5f).endVertex();
        builder.vertex(mat, -entity.getCords(0) + 0.5f, 0.75f, -entity.getCords(2) + 0.5f).color(255, 255, 255, 200)
                .normal(mat1, -entity.getCords(0) + 0.5f, 0.75f, -entity.getCords(2) + 0.5f).endVertex();
        //builder.vertex(mat, 4.5f, 0.75f, 4.5f).color(255, 255, 255, 200).normal(mat1, 4.5f, 0.75f, 4.5f).endVertex();
        stack.popPose();

        buffer.getBuffer(RenderType.LINES).endVertex();
    }
}
