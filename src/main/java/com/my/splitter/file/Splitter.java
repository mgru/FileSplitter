package com.my.splitter.file;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for breaking file to pieces
 * 
 * @author mike
 * 
 */
public class Splitter {

	private final static Logger log = LoggerFactory.getLogger(OperationResult.class);
	
	private File file;

	public Splitter(File file) {
		this.file = file;
	}

	/**
	 * Creates files that are copies of the original file parts in sequence
	 * 
	 * @param size
	 *            - the size of the parts in byte
	 */
	public OperationResult split(int size) {
		OperationResult or = new OperationResult(false);
		try {
			FileChannel fc = FileChannel.open(file.toPath(),
					StandardOpenOption.READ);
			calculatePartsNumber(size, fc.size());
		} catch (IOException e) {
			or.setMessage("Could not open the file");
			log.error("Could not open file {}", file.toPath(), e);
		}
		return or;
	}

	private int calculatePartsNumber(int chunckSize, long fileSize) {
		long parsNum = fileSize / chunckSize;
		if (fileSize % chunckSize != 0) {
			parsNum += 1;
		}
		return (int) parsNum;
	}

}
