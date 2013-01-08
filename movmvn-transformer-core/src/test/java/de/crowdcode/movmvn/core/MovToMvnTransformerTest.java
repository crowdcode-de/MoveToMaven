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

/**
 * Unit test for Ant To Maven Transformer class.
 * 
 * @author lofi
 */
@RunWith(MockitoJUnitRunner.class)
public class MovToMvnTransformerTest {

	@Mock
	private Plugin mockPlugin;

	@Mock
	private Unzipper mockUnzipper;

	@InjectMocks
	private MovToMvnTransformer antToMvnTransformer;

	@Test
	public void testExecute() {
		try {
			Context context = new ContextImpl();
			context.setProjectWorkDirectory("target/tmp");
			context.setZipFile("src/test/resources/de/crowdcode/movmvn/cli/testantproject/extra-dataplugin.zip");
			antToMvnTransformer.setContext(context);

			Set<Plugin> plugins = new HashSet<Plugin>();
			plugins.add(mockPlugin);
			antToMvnTransformer.setPlugins(plugins);

			antToMvnTransformer.executeZip();

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
