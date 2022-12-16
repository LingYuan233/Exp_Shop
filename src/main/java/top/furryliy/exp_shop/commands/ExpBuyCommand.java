package top.furryliy.exp_shop.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import top.furryliy.exp_shop.config.Base;
import top.furryliy.exp_shop.config.Config;
import java.util.ArrayList;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ExpBuyCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher ) {
        dispatcher.register(literal("exp")
                .then(literal("view")
                        .then(literal("all").executes(ctx -> viewAll(Config.getList(), ctx.getSource().getPlayer())))
                        .then(literal("id").then(argument("id", IntegerArgumentType.integer()).executes(ctx -> viewLvl(IntegerArgumentType.getInteger(ctx, "id"), Config.getList(), ctx.getSource().getPlayer()))) )
                )
                .then(literal("buy")
                        .then(argument("id", IntegerArgumentType.integer()).executes(ctx -> buyItem(Config.getList(), ctx.getSource().getPlayer(), 1, IntegerArgumentType.getInteger(ctx, "id")))
                            .then(argument("count", IntegerArgumentType.integer()).executes(ctx -> buyItem(Config.getList(), ctx.getSource().getPlayer(), IntegerArgumentType.getInteger(ctx, "count"), IntegerArgumentType.getInteger(ctx, "id")))))

                )
        );
    }

    private static int viewLvl(int id, ArrayList<Base> list, ServerPlayerEntity player){
        for (Base base : list){
            if (base.getId() == id){
                String itemName = Registry.ITEM.get(new Identifier(base.getItem())).getDefaultStack().getName().getString();
                player.sendMessage(new TranslatableText("exp_shop.msg.found_id", id, itemName, base.getExp(), base.getCount()), false);
                return Command.SINGLE_SUCCESS;
            }
        }
        player.sendMessage(new TranslatableText("exp_shop.msg.id_not_found", id), false);
        return Command.SINGLE_SUCCESS;
    }

    private static int viewAll(ArrayList<Base> list, ServerPlayerEntity player){
        if (list.size() == 0){
            player.sendMessage(new TranslatableText("exp_shop.msg.view_all_none"), false);
            return Command.SINGLE_SUCCESS;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(new TranslatableText("exp_shop.msg.view_all_start").getString()).append("\n");
        for (Base base : list){
            String itemName = Registry.ITEM.get(new Identifier(base.getItem())).getDefaultStack().getName().getString();

            sb.append(base.getId()).append(new TranslatableText("exp_shop.msg.view_all_piece", itemName, base.getExp(), base.getCount()).getString())
                .append("\n");
        }
        String ls = sb.toString();
        player.sendMessage(new LiteralText(ls), false);
        return Command.SINGLE_SUCCESS;
    }

    private static int buyItem(ArrayList<Base> list, ServerPlayerEntity player, int count, int id){
        for (Base base : list){
            if (base.getId() == id){
                int requireExp = base.getExp() * count;
                int itemCount = count * base.getCount();
                int playerExp = player.totalExperience;
                if (requireExp > playerExp){
                    player.sendMessage(new TranslatableText("exp_shop.msg.buy_fail_exp"), false);
                    return Command.SINGLE_SUCCESS;
                }
                if (player.getInventory().getEmptySlot() == -1){
                    player.sendMessage(new TranslatableText("exp_shop.msg.buy_fail_empty"), false);
                    return Command.SINGLE_SUCCESS;
                }
                if (itemCount > 64 || itemCount == 0){
                    player.sendMessage(new TranslatableText("exp_shop.msg.buy_fail_count"), false);
                    return Command.SINGLE_SUCCESS;
                }
                else {
                    ItemStack stack = new ItemStack(Registry.ITEM.get(new Identifier(base.getItem())), count * base.getCount());
                    player.getInventory().insertStack(stack);
                    player.addExperience(-requireExp);
                    player.sendMessage(new TranslatableText("exp_shop.msg.buy_success"), false);
                }
            }
        }
        return Command.SINGLE_SUCCESS;
    }


}
