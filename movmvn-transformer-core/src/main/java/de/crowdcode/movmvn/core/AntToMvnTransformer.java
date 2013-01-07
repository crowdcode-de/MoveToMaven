package de.crowdcode.movmvn.core;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.inject.Inject;
import javax.xml.transform.TransformerException;

/**
 * Ant To Maven Transformer class.
 * 
 * @author lofi
 */
public class AntToMvnTransformer {

	@Inject
	private Set<Plugin> plugins;

	@Inject
	private Context context;

	@Inject
	private Unzipper unzipper;

	/**
	 * Get the context information.
	 * 
	 * @return Context
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * Setter.
	 * 
	 * @param plugins
	 *            plugins content
	 */
	public void setPlugins(final Set<Plugin> plugins) {
		this.plugins = plugins;
	}

	/**
	 * Setter.
	 * 
	 * @param context
	 *            context content
	 */
	public void setContext(final Context context) {
		this.context = context;
	}

	/**
	 * Execute the transformer.
	 */
	public void execute() throws TransformerException {
		// Unzip the zip project file to the working dir
		try {
			unzip();
		} catch (IOException e) {
			// We cannot unzip, so we need to stop
			throw new TransformerException(
					"We cannot unzip, so we need to stop: " + e.getStackTrace());
		}

		// TODO Sort the plugins

		// Go through all plugins and execute them one by one...
		for (Plugin plugin : plugins) {
			plugin.execute(context);
		}

		// Zip the result directory into one zip file
		zip();
	}

	/**
	 * Zip the file.
	 */
	private void zip() {
		// TODO Auto-generated method stub

	}

	/**
	 * Unzip the zipFile.
	 * 
	 * @throws IOException
	 *             exception for zip file
	 */
	private void unzip() throws IOException {
		File archiveFile = new File(context.getZipFile());
		File outPath = new File(context.getProjectWorkDirectory());

		unzipper.unzipFileToDir(archiveFile, outPath);
	}
}
