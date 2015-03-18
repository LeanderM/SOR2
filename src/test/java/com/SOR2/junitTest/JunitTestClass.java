package com.SOR2.junitTest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class JunitTestClass {

	private JunitTestClass subject;

	@Before
	public void setUp() {
		subject = new JunitTestClass();
	}

	@Test
	public void testCalculate() {
		assertEquals(9, subject.calculate(4, 5));
	}

	private Object calculate(int i, int j) {
		// TODO Auto-generated method stub
		return i + j;
	}

}
