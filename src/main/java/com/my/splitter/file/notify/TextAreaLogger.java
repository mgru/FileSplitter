package com.my.splitter.file.notify;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextAreaLogger implements Notifier {
	
	private JTextArea tarea;
	private JScrollPane pane;

	public TextAreaLogger(JTextArea tarea, JScrollPane pane) {
		this.tarea = tarea;
		this.pane = pane;
	}

	@Override
	public void informProgress(Information info) {
		pane.setVisible(true);
		tarea.setVisible(true);
		StringBuilder sb = new StringBuilder();
		sb.append(tarea.getText()).append(info.getFilename()).append("     ").append(info.getFileSize()).append("\n");
		tarea.setText(sb.toString());
	}

}
