package com.my.splitter.file;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils {
	
	private static final Logger log = LoggerFactory.getLogger(FileUtils.class);
	
	private static final String EXTENTION = "";
	private static final String SUFFIX_PATTERN = "0[1-9][0-9]*$" + EXTENTION;

	public static String getExtention(Path path) {
		String string = path.getFileName().toString();
		int dotIndex = string.lastIndexOf('.');
		if(dotIndex > 0) {
			return string.substring(dotIndex + 1);
		}
		return EXTENTION;
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
		List<Path> list = makeGuess(path);
		if(list.isEmpty()) {
			list.add(path);
		}
		return list;
	}

	private static List<Path> makeGuess(Path path) {
		ArrayList<Path> list = new ArrayList<>();
		if(getExtention(path).equals(EXTENTION)) {
			String name = getNameWithoutExt(path);
			Pattern pattern = Pattern.compile(SUFFIX_PATTERN);
			Matcher matcher = pattern.matcher(name);
			if(matcher.find()) {
				name = name.split(SUFFIX_PATTERN)[0];
				Path workingDirectory = path.getParent().toAbsolutePath();
				try {
					DirectoryStream<Path> directoryStream = Files.newDirectoryStream(workingDirectory);
					Pattern filePattern = Pattern.compile(name + SUFFIX_PATTERN);
					for(Path p : directoryStream) {
						if(p.toFile().isFile()) {
							Matcher fileMatcher = filePattern.matcher(p.getFileName().toString());
							if(fileMatcher.find()) {
								list.add(p);
							}
						}
					}
				} catch (IOException e) {
					if(log.isDebugEnabled()) {
						log.debug("IO exception", e);
					}
				}
				specialOrder(list);
			}
		}
		return list;
	}

	private static void specialOrder(ArrayList<Path> list) {
		 Collections.sort(list, new Comparator<Path>() {

				@Override
				public int compare(Path o1, Path o2) {
					return compareSuffix(o1,o2);
				}

				private int compareSuffix(Path o1, Path o2) {
					int i1 = pathToInt(o1);
					int i2 = pathToInt(o2);
					return i1 == i2 ? 0 : i1 > i2 ? 1 : -1;
				}

				private int pathToInt(Path path) {
					String name = getNameWithoutExt(path);
					Pattern pattern = Pattern.compile(SUFFIX_PATTERN);
					Matcher matcher = pattern.matcher(name);
					if(!matcher.find()) {
						throw new IllegalArgumentException();
					}
					String str = matcher.group().substring(1);
					return Integer.parseInt(str);
				}});
	}

}
