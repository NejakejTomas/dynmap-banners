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
	
//	private static File[] getResourceFolderFiles(String folder) {
//		LOGGER.info("0");
//		final ClassLoader loader = Banners.class.getClassLoader();
//		LOGGER.info("1");
//		final URL url = loader.getResource(folder);
//		LOGGER.info("2");
//		final String path = url.getPath();
//
//		LOGGER.info("3");
//		LOGGER.info(path);
//		LOGGER.info("4");
//		LOGGER.info((new File(path)).toString());
//		File[] files = new File(path).listFiles();
//
//		return new File(path).listFiles();
//	}

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
		
		
//		for (File file : getResourceFolderFiles(BANNER_FOLDER)) {
//			final String colorName = file.getName().replace(".png", "");
//
//			final InputStream inputStream;
//			try {
//				inputStream = new FileInputStream(file);
//
//				if (markerApi.getMarkerIcon(getMarkerIconId(colorName)) == null) {
//					markerApi.createMarkerIcon(getMarkerIconId(colorName), getMarkerIconLabel(colorName), inputStream);
//				}
//				else {
//					markerApi.getMarkerIcon(getMarkerIconId(colorName)).setMarkerIconImage(inputStream);
//				}
//			}
//			catch (FileNotFoundException e) {
//				LOGGER.warn("Error opening banner image file", e);
//			}
//		}
	}
}
