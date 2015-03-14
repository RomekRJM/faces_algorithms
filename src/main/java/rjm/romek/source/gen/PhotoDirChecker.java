package rjm.romek.source.gen;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import rjm.romek.source.model.Country;

public class PhotoDirChecker {
	
	public static final int MIN_PHOTOS = 1;
	
	public void disableCountriesWithoutPhotos(Set<Country> countries, File nationalityDir) {
		List<String> allDirNames = prepareDirNamesListMatchingCriteria(nationalityDir);
        Set<Country> countriesMatchingCriteria = new HashSet<Country>();

		for (Country country : countries) {
			if(allDirNames.contains(country.getName())) {
                countriesMatchingCriteria.add(country);
			}
		}

        countries.clear();
        countries.addAll(countriesMatchingCriteria);
	}
	
	List<String> prepareDirNamesListMatchingCriteria(File nationalityDir) {
		List<String> map = new ArrayList<String>();
		
		if(!nationalityDir.isDirectory()) {
			return map;
		}
		
		File[] photoDirs = nationalityDir.listFiles();
		
		for(File p : photoDirs) {
			if(p.isDirectory() && p.list().length >= MIN_PHOTOS) {
				map.add(p.getName());
			}
		}
		
		return map;
	}
	
}
