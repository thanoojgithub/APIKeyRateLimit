package com.ratelimit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppTest.class);

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
		LOGGER.info("AppTest.AppTest()");
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		LOGGER.info("AppTest.suite()");
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		LOGGER.info("AppTest.testApp()");
		assertTrue(true);
	}
}
