package com.sq3xd.magical_obsession.render.block;

import com.electronwill.nightconfig.core.io.Utils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import com.sq3xd.magical_obsession.block.tile.SpecialCauldronBlockEntity;
import com.sq3xd.magical_obsession.init.ModBlocks;
import com.sq3xd.magical_obsession.init.ModItems;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.IForgeVertexConsumer;

import java.awt.*;
import java.util.Random;

public class SpecialCauldronRenderer implements BlockEntityRenderer<SpecialCauldronBlockEntity> {
    private final BlockEntityRendererProvider.Context context;
    public SpecialCauldronRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(SpecialCauldronBlockEntity entity, float pticks, PoseStack stack, MultiBufferSource buffer, int coverlay, int plight) {
        final BlockRenderDispatcher block_renderer = this.context.getBlockRenderDispatcher(); // TODO make block save items and render it

        //ItemStack item = Minecraft.getInstance().player.getMainHandItem();
        final ItemRenderer item_renderer = this.context.getItemRenderer();

        BlockPos pos = entity.getBlockPos();
        BlockState state = entity.getBlockState();

        // Render Sphere
        if (state.getValue(BlockStateProperties.ENABLED).equals(true)){
            stack.pushPose();
            stack.translate(3.15d, 3.15d, 3.15d);
            stack.scale(15, 15, 15);
            stack.mulPose(Vector3f.YN.rotationDegrees(0));
            item_renderer.renderStatic(Minecraft.getInstance().player, ModItems.SPHERE_ITEM.get().getDefaultInstance(), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                    Minecraft.getInstance().level, coverlay, plight, plight);
            stack.popPose();
        }

        // Render Item inside

        if (state.getValue(HorizontalDirectionalBlock.FACING).equals(Direction.EAST) || state.getValue(HorizontalDirectionalBlock.FACING).equals(Direction.WEST)){
            stack.pushPose();
            stack.translate(0.5d, 0.55d, 0.5d);
            stack.scale(0.75f, 0.75f, 0.75f);
            stack.mulPose(Vector3f.YN.rotationDegrees(90));

            item_renderer.renderStatic(Minecraft.getInstance().player, entity.itemStackHandler.getStackInSlot(0), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                    Minecraft.getInstance().level, coverlay, plight, plight);
            stack.popPose();
        } else {
            stack.pushPose();
            stack.translate(0.5d, 0.55d, 0.5d);
            stack.scale(0.75f, 0.75f, 0.75f);

            item_renderer.renderStatic(Minecraft.getInstance().player, entity.itemStackHandler.getStackInSlot(0), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                    Minecraft.getInstance().level, coverlay, plight, plight);
            stack.popPose();
        }

        // Render Crystal

        if (!entity.itemStackHandler.getStackInSlot(0).is(ItemStack.EMPTY.getItem())) {
            stack.pushPose();
            stack.translate(0.65d, 1.75d, 0.65d);
            stack.scale(0.75f, 0.75f, 0.75f);
            stack.mulPose(Vector3f.YN.rotation(0));
            item_renderer.renderStatic(Minecraft.getInstance().player, ModItems.CRYSTAL_ITEM.get().getDefaultInstance(), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                    Minecraft.getInstance().level, coverlay, plight, plight);
            stack.popPose();
        }

        /*stack.pushPose();
        stack.translate(0f, 0f, 0f);
        stack.mulPose(Vector3f.YN.rotation(0));
        dispatcher.renderSingleBlock(Blocks.GLASS.defaultBlockState(), stack, buffer, coverlay, plight);
        stack.popPose();*/
    }
}
