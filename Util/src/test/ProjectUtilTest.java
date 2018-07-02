package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import util.ProjectUtil;

public class ProjectUtilTest {
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetProjectRootPath() {
		System.out.println(ProjectUtil.getProjectRootPath());
	}

}
