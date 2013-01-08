/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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

import de.crowdcode.movmvn.core.Context;
import de.crowdcode.movmvn.core.MovToMvnTransformer;
import de.crowdcode.movmvn.core.MovToMvnTransformerModule;
import de.crowdcode.movmvn.plugin.general.GeneralPluginModule;

/**
 * Executor for transformer.
 * 
 * @author lofi
 */
public class TransformerExecutor {

	private static final Logger log = Logger
			.getLogger(TransformerExecutor.class.getName());

	/**
	 * Main method.
	 * 
	 * @param args
	 *            arguments for CLI.
	 */
	public static void main(final String[] args) {
		// Parameter arg[0] = -zip or -dirProject or -dirProjectGroup
		// Parameter arg[1] = zip or directory name
		// Parameter arg[2] = working directory
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

		MovToMvnTransformer movToMvnTransformer = injector
				.getInstance(MovToMvnTransformer.class);

		if (args[0].equals("-zip")) {
			executeWithZip(args, movToMvnTransformer);
		} else if (args[0].equals("-dirProject")) {
			executeWithDirProject(args, movToMvnTransformer);
		} else if (args[0].equals("-dirProjectGroup")) {
			executeWithDirProjectGroup(args, movToMvnTransformer);
		} else {
			log.log(Level.SEVERE,
					"Error: please use following format as an example: movmvn-cli -zip C:/temp/extra-dataplugin.zip C:/temp/result");
		}
	}

	private static void executeWithDirProjectGroup(final String[] args,
			MovToMvnTransformer movToMvnTransformer) {
		// Input: the dir of the projects - MANY projects reside within this
		// directory
		// Input: the project working directory
		String dir = args[1];
		String projectWorkDirectory = args[2];
		log.info("Move To Maven Transformer - Projects Group Directory: "
				+ dir + " - " + "Project working directory: "
				+ projectWorkDirectory);

		// Get the injected context object
		Context context = movToMvnTransformer.getContext();
		context.setProjectWorkDirectory(projectWorkDirectory);
		context.setDirectory(dir);
		log.info("Project name: " + context.getProjectName());
		log.info("Project sourcename: " + context.getProjectSourceName());
		log.info("Project targetname: " + context.getProjectTargetName());

		try {
			movToMvnTransformer.executeDirProjectGroup();
		} catch (TransformerException e) {
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}

	private static void executeWithDirProject(final String[] args,
			MovToMvnTransformer movToMvnTransformer) {
		// Input: the dir of the project - ONLY ONE project
		// Input: the project working directory
		String dir = args[1];
		String projectWorkDirectory = args[2];
		log.info("Move To Maven Transformer - Project Directory: " + dir
				+ " - " + "Project working directory: "
				+ projectWorkDirectory);

		// Get the injected context object
		Context context = movToMvnTransformer.getContext();
		context.setProjectWorkDirectory(projectWorkDirectory);
		context.setDirectory(dir);
		log.info("Project name: " + context.getProjectName());
		log.info("Project sourcename: " + context.getProjectSourceName());
		log.info("Project targetname: " + context.getProjectTargetName());

		try {
			movToMvnTransformer.executeDirProject();
		} catch (TransformerException e) {
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}

	private static void executeWithZip(final String[] args,
			MovToMvnTransformer movToMvnTransformer) {
		// Input: the zip file of the project
		// Input: the project working directory
		String zipFile = args[1];
		String projectWorkDirectory = args[2];
		log.info("Move To Maven Transformer - ZIP file: " + zipFile + " - "
				+ "Project working directory: " + projectWorkDirectory);

		// Get the injected context object
		Context context = movToMvnTransformer.getContext();
		context.setProjectWorkDirectory(projectWorkDirectory);
		context.setZipFile(zipFile);
		log.info("Project name: " + context.getProjectName());
		log.info("Project sourcename: " + context.getProjectSourceName());
		log.info("Project targetname: " + context.getProjectTargetName());

		try {
			movToMvnTransformer.executeZip();
		} catch (TransformerException e) {
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}
}
