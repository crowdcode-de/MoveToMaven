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

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.inject.Module;

/**
 * Transformer Helper.
 * 
 * @author lofi
 * @version 1.0.0
 * @since 1.0.0
 */
public class TransformerHelper {

	private static final Logger log = Logger.getLogger(TransformerHelper.class
			.getName());

	public Set<Module> createModules(List<String> moduleItems) {
		Set<Module> moduleObjects = new HashSet<Module>();
		for (String moduleName : moduleItems) {
			try {
				@SuppressWarnings("unchecked")
				Class<Module> moduleClass = (Class<Module>) Class
						.forName(moduleName);
				Constructor<Module> constructor = moduleClass.getConstructor();
				Module moduleObject = constructor.newInstance();
				moduleObjects.add(moduleObject);
			} catch (ClassNotFoundException e) {
				log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			} catch (IllegalArgumentException e) {
				log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			} catch (InstantiationException e) {
				log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			} catch (IllegalAccessException e) {
				log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			} catch (InvocationTargetException e) {
				log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			} catch (SecurityException e) {
				log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			} catch (NoSuchMethodException e) {
				log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			}
		}
		return moduleObjects;
	}

	public List<String> getModuleItems(String modules) {
		List<String> moduleItems = Arrays.asList(modules.split("\\s*,\\s*"));
		return moduleItems;
	}

	public Properties loadProperties() {
		Properties properties = new Properties();
		ClassLoader loader = TransformerExecutor.class.getClassLoader();
		URL url = loader.getResource("config.properties");
		try {
			properties.load(url.openStream());
		} catch (IOException e) {
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}

		return properties;
	}
}
