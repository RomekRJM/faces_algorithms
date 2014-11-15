package rjm.romek.source.randomizer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class PicturesTest {
	
	private final int MIN_PHOTOS = 1;
	
	@Test
	public void testNationDirsContainEnoughtPhotos() {
		Map<String, Integer> picturesPerNationality = new HashMap<String, Integer>();
		String rootPath = "F:/Eclipse Android/adt-bundle-windows-x86_64-20140624/workspace/faces_algorithms/src/main/resources/photos";
		File picturesDir = new File(rootPath);

		assertTrue(picturesDir.isDirectory());
		File[] nationalityDirs = picturesDir.listFiles();

		for (File nationalityDir : nationalityDirs) {
			assertTrue(nationalityDir.isDirectory());
			File [] photos = nationalityDir.listFiles();
			picturesPerNationality.put(nationalityDir.getName(), new Integer(photos.length));
		}
		
		boolean minimumReached = true;
		int i = 0;
		
		for(Map.Entry<String, Integer> entry : picturesPerNationality.entrySet()) {
			Integer value = entry.getValue();
			
			if(value < MIN_PHOTOS) {
				minimumReached = false;
			}
		}
		
		assertTrue("Each folder should contain at least: " + MIN_PHOTOS + " photos.", minimumReached);
	}

	@Test
	public void testPicturesNotRepeated() throws Exception {
		Map<String, List<String>> pictureMd5 = new HashMap<String, List<String>>();
		String rootPath = "F:/Eclipse Android/adt-bundle-windows-x86_64-20140624/workspace/faces_algorithms/src/main/resources/photos";
		File picturesDir = new File(rootPath);

		assertTrue(picturesDir.isDirectory());
		File[] nationalityDirs = picturesDir.listFiles();

		for (File nationalityDir : nationalityDirs) {
			assertTrue(nationalityDir.isDirectory());
			File [] photos = nationalityDir.listFiles();
			
			for(File photo : photos) {
				assertFalse(photo.isDirectory());
				String path = photo.getAbsolutePath().substring(rootPath.length());
				String md5 = computeMd5(photo);
				
				if(pictureMd5.containsKey(md5)) {
					pictureMd5.get(md5).add(path);
				} else {
					List<String> list = new ArrayList<String>();
					list.add(path);
					pictureMd5.put(md5, list);
				}
			}
		}
		
		boolean repeatedPictures = false;
		int i = 0;
		
		for(Map.Entry<String, List<String>> entry : pictureMd5.entrySet()) {
			List<String> value = entry.getValue();
			
			if(value.size() > 1) {
				repeatedPictures = true;
				for(String s : entry.getValue()) {
					System.out.print(s + " ");
				}
				System.out.println();
			}
		}
		
		assertFalse("There mustn't be repeated pictures in the folder", repeatedPictures);
	}

	public String computeMd5(File file) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		FileInputStream fis = new FileInputStream(file);

		byte[] dataBytes = new byte[1024];

		int nread = 0;
		while ((nread = fis.read(dataBytes)) != -1) {
			md.update(dataBytes, 0, nread);
		}
		
		byte[] mdbytes = md.digest();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < mdbytes.length; i++) {
			sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		
		fis.close();

		return sb.toString();
	}
}
