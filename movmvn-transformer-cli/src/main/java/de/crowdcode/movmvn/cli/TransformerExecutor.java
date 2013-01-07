package de.crowdcode.movmvn.cli;

import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.transform.TransformerException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import de.crowdcode.movmvn.core.MovToMvnTransformer;
import de.crowdcode.movmvn.core.MovToMvnTransformerModule;
import de.crowdcode.movmvn.core.Context;
import de.crowdcode.movmvn.plugin.general.GeneralPluginModule;

/**
 * Executor for transformer.
 * 
 * @author lofi
 */
public class TransformerExecutor {

	private static final Logger log = Logger
			.getLogger(TransformerExecutor.class.getName());

	public static void main(final String[] args) {
		// Input: the zip file of the Ant project
		// Input: the project working directory
		String zipFile = args[0];
		String projectWorkDirectory = args[1];
		log.info("Ant To Maven Transformer - ZIP file: " + zipFile + " - "
				+ "Project working directory: " + projectWorkDirectory);

		TransformerHelper transformerHelper = new TransformerHelper();

		// Get all Guice modules in config
		Properties properties = transformerHelper.loadProperties();
		String modules = properties.getProperty("module.classes");
		List<String> moduleItems = transformerHelper.getModuleItems(modules);

		// Create all the plugin classes
		Set<Module> moduleObjects = transformerHelper
				.createModules(moduleItems);

		// Create injectors for the transformer and all the plugins!
		moduleObjects.add(new MovToMvnTransformerModule());
		moduleObjects.add(new GeneralPluginModule());
		Injector injector = Guice.createInjector(moduleObjects);

		MovToMvnTransformer antToMvnTransformer = injector
				.getInstance(MovToMvnTransformer.class);

		// TODO Instead we need to make singleton for the context object
		Context context = antToMvnTransformer.getContext();
		context.setProjectWorkDirectory(projectWorkDirectory);
		context.setZipFile(zipFile);
		log.info("Project name: " + context.getProjectName());
		log.info("Project sourcename: " + context.getProjectSourceName());
		log.info("Project targetname: " + context.getProjectTargetName());

		try {
			antToMvnTransformer.execute();
		} catch (TransformerException e) {
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}

}
