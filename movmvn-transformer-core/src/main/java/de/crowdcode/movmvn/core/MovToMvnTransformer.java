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
public class MovToMvnTransformer {

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
