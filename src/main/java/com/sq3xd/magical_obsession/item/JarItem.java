package com.sq3xd.magical_obsession.item;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class JarItem extends Item {

    private final EntityType<?> entityType;

    public JarItem(Properties properties, EntityType<?> entityType) {
        super(properties);
        this.entityType = entityType;
    }

    @Override
    public Component getName(ItemStack item) {
        return Component.translatable("item.magical_obsession.soul_jar");
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();

        Entity entity = entityType.create(level);
        entity.moveTo(context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ());
        level.addFreshEntity(entity);
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        String name = EntityType.getKey(entityType).toString();
        if(Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.magical_obsession.soul_jar"));
            tooltipComponents.add(Component.literal("§9§o" + name));
        } else {
            tooltipComponents.add(Component.translatable("tooltip.magical_obsession.shift"));
        }
    }
}