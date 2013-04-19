package com.my.splitter.swing.model;

import java.nio.file.Path;

/**
 * Container for path object - for putting path into JList 
 * @author mgruzman
 *	
 */
public class PathObject {
	private Path path;
	
	public PathObject(Path path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return path.getFileName().toString();
	}
}
