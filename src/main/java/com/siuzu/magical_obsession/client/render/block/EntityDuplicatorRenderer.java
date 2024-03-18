package com.siuzu.magical_obsession.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.siuzu.magical_obsession.block.tile.EntityDuplicatorBlockEntity;
import com.siuzu.magical_obsession.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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

        int degress;

        switch (state.getValue(HorizontalDirectionalBlock.FACING)) {
            case NORTH -> degress = 0;
            case SOUTH -> degress = 180;
            case EAST -> degress = 270;
            case WEST -> degress = 90;
            default -> degress = 90;
        }

        stack.pushPose();
        stack.translate(0.5d, 1.2d, 0.5d);
        stack.scale(0.5f, 0.5f, 0.5f);

        if (degress == 270) {
            stack.mulPose(Vector3f.YN.rotationDegrees(degress + 270));
        } else if (degress == 180) {
            stack.mulPose(Vector3f.YN.rotationDegrees(degress + 180));
        } else if (degress == 90) {
            stack.mulPose(Vector3f.YN.rotationDegrees(degress + 90));
        } else {
            stack.mulPose(Vector3f.YN.rotationDegrees(degress));
        }

        stack.mulPose(Vector3f.YN.rotationDegrees(degress + entity.getProgress() / 2));

        item_renderer.renderStatic(Minecraft.getInstance().player, entity.inventory.getStackInSlot(0), ItemTransforms.TransformType.FIXED, false, stack, buffer,
                Minecraft.getInstance().level, coverlay, plight, plight);
        stack.popPose();

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

        stack.pushPose();
        stack.translate(entity.getBlockPos().getX() + 0.5, entity.getBlockPos().getY() + 0.5, entity.getBlockPos().getZ() + 0.5); // Adjust position as needed

        // Define vertices for the cube (assuming unit size)
        float halfWidth = 0.5f;
        Minecraft instance = Minecraft.getInstance();
        TextureAtlasSprite sprite = instance.getBlockRenderer().getBlockModelShaper().getParticleIcon(entity.getBlockState());
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.solid());
        vertexConsumer.vertex(stack.last().pose(), -halfWidth, -halfWidth, -halfWidth).uv(sprite.getU1(), sprite.getV1());
        // ... (add vertices for all 6 faces of the cube)

        stack.popPose();
        // Render Particle

        /*if (state.getValue(HorizontalDirectionalBlock.FACING).equals(Direction.EAST) || state.getValue(HorizontalDirectionalBlock.FACING).equals(Direction.WEST)) {
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
        }*/
    }
}
