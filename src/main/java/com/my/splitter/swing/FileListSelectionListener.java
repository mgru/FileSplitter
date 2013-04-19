package com.my.splitter.swing;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FileListSelectionListener implements ListSelectionListener {
	
	private JButton button;

	public FileListSelectionListener(JButton removeBtn) {
		button = removeBtn;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		@SuppressWarnings("rawtypes")
		int index = ((JList) e.getSource()).getSelectedIndex();
		button.setEnabled(! (index < 0));
	}

}
