package rjm.romek.source.gen;

import java.io.File;
import java.util.Set;

import rjm.romek.source.model.Country;

public class CountriesSerializer {
	public static void main(String [] arg) {
		File csvFile = new File("f:/Eclipse Android/list.csv");
		File jsonFile = new File(csvFile.getAbsolutePath().replace(".csv", ".json"));
		File flagDir = new File("F:/Eclipse Android/borders/List of countries and territories by land and maritime borders - Wikipedia, the free encyclopedia_files");
		
		CsvDeserializer csvDeserializer = new CsvDeserializer();
		FlagExtractor flagExtractor = new FlagExtractor();
		JsonGenerator jsonGenerator = new JsonGenerator();
		
		Set<Country> countries = csvDeserializer.deserialize(csvFile);
		flagExtractor.addFlags(countries, flagDir);
		jsonGenerator.writeCountriesToJSON(jsonFile, countries);
	}
}
