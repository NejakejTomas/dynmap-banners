package cz.nejakejtomas.dynmapbanners;

import net.fabricmc.api.ModInitializer;
import org.dynmap.DynmapCommonAPIListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynmapBanners implements ModInitializer
{
	private static final Logger LOGGER = LoggerFactory.getLogger("dynmap-banners");
	private static DynmapBanners instance;
	private BannerApi bannerApi;
	
	public static DynmapBanners getInstance() {
		return instance;
	}
	
	@Override
	public void onInitialize() {
		instance = this;
		
		bannerApi = new BannerApi();
		
		DynmapCommonAPIListener.register(bannerApi);
		
		LOGGER.info("Initialized");
	}
	
	public BannerApi getBannerApi() {
		return bannerApi;
	}
}
