package de.crowdcode.movmvn.core;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipException;

import javax.xml.transform.TransformerException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.crowdcode.movmvn.core.AntToMvnTransformer;
import de.crowdcode.movmvn.core.Context;
import de.crowdcode.movmvn.core.ContextImpl;
import de.crowdcode.movmvn.core.Plugin;
import de.crowdcode.movmvn.core.Unzipper;

/**
 * Unit test for Ant To Maven Transformer class.
 * 
 * @author lofi
 */
@RunWith(MockitoJUnitRunner.class)
public class AntToMvnTransformerTest {

	@Mock
	private Plugin mockPlugin;

	@Mock
	private Unzipper mockUnzipper;

	@InjectMocks
	private AntToMvnTransformer antToMvnTransformer;

	@Test
	public void testExecute() {
		try {
			Context context = new ContextImpl();
			context.setProjectWorkDirectory("target/tmp");
			context.setZipFile("src/test/resources/de/crowdcode/antmvntransf/cli/testantproject/extra-dataplugin.zip");
			antToMvnTransformer.setContext(context);

			Set<Plugin> plugins = new HashSet<Plugin>();
			plugins.add(mockPlugin);
			antToMvnTransformer.setPlugins(plugins);

			antToMvnTransformer.execute();

			verify(mockUnzipper, times(1)).unzipFileToDir(any(File.class),
					any(File.class));
			verify(mockPlugin, times(1)).execute(any(Context.class));
		} catch (TransformerException e) {
			fail("Error happens!");
			e.printStackTrace();
		} catch (ZipException e) {
			fail("Error happens!");
			e.printStackTrace();
		} catch (IOException e) {
			fail("Error happens!");
			e.printStackTrace();
		}
		assertTrue(true);
	}
}
