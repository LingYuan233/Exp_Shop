package top.furryliy.exp_shop;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import top.furryliy.exp_shop.commands.ExpBuyCommand;

public class Client implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CommandRegistrationCallback.EVENT.register(((dispatcher, dedicated) -> {
            ExpBuyCommand.register(dispatcher);
        }));
    }
}
