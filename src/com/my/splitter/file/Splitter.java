package com.my.splitter.file;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;

/**
 * Class for breaking file to pieces
 * 
 * @author mike
 * 
 */
public class Splitter {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return or;
	}

	private int calculatePartsNumber(int chunckSize, long fileSize)
			throws IOException {
		long parsNum = fileSize / chunckSize;
		if(fileSize % chunckSize != 0) {
			parsNum += 1;
		}
		return (int) parsNum;
	}

}
