package com.my.splitter.file;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
	
	public static String getExtention(Path path) {
		String string = path.getFileName().toString();
		int dotIndex = string.lastIndexOf('.');
		if(dotIndex > 0) {
			return string.substring(dotIndex + 1);
		}
		return "";
	}
	
	public static String getNameWithoutExt(Path path) {
		String string = path.getFileName().toString();
		int dotIndex = string.lastIndexOf('.');
		if(dotIndex > 0) {
			return string.substring(0, dotIndex);
		}
		return string;
	}
	
	public static Path getFolder(Path path) {
		return path.toAbsolutePath().getParent();
	}
	
	public static List<Path> guessFileList(Path path) {
		ArrayList<Path> list = new ArrayList<Path>();
		list.add(path);
		return list;
	}

}
