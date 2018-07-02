package test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import util.DateUtil;

public class DateUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetWeekDateInt() {
		TestCase.assertEquals(5, DateUtil.getWeekDateInt(new Date()));
	}

}
