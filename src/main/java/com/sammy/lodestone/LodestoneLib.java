package com.sammy.lodestone;

import com.sammy.lodestone.helpers.OrtTestItem;
import com.sammy.lodestone.setup.LodestoneParticles;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Rarity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.random.RandomGenerator;

public class LodestoneLib implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("LodestoneLib");
	public static final String MODID= "lodestone";
	public static final RandomGenerator RANDOM = RandomGenerator.getDefault();

	@Override
	public void onInitialize() {
		LOGGER.info("jesser where is the cocainer");
		LodestoneParticles.init();
		if(FabricLoaderImpl.INSTANCE.isDevelopmentEnvironment()) {
			Registry.register(Registry.ITEM, id("ort"), new OrtTestItem(new FabricItemSettings().rarity(Rarity.EPIC).group(CreativeModeTab.TAB_MISC)));
		}
	}
	public static ResourceLocation id(String path) {
		return new ResourceLocation(MODID, path);
	}
}
