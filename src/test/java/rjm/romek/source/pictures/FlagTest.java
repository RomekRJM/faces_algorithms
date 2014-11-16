package rjm.romek.source.pictures;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Set;

import org.junit.Test;

import rjm.romek.source.gen.CsvDeserializer;
import rjm.romek.source.model.Country;

public class FlagTest {

	@Test
	public void testFlags() throws Exception {
		Set<Country> countries = new CsvDeserializer().deserialize(new File(
				"src/main/resources/list.csv"));

		File countryDir = new File(
				"src/main/resources/borders/List of countries and territories by land and maritime borders");

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
			assertTrue(adjustedCountryName + " has not been found!", found);
		}
	}
}
