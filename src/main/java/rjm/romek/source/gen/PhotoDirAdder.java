package rjm.romek.source.gen;

import java.io.File;
import java.util.Collection;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import rjm.romek.source.model.Country;

public class PhotoDirAdder {
	public void addPhotosOfFamousPeopleDir(Set<Country> countries, File nationalityDir) {
		File[] photoDirs = nationalityDir.listFiles();

		for (Country country : countries) {
			String adjustedCountryName = country.getName();
			for (File photoDir : photoDirs) {
				if (photoDir.getName().contains(adjustedCountryName)) {
					country.setFlag(photoDir.getName());
					break;
				}
			}
		}
	}
	
	String findClosestMatch(Collection<String> all, String query) {
		int max = 0;
		String bestMatch = null;
		query = StringUtils.lowerCase(query);
		
		for(String s : all) {
			int current = findLongestCommonSubstring(s, query);
			if(current > max) {
				max = current;
				bestMatch = s;
			}
		}
		
		return bestMatch;
	}
	
	int findLongestCommonSubstring(String s1, String s2) {
		int longestCommonSubstring = 0;
		int commonSubstring = 0;
		
		if (s1 == null || s2 == null) {
			return 0;
		}
		
		s1 = StringUtils.lowerCase(s1);
		s2 = StringUtils.lowerCase(s2);
		
		for (int i=0; i<s1.length(); ++i) {
			commonSubstring = 0;
			String fo = "";
			for (int j=0; j<s2.length() && j+i<s1.length(); ++j) {
				if(s1.charAt(j+i) == s2.charAt(j)) {
					++commonSubstring;
					fo = fo + (char)s1.charAt(i+j);
				}
			}
			if(commonSubstring > longestCommonSubstring) {
				longestCommonSubstring = commonSubstring;
				System.out.println(fo);
			}
		}
		
		return longestCommonSubstring;
	}
}
