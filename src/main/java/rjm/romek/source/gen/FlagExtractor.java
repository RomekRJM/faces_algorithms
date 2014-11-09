package rjm.romek.source.gen;

import java.io.File;
import java.util.Set;

import rjm.romek.source.model.Country;

public class FlagExtractor {
	
	public void addFlags(Set<Country> countries, File flagDir) {
		File[] flagFiles = flagDir.listFiles();

		for (Country country : countries) {
			String adjustedCountryName = 
					country.getName().replace(" ", "_");
			for (File flagFile : flagFiles) {
				if (flagFile.getName().contains(adjustedCountryName)) {
					country.setFlag(flagFile.getName());
					break;
				}
			}
		}
	}
}
