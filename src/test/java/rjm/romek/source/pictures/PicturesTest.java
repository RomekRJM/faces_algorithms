package rjm.romek.source.pictures;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import rjm.romek.source.gen.PhotoDirChecker;
import static rjm.romek.source.model.Properties.Path;

public class PicturesTest {
	
	private final String rootPath = Path.PHOTOS;
	
	@Test
	public void testNationDirsContainEnoughtPhotos() {
		Map<String, Integer> picturesPerNationality = new HashMap<String, Integer>();
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
			
			if(value < PhotoDirChecker.MIN_PHOTOS) {
				minimumReached = false;
				System.out.println(entry.getKey() + " has only " + value + " photos.");
			}
		}
		
		assertTrue(minimumReached, "Each folder should contain at least: " + PhotoDirChecker.MIN_PHOTOS + " photos.");
	}

	@Test
	public void testPicturesNotRepeated() throws Exception {
		Map<String, List<String>> pictureMd5 = new HashMap<String, List<String>>();
		File picturesDir = new File(rootPath);

		assertTrue(picturesDir.isDirectory());
		File[] nationalityDirs = picturesDir.listFiles();

		for (File nationalityDir : nationalityDirs) {
			assertTrue(nationalityDir.isDirectory());
			File [] photos = nationalityDir.listFiles();
			
			for(File photo : photos) {
				assertFalse(photo.isDirectory());
				String path = photo.getPath();
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
		
		assertFalse(repeatedPictures, "There mustn't be repeated pictures in the folder");
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
