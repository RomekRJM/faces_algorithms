package rjm.romek.source.gen;

import java.io.File;
import java.util.Set;

import rjm.romek.source.model.Country;

public class CountriesSerializer {
	public static void main(String [] arg) {
		File csvFile = new File("src/main/resources/list.csv");
		File jsonFile = new File(csvFile.getAbsolutePath().replace(".csv", ".json"));
		File flagDir = new File("src/main/resources/borders/List of countries and territories by land and maritime borders");
		File photoDir = new File("src/main/resources/photos");
		
		CsvDeserializer csvDeserializer = new CsvDeserializer();
		FlagExtractor flagExtractor = new FlagExtractor();
		JsonGenerator jsonGenerator = new JsonGenerator();
		PhotoDirChecker photoDirAdder = new PhotoDirChecker();
		
		Set<Country> countries = csvDeserializer.deserialize(csvFile);
		flagExtractor.addFlags(countries, flagDir);
		photoDirAdder.disableCountriesWithoutPhotos(countries, photoDir);
		jsonGenerator.writeCountriesToJSON(jsonFile, countries);
	}
}
