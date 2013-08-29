package com.my.splitter.file.notify;

import javax.swing.JProgressBar;

public class ProgressBarAdapter implements Adapter {
	
	private JProgressBar pb;

	public ProgressBarAdapter(JProgressBar pb) {
		this.pb = pb;
	}
	
	@Override
	public void take(Information info) {
		pb.setValue(info.getProgress());
	}

}
