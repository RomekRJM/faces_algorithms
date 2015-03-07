package rjm.romek.source.gen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import rjm.romek.source.model.Country;
import rjm.romek.source.model.Properties;
import static rjm.romek.source.model.Properties.Path;
import static rjm.romek.source.model.Properties.Extension;;

public class Main {
	
	public static void main(String [] arg) {
		File csvFile = new File(Path.LIST_CSV);
		File jsonFile = new File(csvFile.getAbsolutePath().replace(Extension.CSV, Extension.JSON));
		File flagDir = new File(Path.FLAGS);
		File photoDir = new File(Path.PHOTOS);
		File resourcesDir = new File(Properties.RESOURCES_DIR);
		File targetDir = new File(Properties.OUTPUT_DIR);
		File naming = new File(Properties.NAMING);
		
		CsvDeserializer csvDeserializer = new CsvDeserializer();
		FlagExtractor flagExtractor = new FlagExtractor();
		CountriesSerializer jsonGenerator = new CountriesSerializer();
		PhotoDirChecker photoDirAdder = new PhotoDirChecker();
		Renamer renamer = new Renamer(3,
				new String[]{"output", "output\\flags", "output\\unknown_photos"},
				new String[]{".json", ".csv", ".xml", ".txt", ".bash", "flags", "photos", "unknown_photos"});
		
		Set<Country> countries = csvDeserializer.deserialize(csvFile);
		flagExtractor.addFlags(countries, flagDir);
		photoDirAdder.disableCountriesWithoutPhotos(countries, photoDir);
		
		try {
			renamer.changeFileNamesToUUIDSWithinFolder(resourcesDir, targetDir, naming);
			jsonGenerator.serialize(jsonFile, countries);
		} catch (IOException e1) {
		}
	}
	
}
