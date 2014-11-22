package rjm.romek.source.gen;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import rjm.romek.source.model.Country;

public class PhotoDirAdder {
	
	public void addPhotosOfFamousPeopleDir(Set<Country> countries, File nationalityDir) {
		List<String> allDirNames = prepareDirNamesList(nationalityDir);

		for (Country country : countries) {
			if(!allDirNames.contains(country.getName())) {
				country.setDisabled(true);
			}
		}
	}
	
	List<String> prepareDirNamesList(File nationalityDir) {
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
