package rjm.romek.source.gen;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import rjm.romek.source.model.Properties;

import com.google.gson.Gson;

public class RenamerTest {

	public static final File TEST_DIR = new File("target/test-files/test");
	public static final File TEST_NAMING = new File("target/test-files/naming.txt");
	public static final File RESOURCES_DIR = new File(Properties.RESOURCES_DIR);
	
	@BeforeClass
	public void setUp() throws IOException {
		FileUtils.forceMkdir(TEST_DIR);
	}
	
	@Test
	public void test() throws Exception {
		int maxFilesInFolder = 3;
		Renamer renamer = new Renamer(maxFilesInFolder);
		renamer.changeFileNamesToUUIDSWithinFolder(RESOURCES_DIR,
				TEST_DIR, TEST_NAMING);
		
		String mapAsString = FileUtils.readFileToString(TEST_NAMING);
		
		Map<String, String> map = new Gson().fromJson(mapAsString, Map.class);
		
		Collection<File> oldDir = FileUtils.listFilesAndDirs(RESOURCES_DIR, FileFilterUtils.trueFileFilter(), FileFilterUtils.trueFileFilter());
		Collection<File> newDir = FileUtils.listFilesAndDirs(TEST_DIR, FileFilterUtils.trueFileFilter(), FileFilterUtils.trueFileFilter());
		
		for(Entry<String, String> entry : map.entrySet()) {
			File newFile = fileIsPartOfCollection(entry.getKey(), newDir);
			File oldFile = fileIsPartOfCollection(entry.getValue(), oldDir);

			assertNotNull(entry.getKey() + "(" + entry.getValue() +  ") should be found in test directory.", newFile);
			assertNotNull(entry.getValue() + " should be found in resources directory.", oldFile);
			
			if(newFile.isFile()) {
				if(oldFile.length() != newFile.length()) {
					System.out.println(oldFile.getName());
				}
				assertEquals(oldFile.length(), newFile.length());
			} else {
				assertEquals(Math.min(oldFile.list().length, maxFilesInFolder), newFile.list().length);
			}
		}
	}

	private File fileIsPartOfCollection(String fileName, Collection<File> files) {
		for(File f : files) {
			if(StringUtils.equals(f.getName(), fileName)) {
				return f;
			}
		}
		
		return null;
	}
	
	@AfterClass
	public void tearDown() throws IOException {
		//FileUtils.forceDelete(TEST_DIR);
		//FileUtils.forceDelete(TEST_NAMING);
	}
}
