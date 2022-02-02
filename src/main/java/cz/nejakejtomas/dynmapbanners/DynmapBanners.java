package cz.nejakejtomas.dynmapbanners;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import org.dynmap.DynmapCommonAPIListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynmapBanners implements ModInitializer
{
	private static final Logger LOGGER = LoggerFactory.getLogger(DynmapBanners.class);
	private static DynmapBanners instance;
	private BannerApi bannerApi;
	
	public static DynmapBanners getInstance() {
		return instance;
	}
	
	@Override
	public void onInitialize() {
		ServerLifecycleEvents.SERVER_STARTED.register(this::started);
	}
	
	private void started(MinecraftServer minecraftServer) {
		instance = this;
		bannerApi = new BannerApi();
		
		LOGGER.info("Dynmap banners waiting for dynmap");
		
		DynmapCommonAPIListener.register(bannerApi);
	}
	
	public BannerApi getBannerApi() {
		return bannerApi;
	}
}
