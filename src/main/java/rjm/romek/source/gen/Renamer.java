package rjm.romek.source.gen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;

public class Renamer {
	public int maxFilesInDir;
	public String[] dontReplaceFilesEndingWithName;
	public String[] dontLimitMaxFilesInDirs;
	char hexDigit[] = {
	         '0', '1', '2', '3', '4', '5', '6', '7',
	         '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
	      };

	public Renamer() {
		this(Integer.MAX_VALUE, new String[] {}, new String[] {});
	}

	public Renamer(int maxFilesInDir, String[] dontLimitMaxFilesInDirs, String[] dontReplaceFilesEndingWithName) {
		this.maxFilesInDir = maxFilesInDir;
		this.dontLimitMaxFilesInDirs = dontLimitMaxFilesInDirs;
		this.dontReplaceFilesEndingWithName = dontReplaceFilesEndingWithName;
	}

	public void changeFileNamesToUUIDSWithinFolder(File srcDir,
			File destDir, File namingMapFile) throws IOException {

		if (!srcDir.isDirectory()) {
			return;
		}

		if (destDir.exists()) {
			FileUtils.deleteDirectory(destDir);
		}

		destDir.mkdir();

		FileUtils.copyDirectory(srcDir, destDir);
		enodeFileNames(destDir);
	}

	private void enodeFileNames(File dir) {
		if (!dir.isDirectory()) {
			return;
		}

		File[] files = dir.listFiles();
		int cntr = 0;

		for (File file : files) {
			if (file.isDirectory()) {
				enodeFileNames(file);
			} else if (!fileIsInUnlimitedSizeFolder(file, dontLimitMaxFilesInDirs)) {
				++cntr;
			}

			if (cntr <= maxFilesInDir) {
				if (!fileNameEndsWith(file, dontReplaceFilesEndingWithName)) {
					renameFileToEncoded(file);
				}
			} else if (file.isFile()) {
				file.delete();
			}
		}
	}

	private String renameFileToEncoded(File file) {
		String separator = System.getProperty("file.separator");
		String newName = encodeName(StringUtils.substringBeforeLast(file.getName(), "."));

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
	
	private String encodeName(String raw) {
		try {
			return URLEncoder.encode(raw, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return raw;
		}
	}

	private boolean fileNameEndsWith(File file, String[] names) {
		for (String name : names) {
			if (file.getName().endsWith(name)) {
				return true;
			}
		}

		return false;
	}
	
	private boolean fileIsInUnlimitedSizeFolder(File file, String[] folderNames) {
		if(file.getAbsolutePath().contains("flags")) {
			System.out.print("");
		}
		for (String name : folderNames) {
			if (file.getParent().equals(name)) {
				return true;
			}
		}

		return false;
	}

}
