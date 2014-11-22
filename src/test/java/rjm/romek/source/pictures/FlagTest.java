package rjm.romek.source.pictures;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.Set;

import org.testng.annotations.Test;

import rjm.romek.source.gen.CsvDeserializer;
import rjm.romek.source.model.Country;
import static rjm.romek.source.model.Properties.Path;

public class FlagTest {

	@Test
	public void testFlags() throws Exception {
		Set<Country> countries = new CsvDeserializer().deserialize(new File(
				Path.LIST_CSV));

		File countryDir = new File(Path.FLAGS);

		assertTrue(countryDir.isDirectory());
		File[] flagFiles = countryDir.listFiles();

		for (Country country : countries) {
			String adjustedCountryName = 
					country.getName().replace(" ", "_");
			boolean found = false;
			for (File flagFile : flagFiles) {
				if (flagFile.getName().contains(adjustedCountryName)) {
					found = true;
				}
			}
			assertTrue(found, adjustedCountryName + " has not been found!");
		}
	}
}
