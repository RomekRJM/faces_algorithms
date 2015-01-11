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
	public int maxFilesInDir;

	public Renamer() {
		this(Integer.MAX_VALUE);
	}

	public Renamer(int maxFilesInDir) {
		this.maxFilesInDir = maxFilesInDir;
	}

	public Map<String, String> changeFileNamesToUUIDSWithinFolder(File srcDir, File destDir,
			File namingMapFile) throws IOException {
		Map<String, String> namingMap = new HashMap<String, String>();
		
		if (!srcDir.isDirectory() || !destDir.isDirectory()) {
			return null;
		}

		FileUtils.copyDirectory(srcDir, destDir);
		replaceFileNamesWithUUIDSAndWriteNamingMap(destDir, namingMap);
		writeNamingMap(namingMapFile, namingMap);

		return namingMap;
	}

	private void replaceFileNamesWithUUIDSAndWriteNamingMap(File dir, Map<String, String> namingMap) {
		if (!dir.isDirectory()) {
			return;
		}

		File[] files = dir.listFiles();
		int cntr = 0;

		for (File file : files) {
			if (file.isDirectory()) {
				replaceFileNamesWithUUIDSAndWriteNamingMap(file, namingMap);
			} else {
				++cntr;
			}

			if (cntr <= maxFilesInDir) {
				String oldFileName = file.getName();
				String uuidName = renameFileToUUIDPreservingExtension(file);
				namingMap.put(uuidName, oldFileName);
			} else if (file.isFile()){
				file.delete();
			}
		}
	}

	private String renameFileToUUIDPreservingExtension(File file) {
		String separator = System.getProperty("file.separator");
		String newName = UUID.randomUUID().toString();

		if (file.isFile()) {
			newName += "."
					+ StringUtils.substringAfterLast(file.getName(), ".");
		}

		File newFile = new File(StringUtils.substringBeforeLast(
				file.getAbsolutePath(), separator)
				+ separator + newName);
		file.renameTo(newFile);

		return newName;
	}

	private void writeNamingMap(File file, Map<String, String> namingMap) {
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
