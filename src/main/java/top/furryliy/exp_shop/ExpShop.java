package top.furryliy.exp_shop;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpShop implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Exp Shop");


	@Override
	public void onInitialize() {
		LOGGER.info("Hello World!");
		FileUtil.createConfigDirAndFillDefaultConfigIfItNotExits();
	}

}
