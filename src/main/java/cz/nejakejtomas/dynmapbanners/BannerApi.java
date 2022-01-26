package cz.nejakejtomas.dynmapbanners;

import org.dynmap.DynmapCommonAPI;
import org.dynmap.DynmapCommonAPIListener;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerIcon;
import org.dynmap.markers.MarkerSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BannerApi extends DynmapCommonAPIListener
{
	private static final Logger LOGGER = LoggerFactory.getLogger("dynmap-banners");
	private MarkerAPI markerApi;
	private MarkerSet markerSet;
	
	@Override
	public void apiEnabled(DynmapCommonAPI api) {
		markerApi = api.getMarkerAPI();
		
		if (markerApi == null) {
			LOGGER.error("Dynmap base mod required");
			
			return;
		}
		
		markerApi.getMarkerSet("banners");
		markerSet = markerApi.getMarkerSet("banners");
		
		if (markerSet == null) {
			markerSet = markerApi.createMarkerSet("banners", "Banners", null, true);
		}
		
		markerSet.setMinZoom(-1);
		
		Banners.createIcons(markerApi);
	}
	
	private static String patchWorldName(String worldName) {
		return switch (worldName)
						{
							case "overworld" -> "world";
							case "the_nether" -> "DIM-1";
							case "the_end" -> "DIM1";
							default -> worldName;
						};
	}
	
	public void addBanner(String label, String worldName, WorldPosition position, MarkerIcon icon) {
		final String id = Banners.getMarkerId(position);
		
		
		final MarkerSet markerSet = this.markerSet;
		if (markerSet == null) return;
		final Marker marker = markerSet.createMarker(
						id,
						label,
						patchWorldName(worldName),
						(double)position.x() + 0.5,
						(double)position.y() + 0.5,
						(double)position.z() + 0.5,
						icon,
						true);
	}
	
	public void removeBanner(WorldPosition position) {
		final MarkerSet markerSet = this.markerSet;
		if (markerSet == null) return;
		final Marker marker = markerSet.findMarker(Banners.getMarkerId(position));
		
		if (marker == null) return;
		marker.deleteMarker();
	}
	
	public MarkerIcon getIcon(String colorName) {
		final MarkerAPI markerApi = this.markerApi;
		
		return markerApi.getMarkerIcon(Banners.getMarkerIconId(colorName));
	}
}
