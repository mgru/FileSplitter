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
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.my.splitter.file.OperationResult;
import com.my.splitter.file.Splitter;

/**
 * @author mike
 *
 */
public class SplitterTest {

	private static final int CUNCK_SIZE = 50;
	private static final int SIZE = 100;
	private static final String TEMPFILE_ORIGIN = "test.txt";
	private static Splitter splitter = null;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
		Files.delete(Paths.get(TEMPFILE_ORIGIN));
	}

	/**
	 * Test method for {@link com.my.splitter.file.Splitter#split(int)}.
	 */
	@Test
	public void testSplit() {
		Path path = Paths.get(TEMPFILE_ORIGIN);
		OperationResult or = splitter.split(CUNCK_SIZE);
		assertTrue(or.isSuccess());
	}
	
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

	private int invokeCalculatePartsNumber(int chunkSize, long fileSize) {
		int result = 0;
		Method method;
		try {
			method = splitter.getClass().getDeclaredMethod("calculatePartsNumber", int.class, long.class);
			method.setAccessible(true);
			result = (Integer) method.invoke(splitter, chunkSize, fileSize);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
