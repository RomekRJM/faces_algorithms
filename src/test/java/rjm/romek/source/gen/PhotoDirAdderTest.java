package rjm.romek.source.gen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import rjm.romek.source.model.Country;

public class PhotoDirAdderTest {
	
	@Test
	public void testAllCountriesMatchedToDirName() {
		Set<Country> countries = new CsvDeserializer().deserialize(new File(
				"src/main/resources/list.csv"));
		
		List<String> allDirNames = prepareDirNamesList(new File("src/main/resources/photos"));
		
		for(Country country : countries) {
			//assertNotNull(country.getName() + " does not have matching folder!", country.getPhotoFolder());
			boolean removed = allDirNames.remove(country.getName());
			System.out.println(country.getName());
			
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
