package de.crowdcode.movmvn.cli;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.crowdcode.movmvn.cli.TransformerExecutor;

/**
 * Test for executor.
 * 
 * @author lofi
 */
public class TransformerExecutorTest {

	@Test
	public void testProjectOawIntegration() {
		String[] args = new String[2];
		args[0] = "src/test/resources/de/crowdcode/antmvntransf/cli/testantproject/oaw_integration.zip";
		args[1] = "target/tmp";
		TransformerExecutor.main(args);
		assertTrue(true);
	}

	@Test
	public void testProjectExtraDataPlugin() {
		String[] args = new String[2];
		args[0] = "src/test/resources/de/crowdcode/antmvntransf/cli/testantproject/extra-dataplugin.zip";
		args[1] = "target/tmp";
		TransformerExecutor.main(args);
		assertTrue(true);
	}

	@Test
	public void testProjectExtraProfiling() {
		String[] args = new String[2];
		args[0] = "src/test/resources/de/crowdcode/antmvntransf/cli/testantproject/extra-profiling.zip";
		args[1] = "target/tmp";
		TransformerExecutor.main(args);
		assertTrue(true);
	}
}
