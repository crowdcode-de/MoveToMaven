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
package de.crowdcode.movmvn.plugin.general;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.crowdcode.movmvn.core.AbstractPlugin;
import de.crowdcode.movmvn.core.Context;

/**
 * General plugin for Ant Maven transformer.
 * 
 * @author lofi
 */
public class GeneralPlugin extends AbstractPlugin {

	private Context context;

	private static final Logger log = Logger.getLogger(GeneralPlugin.class
			.getName());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(final Context context) {
		this.context = context;
		// Restructure the directories
		restructureDirectories();
		// Move all the files to the correct Maven directories
		// e.g. all Java classes must go to main/java
		// all properties files must go to main/resources
		moveFiles();
		createPomFile();
	}

	private void createPomFile() {
		context.logInfo("Create POM file...");
		try {
			String projectWorkDir = context.getProjectTargetName();
			Document doc = createDocument();

			// Root project
			Element rootElement = createPomRootAndTitle(doc);

			createPomProperties(doc, rootElement);
			createPomDependencies(doc, rootElement);
			createPomBuild(doc, rootElement);

			// Write the content into xml file
			writeToFile(projectWorkDir, doc);

			context.logInfo("POM file saved!");
		} catch (ParserConfigurationException e) {
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} catch (TransformerException e) {
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}

	private void createPomBuild(Document doc, Element rootElement) {
		Element build = doc.createElement("build");
		rootElement.appendChild(build);
		Element plugins = doc.createElement("plugins");
		build.appendChild(plugins);

		Element plugin = doc.createElement("plugin");
		plugins.appendChild(plugin);
		createPomGroupArtifactVersion(doc, plugin, "org.apache.maven.plugins",
				"maven-compiler-plugin", "2.3.2");
		Element configuration = doc.createElement("configuration");
		plugin.appendChild(configuration);
		Element source = doc.createElement("source");
		source.appendChild(doc.createTextNode("1.6"));
		Element target = doc.createElement("target");
		target.appendChild(doc.createTextNode("1.6"));
		configuration.appendChild(source);
		configuration.appendChild(target);
	}

	private void createPomProperties(Document doc, Element rootElement) {
		Element properties = doc.createElement("properties");
		rootElement.appendChild(properties);

		Element projectBuildSourceEncoding = doc
				.createElement("project.build.sourceEncoding");
		projectBuildSourceEncoding.appendChild(doc.createTextNode("UTF-8"));
		properties.appendChild(projectBuildSourceEncoding);
	}

	private void writeToFile(String projectWorkDir, Document doc)
			throws TransformerFactoryConfigurationError,
			TransformerConfigurationException, TransformerException {
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(projectWorkDir.concat(
				"/").concat("pom.xml")));

		transformer.transform(source, result);
	}

	private void createPomDependencies(Document doc, Element rootElement) {
		Element dependencies = doc.createElement("dependencies");
		rootElement.appendChild(dependencies);
		Element dependency = doc.createElement("dependency");
		dependencies.appendChild(dependency);

		createPomGroupArtifactVersion(doc, dependency, "junit", "junit", "4.10");
		Element scope = doc.createElement("scope");
		scope.appendChild(doc.createTextNode("test"));
		dependency.appendChild(scope);
	}

	private Element createPomRootAndTitle(Document doc) {
		Element rootElement = doc.createElement("project");
		doc.appendChild(rootElement);
		rootElement.setAttribute("xmlns", "http://maven.apache.org/POM/4.0.0");
		rootElement.setAttribute("xmlns:xsi",
				"http://www.w3.org/2001/XMLSchema-instance");
		rootElement
				.setAttribute("xsi:schemaLocation",
						"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd");

		Element modelVersion = doc.createElement("modelVersion");
		modelVersion.appendChild(doc.createTextNode("4.0.0"));
		rootElement.appendChild(modelVersion);

		createPomGroupArtifactVersion(doc, rootElement, "de.xxx",
				context.getProjectName(), "1.0.0-SNAPSHOT");

		Element packaging = doc.createElement("packaging");
		packaging.appendChild(doc.createTextNode("jar"));
		rootElement.appendChild(packaging);
		Element name = doc.createElement("name");
		name.appendChild(doc.createTextNode(context.getProjectName()));
		rootElement.appendChild(name);
		Element url = doc.createElement("url");
		url.appendChild(doc.createTextNode("http://maven.apache.org"));
		rootElement.appendChild(url);
		return rootElement;
	}

