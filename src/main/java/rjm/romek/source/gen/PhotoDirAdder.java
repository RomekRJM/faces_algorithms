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
	private static final int MIN_MATCH_STRING_LENGTH = 4;
	
	public void addPhotosOfFamousPeopleDir(Set<Country> countries, File nationalityDir) {
		List<String> allDirNames = prepareDirNamesList(nationalityDir);

		for (Country country : countries) {
			String closesDirName = findClosestMatch(allDirNames, country.getName());
			if(closesDirName != null) {
				country.setPhotoDir(closesDirName);
				allDirNames.remove(closesDirName);
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
	
	/**
	 * 
	 * @param all
	 * @param query
	 * @return a string from all, that is closest to query or null
	 */
	String findClosestMatch(Collection<String> all, String query) {
		
		if(all==null || StringUtils.isBlank(query)) {
			return null;
		}
		
		int max = -1;
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
	
	/**
	 * Loops throught s1, trying to find longest substring, that is s2.substring(0,x).
	 * Returns length of longest substring.
	 * 
	 * Example:
	 * s1 = "I need a dollar!", s2 = "need" returns 4 
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
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
			if(commonSubstring > longestCommonSubstring && commonSubstring > MIN_MATCH_STRING_LENGTH) {
				longestCommonSubstring = commonSubstring;
				System.out.println(fo);
			}
		}
		
		return longestCommonSubstring;
	}
}
