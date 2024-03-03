package com.siuzu.magical_obsession.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.siuzu.magical_obsession.block.tile.MagicalCatallyzatorBlockEntity;
import com.siuzu.magical_obsession.block.tile.MagicalFieldShieldBlockEntity;
import com.siuzu.magical_obsession.init.ModBlocks;
import com.siuzu.magical_obsession.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class MagicalCatallyzatorRenderer implements BlockEntityRenderer<MagicalCatallyzatorBlockEntity> {
    private final BlockEntityRendererProvider.Context context;
    public MagicalCatallyzatorRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(MagicalCatallyzatorBlockEntity entity, float pticks, PoseStack stack, MultiBufferSource buffer, int coverlay, int plight) {
        final BlockRenderDispatcher block_renderer = this.context.getBlockRenderDispatcher();

        //ItemStack item = Minecraft.getInstance().player.getMainHandItem();
        final ItemRenderer item_renderer = this.context.getItemRenderer();

        BlockPos pos = entity.getBlockPos();
        BlockState state = entity.getBlockState();

        // Render Sphere

        if (entity.getSphere() >= 12000){
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
            stack.translate(0.5d, 1.29d + entity.getProgress() / 870f, 0.5d);
            stack.scale(0.7f, 0.7f, 0.7f);
            stack.mulPose(Vector3f.YN.rotationDegrees(90 - entity.getProgress() / 2));

            item_renderer.renderStatic(Minecraft.getInstance().player, entity.inventory.getStackInSlot(0), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                    Minecraft.getInstance().level, coverlay, plight, plight);
            stack.popPose();
        } else {
            stack.pushPose();
            stack.translate(0.5d, 1.29d + entity.getProgress() / 870f, 0.5d);
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

        // Render pentagram
        if (entity.getProgress() >= 1) {
            stack.pushPose();
            stack.translate(0.5d, 0.1d + entity.getProgress() / 1570f, 0.5d);
            stack.scale(11.5f, 0.00000001f, 11.57f);
            stack.mulPose(Vector3f.XP.rotationDegrees(90));
            stack.mulPose(Vector3f.ZN.rotationDegrees(90 + entity.getProgress() / 3.5f));

            item_renderer.renderStatic(Minecraft.getInstance().player, ModBlocks.MAGICAL_PENTAGRAM.get().asItem().getDefaultInstance(), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                    Minecraft.getInstance().level, coverlay, plight, plight);
            stack.popPose();
        }

        /*stack.pushPose();
        stack.translate(0f, 0f, 0f);
        stack.mulPose(Vector3f.YN.rotation(0));
        dispatcher.renderSingleBlock(Blocks.GLASS.defaultBlockState(), stack, buffer, coverlay, plight);
        stack.popPose();*/
    }

    @Override
    public boolean shouldRender(MagicalCatallyzatorBlockEntity entity, Vec3 pCameraPos) {
        return true;
    }

    @Override
    public boolean shouldRenderOffScreen(MagicalCatallyzatorBlockEntity entity) {
        return true;
    }

    @Override
    public int getViewDistance() {
        return 12;
    }
}