	private void createPomGroupArtifactVersion(Document doc,
			Element rootElement, String groupIdStr, String artifactIdStr,
			String versionStr) {
		Element groupId = doc.createElement("groupId");
		groupId.appendChild(doc.createTextNode(groupIdStr));
		rootElement.appendChild(groupId);
		Element artifactId = doc.createElement("artifactId");
		artifactId.appendChild(doc.createTextNode(artifactIdStr));
		rootElement.appendChild(artifactId);
		Element version = doc.createElement("version");
		version.appendChild(doc.createTextNode(versionStr));
		rootElement.appendChild(version);
	}

	private Document createDocument() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		return doc;
	}

	private void moveFiles() {
		try {
			context.logInfo("Move files...");
			String projectSourceName = context.getProjectSourceName();
			String projectTargetName = context.getProjectTargetName();
			// Move files *.java from src -> src/main/java
			// Move files *.properties;*.txt;*.jpg -> src/main/resources
			// Move files *.java from test -> src/test/java
			// Move files *.properties;*.txt;*.jpg -> src/test/resources
			// Move files *.* from resources or resource -> src/main/resources
			// Move files from / to / but not .classpath, .project ->
			// src/main/resources
			// Move directory META-INF -> src/main/resources
			String[] extensionsJava = { "java" };
			Iterator<File> fileIterator = FileUtils.iterateFiles(new File(
					projectSourceName + "/src"), extensionsJava, true);
			for (Iterator<File> iterator = fileIterator; iterator.hasNext();) {
				File currentFile = iterator.next();
				log.info("File to be copied: " + currentFile.getCanonicalPath());
				String nameResultAfterSrc = StringUtils.substringAfterLast(
						currentFile.getAbsolutePath(), "src\\");
				nameResultAfterSrc = projectTargetName
						.concat("/src/main/java/").concat(nameResultAfterSrc);
				log.info("Target file: " + nameResultAfterSrc);
				File targetFile = new File(nameResultAfterSrc);
				FileUtils.copyFile(currentFile, targetFile, true);
			}

			// Check whether "resource" or "resources" exist?
			File directoryResources = new File(projectSourceName + "/resource");
			File targetResourcesDir = new File(
					projectTargetName.concat("/src/main/resources"));
			if (directoryResources.exists()) {
				// Move the files
				FileUtils.copyDirectory(directoryResources, targetResourcesDir);
			}

			directoryResources = new File(projectSourceName + "/resources");
			if (directoryResources.exists()) {
				// Move the files
				FileUtils.copyDirectory(directoryResources, targetResourcesDir);
			}

			// META-INF
			File directoryMetaInf = new File(projectSourceName + "/META-INF");
			if (directoryMetaInf.exists()) {
				FileUtils.copyDirectoryToDirectory(directoryMetaInf,
						targetResourcesDir);
			}

			// Directory . *.txt, *.doc*, *.png, *.jpg -> src/main/docs
			File targetDocsDir = new File(
					projectTargetName.concat("/src/main/docs"));
			String[] extensionsRootDir = { "txt", "doc", "docx", "png", "jpg" };
			fileIterator = FileUtils.iterateFiles(new File(projectSourceName),
					extensionsRootDir, false);
			for (Iterator<File> iterator = fileIterator; iterator.hasNext();) {
				File currentFile = iterator.next();
				log.info("File to be copied: " + currentFile.getCanonicalPath());
				FileUtils.copyFileToDirectory(currentFile, targetDocsDir);
			}

			// Directory . *.cmd, *.sh -> src/main/bin
			File targetBinDir = new File(
					projectTargetName.concat("/src/main/bin"));
			String[] extensionsRootBinDir = { "sh", "cmd", "properties" };
			fileIterator = FileUtils.iterateFiles(new File(projectSourceName),
					extensionsRootBinDir, false);
			for (Iterator<File> iterator = fileIterator; iterator.hasNext();) {
				File currentFile = iterator.next();
				log.info("File to be copied: " + currentFile.getCanonicalPath());
				FileUtils.copyFileToDirectory(currentFile, targetBinDir);
			}
		} catch (IOException e) {
			log.info(e.getStackTrace().toString());
		}
	}

	private void restructureDirectories() {
		String projectWorkDir = context.getProjectTargetName();
		context.logInfo("Restructure directories: " + projectWorkDir);
		// Create all the Maven directories
		// src/main/java
		// src/main/resources
		// src/test/java
		// src/test/resources
		File directory = new File(projectWorkDir.concat("/src/main/java"));
		directory.mkdirs();
		directory = new File(projectWorkDir.concat("/src/main/resources"));
		directory.mkdirs();
		directory = new File(projectWorkDir.concat("/src/test/java"));
		directory.mkdirs();
		directory = new File(projectWorkDir.concat("/src/test/resources"));
		directory.mkdirs();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return "General Plugin";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getExecutionOrderedNumber() {
		// The general plugin will work as the FIRST plugin ever!
		return 0;
	}
}
