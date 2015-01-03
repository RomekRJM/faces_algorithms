package rjm.romek.source.gen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;

public class Renamer {
	public Map<String, String> namingMap;
	public int cntr = 0;

	public Renamer() {
		namingMap = new HashMap<String, String>();
	}

	public void changeFileNamesToUUIDSWithinFolder(File srcDir, File destDir, File namingMap) throws IOException {
		if (!srcDir.isDirectory() || !destDir.isDirectory()) {
			return;
		}
		
		FileUtils.copyDirectory(srcDir, destDir);
		replaceFileNamesWithUUIDSAndWriteNamingMap(destDir);
		writeNamingMap(namingMap);
		
	}
	
	public void replaceFileNamesWithUUIDSAndWriteNamingMap(File dir) {
		if (!dir.isDirectory()) {
			return;
		}

		File[] files = dir.listFiles();

		for (File file : files) {
			if (file.isDirectory()) {
				replaceFileNamesWithUUIDSAndWriteNamingMap(file);
			}
			String oldFileName = file.getName();
			String uuidName = renameFileToUUIDPreservingExtension(file);
			namingMap.put(uuidName, oldFileName);
		}
	}
	
	private String renameFileToUUIDPreservingExtension(File file) {
		String separator = System.getProperty("file.separator");
		String newName = UUID.randomUUID().toString();
		
		if(file.isFile()) {
			newName += "." + StringUtils.substringAfterLast(file.getName(), ".");
		}
		 
		File newFile = new File(StringUtils.substringBeforeLast(file.getAbsolutePath(), separator) 
				+ separator + newName);
		file.renameTo(newFile);
		
		return newName;
	}
	
	private void writeNamingMap(File file) {
		FileOutputStream fos = null;
		
		try {
			fos = new FileOutputStream(file);
			IOUtils.write(new Gson().toJson(namingMap), fos);
		} catch (IOException e) {
		} finally {
			IOUtils.closeQuietly(fos);
		}
	}
	
}
