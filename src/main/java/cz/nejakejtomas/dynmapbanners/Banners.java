package cz.nejakejtomas.dynmapbanners;

import org.dynmap.markers.MarkerAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;

public class Banners
{
	private static final String BANNER_FOLDER = "banners";
	public static final Logger LOGGER = LoggerFactory.getLogger("dynmap-banners");
	
	public static String getMarkerIconId(String colorName) {
		return "banner." + colorName;
	}
	
	public static String getMarkerIconLabel(String colorName) {
		return "banner_" + colorName;
	}
	
	public static String getMarkerId(WorldPosition position) {
		return "banner_" + position.x() + "_" + position.y() + "_" + position.z();
	}
	
	private static File[] getResourceFolderFiles(String folder) {
		final ClassLoader loader = Banners.class.getClassLoader();
		final URL url = loader.getResource(folder);
		final String path = url.getPath();
		
		return new File(path).listFiles();
	}
	
	public static void createIcons(MarkerAPI markerApi) {
		for (File file : getResourceFolderFiles(BANNER_FOLDER)) {
			final String colorName = file.getName().replace(".png", "");
			
			final InputStream inputStream;
			try {
				inputStream = new FileInputStream(file);
				
				if (markerApi.getMarkerIcon(getMarkerIconId(colorName)) == null) {
					markerApi.createMarkerIcon(getMarkerIconId(colorName), getMarkerIconLabel(colorName), inputStream);
				}
				else {
					markerApi.getMarkerIcon(getMarkerIconId(colorName)).setMarkerIconImage(inputStream);
				}
			}
			catch (FileNotFoundException e) {
				LOGGER.warn("Error opening banner image file", e);
			}
		}
	}
}
