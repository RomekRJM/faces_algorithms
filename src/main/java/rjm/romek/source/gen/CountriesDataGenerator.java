package rjm.romek.source.gen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;

import rjm.romek.source.model.Country;
import static rjm.romek.source.model.Properties.Path;
import static rjm.romek.source.model.Properties.Extension;;

public class CountriesDataGenerator {
	
	public static void main(String [] arg) {
		File csvFile = new File(Path.LIST_CSV);
		File jsonFile = new File(csvFile.getAbsolutePath().replace(Extension.CSV, Extension.JSON));
		File flagDir = new File(Path.FLAGS);
		File photoDir = new File(Path.PHOTOS);
		
		CsvDeserializer csvDeserializer = new CsvDeserializer();
		FlagExtractor flagExtractor = new FlagExtractor();
		CountriesSerializer jsonGenerator = new CountriesSerializer();
		PhotoDirChecker photoDirAdder = new PhotoDirChecker();
		
		Set<Country> countries = csvDeserializer.deserialize(csvFile);
		flagExtractor.addFlags(countries, flagDir);
		photoDirAdder.disableCountriesWithoutPhotos(countries, photoDir);
		try {
			jsonGenerator.serialize(jsonFile, countries);
		} catch (FileNotFoundException e) {
		}
	}
	
}
