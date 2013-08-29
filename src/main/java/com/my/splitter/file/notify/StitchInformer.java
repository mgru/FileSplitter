package com.my.splitter.file.notify;

public class StitchInformer implements Notifier {
	
	private ProgressBarAdapter adapter;

	public StitchInformer(ProgressBarAdapter adapter) {
		this.adapter = adapter;
	}

	@Override
	public void informProgress(Information info) {
		adapter.take(info);
	}

}
