package com.my.splitter.swing;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import com.my.splitter.swing.model.PathObject;

public class ChunckListListener implements ListDataListener {
	
	private DefaultListModel<PathObject> model;
	private JButton clear;
	private JButton stitch;

	public ChunckListListener(DefaultListModel<PathObject> listModel,
			JButton btnClear, JButton btnStitch) {
		model = listModel;
		clear = btnClear;
		stitch = btnStitch;
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		update();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		update();
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		update();
	}

	private void update() {
		clear.setEnabled(!model.isEmpty());
		stitch.setEnabled(model.size() > 1);
	}

}
