package com.sq3xd.magical_obsession.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.sq3xd.magical_obsession.block.tile.SpecialCauldronBlockEntity;
import com.sq3xd.magical_obsession.init.ModItems;
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

        //ItemStack item = Minecraft.getInstance().player.getMainHandItem();
        final ItemRenderer item_renderer = this.context.getItemRenderer();

        BlockPos pos = entity.getBlockPos();
        BlockState state = entity.getBlockState();

        // Render Sphere

        if (entity.getSphere() >= 3200){
            stack.pushPose();
            stack.translate(3.125d, 3.125d, 3.125d);
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
            stack.mulPose(Vector3f.YN.rotationDegrees(90 - entity.getProgress() / 2));

            item_renderer.renderStatic(Minecraft.getInstance().player, entity.itemStackHandler.getStackInSlot(0), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                    Minecraft.getInstance().level, coverlay, plight, plight);
            stack.popPose();
        } else {
            stack.pushPose();
            stack.translate(0.5d, 0.55d, 0.5d);
            stack.scale(0.75f, 0.75f, 0.75f);
            stack.mulPose(Vector3f.YN.rotationDegrees(0 - entity.getProgress() / 2));

            item_renderer.renderStatic(Minecraft.getInstance().player, entity.itemStackHandler.getStackInSlot(0), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                    Minecraft.getInstance().level, coverlay, plight, plight);
            stack.popPose();
        }

        // Render Crystal

        if (!entity.itemStackHandler.getStackInSlot(0).is(ItemStack.EMPTY.getItem())) {
            stack.pushPose();
            stack.translate(0.65d, 2.07d + entity.getProgress() / 370d, 0.65d);
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
