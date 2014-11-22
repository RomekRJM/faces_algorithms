package rjm.romek.source.gen;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import rjm.romek.source.model.Country;

public class PhotoDirCheckerTest {
	
	@Test
	public void testAllCountriesMatchedToDirName() throws IOException {
		Set<Country> countries = new CsvDeserializer().deserialize(new File(
				"src/main/resources/list.csv"));
		
		List<String> missingCountries = FileUtils.readLines(new File("src/main/resources/missing"));
		List<String> allDirNames = prepareDirNamesList(new File("src/main/resources/photos"));
		
		for(Country country : countries) {
			boolean removed = allDirNames.remove(country.getName());
			if(removed) {
				assertFalse(country.getName() + " has matching folder and shouldn't be disabled!", country.isDisabled());
			} else if (!missingCountries.contains(country.getName())) {
				assertTrue(country.getName() + " does not have matching folder and is not disabled!", country.isDisabled());
			}
			
		}
	}
	
	private List<String> prepareDirNamesList(File nationalityDir) {
		List<String> map = new ArrayList<String>();
		
		if(!nationalityDir.isDirectory()) {
			return map;
		}
		
		File[] photoDirs = nationalityDir.listFiles();
		
		for(File p : photoDirs) {
			map.add(p.getName());
		}
		
		return map;
	}

}
