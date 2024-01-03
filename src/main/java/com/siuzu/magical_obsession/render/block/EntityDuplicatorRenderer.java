package com.siuzu.magical_obsession.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.siuzu.magical_obsession.block.tile.EntityDuplicatorBlockEntity;
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

public class EntityDuplicatorRenderer implements BlockEntityRenderer<EntityDuplicatorBlockEntity> {
    private final BlockEntityRendererProvider.Context context;
    public EntityDuplicatorRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(EntityDuplicatorBlockEntity entity, float pticks, PoseStack stack, MultiBufferSource buffer, int coverlay, int plight) {
        final BlockRenderDispatcher block_renderer = this.context.getBlockRenderDispatcher();

        //ItemStack item = Minecraft.getInstance().player.getMainHandItem();
        final ItemRenderer item_renderer = this.context.getItemRenderer();

        BlockPos pos = entity.getBlockPos();
        BlockState state = entity.getBlockState();

        // Render Item inside

        if (state.getValue(HorizontalDirectionalBlock.FACING).equals(Direction.EAST) || state.getValue(HorizontalDirectionalBlock.FACING).equals(Direction.WEST)){
            stack.pushPose();
            stack.translate(0.5d, 1.25d + entity.getProgress() / 870f, 0.5d);
            stack.scale(0.7f, 0.7f, 0.7f);
            stack.mulPose(Vector3f.YN.rotationDegrees(90 - entity.getProgress() / 2));

            item_renderer.renderStatic(Minecraft.getInstance().player, entity.inventory.getStackInSlot(0), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                    Minecraft.getInstance().level, coverlay, plight, plight);
            stack.popPose();
        } else {
            stack.pushPose();
            stack.translate(0.5d, 1.25d + entity.getProgress() / 870f, 0.5d);
            stack.scale(0.7f, 0.7f, 0.7f);
            stack.mulPose(Vector3f.YN.rotationDegrees(0 - entity.getProgress() / 2));

            item_renderer.renderStatic(Minecraft.getInstance().player, entity.inventory.getStackInSlot(0), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                    Minecraft.getInstance().level, coverlay, plight, plight);
            stack.popPose();
        }

        // Render Crystal

//        if (!entity.inventory.getStackInSlot(0).is(ItemStack.EMPTY.getItem())) {
//            stack.pushPose();
//            stack.translate(0.65d, 2.39d + entity.getProgress() / 510d, 0.65d);
//            stack.scale(0.75f, 0.75f, 0.75f);
//            stack.mulPose(Vector3f.YN.rotation(0));
//            item_renderer.renderStatic(Minecraft.getInstance().player, ModItems.CRYSTAL_ITEM.get().getDefaultInstance(), ItemTransforms.TransformType.FIXED, false, stack, buffer,
//                    Minecraft.getInstance().level, coverlay, plight, plight);
//            stack.popPose();
//        }

        // Render Sphere


        // Render Particle

        if (state.getValue(HorizontalDirectionalBlock.FACING).equals(Direction.EAST) || state.getValue(HorizontalDirectionalBlock.FACING).equals(Direction.WEST)) {
            stack.pushPose();
            stack.translate(0.5d, 0.52d + entity.getProgress() / 570f, 0.8d);
            stack.scale(0.75f, 1.15f, 0.75f);

            item_renderer.renderStatic(Minecraft.getInstance().player, ModItems.ENTITY_DUPLICATOR_PARTICLE.get().getDefaultInstance(), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                    Minecraft.getInstance().level, coverlay, plight, plight);
            stack.popPose();

            stack.pushPose();
            stack.translate(0.5d, 0.52d + entity.getProgress() / 570f, 0.2d);
            stack.scale(0.75f, 1.11f, 0.75f);

            item_renderer.renderStatic(Minecraft.getInstance().player, ModItems.ENTITY_DUPLICATOR_PARTICLE.get().getDefaultInstance(), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                    Minecraft.getInstance().level, coverlay, plight, plight);
            stack.popPose();
        } else {
            stack.pushPose();
            stack.translate(0.8d, 0.52d + entity.getProgress() / 570f, 0.5d);
            stack.scale(0.75f, 1.15f, 0.75f);

            item_renderer.renderStatic(Minecraft.getInstance().player, ModItems.ENTITY_DUPLICATOR_PARTICLE.get().getDefaultInstance(), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                    Minecraft.getInstance().level, coverlay, plight, plight);
            stack.popPose();

            stack.pushPose();
            stack.translate(0.2d, 0.52d + entity.getProgress() / 570f, 0.5d);
            stack.scale(0.75f, 1.11f, 0.75f);

            item_renderer.renderStatic(Minecraft.getInstance().player, ModItems.ENTITY_DUPLICATOR_PARTICLE.get().getDefaultInstance(), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                    Minecraft.getInstance().level, coverlay, plight, plight);
            stack.popPose();
        }
    }
}
