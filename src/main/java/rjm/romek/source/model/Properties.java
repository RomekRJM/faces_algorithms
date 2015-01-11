package rjm.romek.source.model;

public class Properties {
	public static final String RESOURCES_DIR = "src/main/resources";
	public static final String OUTPUT_DIR = "output/";
	public static final String NAMING = "naming.json";
	
	public static class Path {
		public static final String LIST_CSV = RESOURCES_DIR + "/list.csv";
		public static final String MISSING = RESOURCES_DIR + "/missing";
		public static final String PHOTOS = RESOURCES_DIR + "/photos";
		public static final String FLAGS = "src/main/resources/flags";
	}
	
	public static class Extension {
		public static final String CSV = ".csv";
		public static final String JSON = ".json";
	}
}
