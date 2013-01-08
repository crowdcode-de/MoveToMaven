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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
	private AbstractPlugin mockPlugin1;

	@Mock
	private AbstractPlugin mockPlugin2;

	@Mock
	private AbstractPlugin mockPlugin3;

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
			plugins.add(mockPlugin1);
			antToMvnTransformer.setPlugins(plugins);

			antToMvnTransformer.executeZip();

			verify(mockUnzipper, times(1)).unzipFileToDir(any(File.class),
					any(File.class));
			verify(mockPlugin1, times(1)).execute(any(Context.class));
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

	@Test
	public void testExecuteSortedPlugins() {
		try {
			when(mockPlugin1.getName()).thenReturn("mockPlugin1");
			when(mockPlugin1.getExecutionOrderedNumber()).thenReturn(1);
			when(mockPlugin1.compareTo(mockPlugin2)).thenReturn(-9);
			when(mockPlugin1.compareTo(mockPlugin3)).thenReturn(1);

			when(mockPlugin2.getName()).thenReturn("mockPlugin2");
			when(mockPlugin2.getExecutionOrderedNumber()).thenReturn(10);
			when(mockPlugin2.compareTo(mockPlugin1)).thenReturn(9);
			when(mockPlugin2.compareTo(mockPlugin3)).thenReturn(10);

			when(mockPlugin3.getName()).thenReturn("mockPlugin3");
			when(mockPlugin3.getExecutionOrderedNumber()).thenReturn(0);
			when(mockPlugin3.compareTo(mockPlugin1)).thenReturn(-1);
			when(mockPlugin3.compareTo(mockPlugin2)).thenReturn(-10);

			Context context = new ContextImpl();
			context.setProjectWorkDirectory("target/tmp");
			context.setZipFile("src/test/resources/de/crowdcode/movmvn/cli/testantproject/extra-dataplugin.zip");
			antToMvnTransformer.setContext(context);

			Set<Plugin> plugins = new HashSet<Plugin>();
			plugins.add(mockPlugin2);
			plugins.add(mockPlugin1);
			plugins.add(mockPlugin3);
			antToMvnTransformer.setPlugins(plugins);

			antToMvnTransformer.executeZip();

			verify(mockUnzipper, times(1)).unzipFileToDir(any(File.class),
					any(File.class));
			verify(mockPlugin1, times(1)).execute(any(Context.class));
			verify(mockPlugin2, times(1)).execute(any(Context.class));
			verify(mockPlugin3, times(1)).execute(any(Context.class));

			// Verify the sorted plugins
			Plugin[] pluginArray = antToMvnTransformer.getSortedPlugins();

			assertEquals("mockPlugin3", pluginArray[0].getName());
			assertEquals("mockPlugin1", pluginArray[1].getName());
			assertEquals("mockPlugin2", pluginArray[2].getName());
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
