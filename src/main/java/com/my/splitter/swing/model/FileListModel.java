package com.my.splitter.swing.model;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;

public class FileListModel extends AbstractListModel {
	
	private List<Path> list = Collections.EMPTY_LIST;

	public void setList(List<Path> list) {
		this.list = list;
	}

	@Override
	public int getSize() {
		return list.size();
	}

	@Override
	public Object getElementAt(int index) {
		return list.get(index);
	}

}
