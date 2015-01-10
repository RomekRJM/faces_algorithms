package rjm.romek.source.gen;

import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import rjm.romek.source.model.Country;
import rjm.romek.source.model.Properties.Path;

public class SerializationTest {
	private Set<Country> countries;
	private CountriesSerializer serializer;
	private CountriesDeserializer deserializer;
	private File serializedFile;
	
	@BeforeClass
	public void setUp() throws IOException {
		countries = new CsvDeserializer().deserialize(new File(Path.LIST_CSV));
		deserializer = new CountriesDeserializer();
		serializer = new CountriesSerializer();
		serializedFile = File.createTempFile("serialized", "json");
	}

	@Test
	public void test() throws Exception {
		serializer.serialize(serializedFile, countries);
		Set<Country> deserializedCountries = deserializer.deserialize(serializedFile);
		
		assertCountrySetsIdentical(countries, deserializedCountries);
	}
	
	private void assertCountrySetsIdentical(Set<Country> set1, Set<Country> set2) {
		assertEquals(set1.size(), set2.size());
		
		for(Country country1 : set1) {
			Country country2Match = null;
			
			for(Country country2 : set2) {
				if(country1.getName().equals(country2.getName())) {
					assertTrue(country1.equals(country2));
					country2Match = country2;
					break;
				}
			}
			assertNotNull(country2Match);
		}
	}

	@AfterClass
	public void tearDown() throws IOException {
		serializedFile.delete();
	}
}
