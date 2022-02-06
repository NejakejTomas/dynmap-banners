package cz.nejakejtomas.dynmapbanners;

import org.dynmap.markers.MarkerAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class Banners
{
	private static final String BANNER_FOLDER = "/banners";
	public static final Logger LOGGER = LoggerFactory.getLogger(Banners.class);
	
	public static String getMarkerIconId(String colorName) {
		return "banner." + colorName;
	}
	
	public static String getMarkerIconLabel(String colorName) {
		return "banner_" + colorName;
	}
	
	public static String getMarkerId(WorldPosition position) {
		return "banner_" + position.x() + "_" + position.y() + "_" + position.z();
	}

	private static String getIconPathByName(String dyeColor) {
		return BANNER_FOLDER + "/" + dyeColor + ".png";
	}
	
	public static void createIcons(MarkerAPI markerApi) {
		McAbstract.getDyeColorNames().forEach(dyeColor -> {
			try (InputStream iconStream = Banners.class.getResourceAsStream(getIconPathByName(dyeColor))) {
				if (markerApi.getMarkerIcon(getMarkerIconId(dyeColor)) == null) {
					markerApi.createMarkerIcon(getMarkerIconId(dyeColor), getMarkerIconLabel(dyeColor), iconStream);
				}
				else {
					markerApi.getMarkerIcon(getMarkerIconId(dyeColor)).setMarkerIconImage(iconStream);
				}
			}
			catch (IOException e) {
				LOGGER.error("Error opening banner icon resource", e);
				e.printStackTrace();
			}
		});
	}
}
