package com.my.splitter.file.notify;

public class Information {
	
	private String filename;
	private long fileSize;
	private int progress;
	public String getFilename() {
		return filename;
	}
	public long getFileSize() {
		return fileSize;
	}
	public int getProgress() {
		return progress;
	}
	public Information(String filename, long fileSize, int progress) {
		this.filename = filename;
		this.fileSize = fileSize;
		this.progress = progress;
	}
}
