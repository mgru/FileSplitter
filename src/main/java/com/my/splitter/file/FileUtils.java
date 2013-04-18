package com.my.splitter.file;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
	
	public static List<Path> guessFileList(Path path) {
		ArrayList<Path> list = new ArrayList<Path>();
		list.add(path);
		return list;
	}

}
