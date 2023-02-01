package com.sq3xd.magical_obsession.item;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.Game;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.client.resources.SplashManager;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.commands.GiveCommand;
import net.minecraft.util.TaskChainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.checkerframework.checker.units.qual.A;

public class ImmolationSwordItem extends SwordItem {
    public ImmolationSwordItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, damage, speed, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity player) {
        Entity entityGet = (Entity) entity;
        EntityType<?> entityType = entityGet.getType();
        String finalEntity = entityType.toString().replaceFirst("entity.", "").replace('.', '_');
        String finalText = "give " + player.getDisplayName().getString().toString() + " magical_obsession:soul_jar_" + finalEntity;
        Level level = player.getLevel();

        player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.getPosition(1), null, player.getServer().overworld(), 2, player.getDisplayName().getString(), player.getDisplayName(), player.getServer(), null), finalText);


        System.out.println(finalText);
        return super.hurtEnemy(stack, entity, player);
    }
}
