package com.my.splitter.file;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.my.splitter.file.notify.Dummy;
import com.my.splitter.file.notify.Information;
import com.my.splitter.file.notify.Notifier;

public class Stitcher {
	
	private static final Logger log = LoggerFactory.getLogger(Stitcher.class);
	
	private static final String RESULTING_NAME ="stitched.bin";

	private static final EnumSet<StandardOpenOption> CREATE_NEW_FILE_OPTIONS = EnumSet.of(StandardOpenOption.CREATE_NEW,
	StandardOpenOption.WRITE);

	private static final EnumSet<StandardOpenOption> READ_FILE_OPTIONS = EnumSet.of(StandardOpenOption.READ);
	
	private List <Path> list;
	
	private Notifier informer;
	
	private String resultingName;

	public Stitcher(List<Path> list, Notifier informer) {
		this.list = list;
		this.informer = informer;
		this.resultingName = RESULTING_NAME;
	}

	public Stitcher(List<Path> list) {
		this.list = list;
		this.informer  = new Dummy();
		this.resultingName = RESULTING_NAME;
	}

	
	public Stitcher(List<Path> chunklist, Notifier informer, String resultingName) {
		super();
		this.list = chunklist;
		this.informer = informer;
		this.resultingName = resultingName;
	}

	public OperationResult stitch() {
		if(list == null || list.isEmpty()) {
			throw new IllegalArgumentException("File list is empty");
		}
		OperationResult or = new OperationResult(false);
		Path workingFolder = list.get(0).toAbsolutePath().getParent();
		if(workingFolder == null) { workingFolder = Paths.get(".\\"); }
		Path result = workingFolder.resolve(resultingName);
		try (FileChannel fc = FileChannel.open(result, CREATE_NEW_FILE_OPTIONS)) {
			stitchFiles(fc);
			fc.close();
		} catch (IOException e) {
			log.error("Could not create bulk file", e);
			or.setMessage("Could not create bulk file " + result);
		}
		return or;
	}

	private void stitchFiles(FileChannel fc) {
		for(int i = 0, n = list.size(); i < n; i++) {
			Path path = list.get(i);
			try(FileChannel pfc = FileChannel.open(path, READ_FILE_OPTIONS)) {
				pfc.transferTo(0, pfc.size(), fc);
				informer.informProgress(new Information(path.toString(), pfc.size(), (int) Math.floor((double)i / (double)list.size())));
				pfc.close();
			} catch (IOException e) {
				log.error("Could not process file {}", path, e);
			}
		}
	}
}
