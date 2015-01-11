package rjm.romek.source.gen;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import rjm.romek.source.model.Country;

public class CountryUUIDFetcher {

	public void fetch(Set<Country> countries, Map<String, String> uuidMap) {
		for(Country country : countries) {
			country.setFlagFile(findKeyForValue(country.getFlag(), uuidMap));
			country.setCountryDir(findKeyForValue(country.getName(), uuidMap));
		}
	}
	
	private String findKeyForValue(String value, Map<String, String> uuidMap) {
		
		for(Entry<String, String> entry : uuidMap.entrySet()) {
			if(StringUtils.equals(entry.getValue(), value)) {
				return entry.getKey();
			}
		}
		
		return null;
	}
}
