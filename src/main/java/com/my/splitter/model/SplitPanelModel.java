package com.my.splitter.model;

import java.io.File;

public class SplitPanelModel {
	
	private String fileLabel;
	private String sizeLabel;
	private String folderLabel;
	private File file;
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileLabel() {
		return fileLabel;
	}
	public String getSizeLabel() {
		return sizeLabel;
	}
	public String getFolderLabel() {
		return folderLabel;
	}
	public void setLabels(String folder, String fileLabel, String size) {
		this.fileLabel = fileLabel;
		this.folderLabel = folder;
		this.sizeLabel = size;
	}
	public SplitPanelModel(String fileLabel, String sizeLabel, String folderLabel) {
		this.fileLabel = fileLabel;
		this.sizeLabel = sizeLabel;
		this.folderLabel = folderLabel;
	}
}
