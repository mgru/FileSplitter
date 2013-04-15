/**
 * 
 */
package com.my.splitter.test;

import static java.nio.file.StandardOpenOption.*;
import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.my.splitter.file.OperationResult;
import com.my.splitter.file.Splitter;

/**
 * @author mike
 *
 */
public class SplitterTest {

	private static final int CUNCK_SIZE = 50;
	private static final int SIZE = 100;
	private static final String TEMPFILE_ORIGIN = "tmp\\test.txt";
	private static final String TEMPFOLDER = "tmp";
	private static Splitter splitter = null;
	private static final Logger log = LoggerFactory.getLogger(SplitterTest.class);

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Files.createDirectories(Paths.get(TEMPFOLDER));
		String encoding = System.getProperty("file.encoding");
		Charset cs = Charset.forName(encoding);
		Set<OpenOption> options = new HashSet<OpenOption>();
		options.add(TRUNCATE_EXISTING);
		options.add(CREATE);
		Path path = Paths.get(TEMPFILE_ORIGIN);
		try(BufferedWriter testFile = Files.newBufferedWriter(path, cs, options.toArray(new OpenOption[options.size()]));) {
			testFile.write("0");
			for(int i = 0; i < SIZE; i ++) {
				testFile.write(", " + (i + 1));
			}
			testFile.close();
		}
		splitter = new Splitter(path.toFile());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(TEMPFOLDER))) {
		    for (Path file: stream) {
		    	Files.delete(file);
		    }
		}
		Files.delete(Paths.get(TEMPFOLDER));
	}

	/**
	 * Test method for {@link com.my.splitter.file.Splitter#split(long)}.
	 */
	@Test
	public void testSplit() {
		Path path = Paths.get(TEMPFILE_ORIGIN);
		OperationResult or = splitter.split(CUNCK_SIZE);
		assertTrue(or.isSuccess());
	}
	
	/**
	 * Test method for private method {@link com.my.splitter.file.Splitter#calculatePartsNumber(long, long)}.
	 */
	@Test
	public void testCalculatePartsNumber() {
		int result = 0;
		result = invokeCalculatePartsNumber(50, 101);
		assertEquals(3, result);
		result = invokeCalculatePartsNumber(50, 100);
		assertEquals(2, result);
		result = invokeCalculatePartsNumber(500, 101);
		assertEquals(1, result);
		
	}

	/**
	 * Test method for private method {@link com.my.splitter.file.Splitter#removeExtention(String)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testRemoveExtention() throws Throwable {
		String s = invoceRemoveExtention("");
		assertEquals("", s);
		s = invoceRemoveExtention("mooo.txt");
		assertEquals("mooo", s);
		s = invoceRemoveExtention("moo.txt.chm");
		assertEquals("moo.txt", s);
		s = invoceRemoveExtention(null);
	}

	private int invokeCalculatePartsNumber(int chunkSize, long fileSize) {
		int result = 0;
		Method method;
		try {
			method = splitter.getClass().getDeclaredMethod("calculatePartsNumber", long.class, long.class);
			method.setAccessible(true);
			result = (Integer) method.invoke(splitter, chunkSize, fileSize);
		} catch (NoSuchMethodException e) {
			log.error("Reflection", e);
		} catch (SecurityException e) {
			log.error("Security", e);
		} catch (IllegalAccessException e) {
			log.error("Access", e);
		} catch (IllegalArgumentException e) {
			log.error("Arguments", e);
		} catch (InvocationTargetException e) {
			log.error("Target", e);
		}
		return result;
	}

	private String invoceRemoveExtention(String str) throws Throwable {
		String result = null;
		Method method;
		try {
			method = splitter.getClass().getDeclaredMethod("removeExtention", String.class);
			method.setAccessible(true);
			result = (String) method.invoke(splitter, str);
		} catch (NoSuchMethodException e) {
			log.error("Reflection", e);
		} catch (SecurityException e) {
			log.error("Security", e);
		} catch (IllegalAccessException e) {
			log.error("Access", e);
		} catch (IllegalArgumentException e) {
			log.error("Arguments", e);
		} catch (InvocationTargetException e) {
			log.error("Target", e);
			throw e.getCause();
		}
		return result;
	}
	
	
}
