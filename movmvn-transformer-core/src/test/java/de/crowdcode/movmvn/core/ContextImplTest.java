package de.crowdcode.movmvn.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.crowdcode.movmvn.core.Context;
import de.crowdcode.movmvn.core.ContextImpl;

/**
 * Unit test for context.
 * 
 * @author lofi
 * @version 1.0
 * @since 1.0
 */
public class ContextImplTest {

	@Test
	public void testGetProjectName() {
		Context context = new ContextImpl();
		context.setProjectWorkDirectory("target/tmp");
		context.setZipFile("src/test/resources/de/crowdcode/antmvntransf/cli/testantproject/oaw_integration.zip");

		String projectName = context.getProjectName();
		assertEquals("oaw_integration", projectName);

		context.setZipFile("D:\\LDaten\\git\\antmvntransf\\antmvn-transformer-cli\\src\\test\\resources\\de\\crowdcode\\antmvntransf\\cli\\testantproject\\oaw_integration.zip");
		projectName = context.getProjectName();
		assertEquals("oaw_integration", projectName);
	}

	@Test
	public void testGetProjectSourceName() {
		Context context = new ContextImpl();
		context.setProjectWorkDirectory("target/tmp");
		context.setZipFile("src/test/resources/de/crowdcode/antmvntransf/cli/testantproject/oaw_integration.zip");

		String projectSourceName = context.getProjectSourceName();
		assertEquals("target/tmp/oaw_integration", projectSourceName);
	}

	@Test
	public void testGetProjectTargetName() {
		Context context = new ContextImpl();
		context.setProjectWorkDirectory("target/tmp");
		context.setZipFile("src/test/resources/de/crowdcode/antmvntransf/cli/testantproject/oaw_integration.zip");

		String projectTargetName = context.getProjectTargetName();
		assertEquals("target/tmp/target/oaw_integration", projectTargetName);
	}
}
