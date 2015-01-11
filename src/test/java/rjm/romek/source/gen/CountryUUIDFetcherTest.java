package rjm.romek.source.gen;

import static org.testng.AssertJUnit.assertEquals;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.testng.annotations.Test;

import rjm.romek.source.model.Country;

public class CountryUUIDFetcherTest {
	public CountryUUIDFetcher fetcher = new CountryUUIDFetcher();
	
	@Test
	public void shouldFetchCorrectUUIDs() {
		Country poland = createCountry("Poland", "23px-Flag_of_Poland.png");
		Country thailand = createCountry("Thailand", "23px-Flag_of_Thailand.png");
		
		Set<Country> countries = new LinkedHashSet<Country>();
		countries.add(poland);
		countries.add(thailand);
		
		Map<String, String> uuidMap = new HashMap<String, String>();
		uuidMap.put("9b02f2f2-03ca-4918-a87b-15be5925d2a4.png", "23px-Flag_of_Poland.png");
		uuidMap.put("d15cd338-fe05-43a7-9734-0e4f89596a35", "Poland");
		uuidMap.put("f76021a2-a691-40b8-ae6e-b6a6f604d779", "Thailand");
		uuidMap.put("ff5b52fa-0e08-4f0a-82d5-2bb73c17f419.png", "23px-Flag_of_Thailand.png");
		
		fetcher.fetch(countries, uuidMap);
		
		assertCountryHasProperFlagFileAndCountryDir(poland, "9b02f2f2-03ca-4918-a87b-15be5925d2a4.png", "d15cd338-fe05-43a7-9734-0e4f89596a35");
		assertCountryHasProperFlagFileAndCountryDir(thailand, "ff5b52fa-0e08-4f0a-82d5-2bb73c17f419.png", "f76021a2-a691-40b8-ae6e-b6a6f604d779");
	}
	
	private Country createCountry(String name, String flag) {
		Country country = new Country();
		country.setName(name);
		country.setFlag(flag);
		
		return country;
	}
	
	private void assertCountryHasProperFlagFileAndCountryDir(Country country, String flagFile, String countryDir) {
		assertEquals(country.getFlagFile(), flagFile);
		assertEquals(country.getCountryDir(), countryDir);
	}
}
