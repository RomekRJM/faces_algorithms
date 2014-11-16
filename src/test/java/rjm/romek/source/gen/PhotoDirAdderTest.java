package rjm.romek.source.gen;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

public class PhotoDirAdderTest {
	
	PhotoDirAdder pda = new PhotoDirAdder();

	@Test
	public void testFindLongestCommonSubstring() {
		
		assertEquals(4, pda.findLongestCommonSubstring("dupa jest okragla !", "dupa"));
		assertEquals(9, pda.findLongestCommonSubstring("dupa jest okragla !", "okragla !"));
		assertEquals(5, pda.findLongestCommonSubstring("dupa jest okragla !", " jest"));
		assertEquals(0, pda.findLongestCommonSubstring("dupa jest okragla !", "xyz"));
		assertEquals(0, pda.findLongestCommonSubstring("dupa jest okragla !", null));
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
