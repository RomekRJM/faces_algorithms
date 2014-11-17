package rjm.romek.source.gen;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.junit.Test;

import rjm.romek.source.model.Country;

public class PhotoDirAdderTest {
	
	PhotoDirAdder pda = new PhotoDirAdder();
	
	@Test
	public void testAllCountriesMatchedToDir() {
		Set<Country> countries = new CsvDeserializer().deserialize(new File(
				"src/main/resources/list.csv"));
		
		pda.addPhotosOfFamousPeopleDir(countries, new File("src/main/resources/photos"));
		
		for(Country country : countries) {
			assertNotNull(country.getName() + " does not have matching folder!", country.getPhotoFolder());
			System.out.println(country.getName() + " matches " + country.getPhotoFolder());
		}
	}

	@Test
	public void testFindLongestCommonSubstring() {
		
		assertEquals(1, pda.findLongestCommonSubstring("I need a dollar!", "I"));
		assertEquals(4, pda.findLongestCommonSubstring("I need a dollar!", "need"));
		assertEquals(3, pda.findLongestCommonSubstring("I need a dollar!", "ar!"));
		assertEquals(0, pda.findLongestCommonSubstring("I need a dollar!", "xyz"));
		assertEquals(0, pda.findLongestCommonSubstring("I need a dollar!", null));
		assertEquals(0, pda.findLongestCommonSubstring("need", "I need a dollar!"));
	}
	
	@Test
	public void testFindClosestMatch() {
		Collection<String> all = new ArrayList<String>();
		
		all.add("Algerians");
		all.add("Americans");
		all.add("Azeris");
		all.add("Bosniaks");
		
		String query = "Azerbejian";
		
		assertEquals("Azeris", pda.findClosestMatch(all, query));
		
	}

}
