package com.SOR2.junitTest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class JunitTestClass {

	private JunitTestFile subject;

	@Before
	public void setUp() {
		subject = new JunitTestFile();
	}

	@Test
	public void testCalculate() {
		assertEquals(9, subject.calculate(4, 5));
	}

}
