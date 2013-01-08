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

import org.junit.Test;

/**
 * Unit test for context.
 * 
 * @author lofi
 * @version 1.0
 * @since 1.0
 */
public class ContextImplTest {

	@Test
	public void testGetProjectNameZip() {
		Context context = new ContextImpl();
		context.setProjectWorkDirectory("target/tmp");
		context.setZipFile("src/test/resources/de/crowdcode/movmvn/cli/testantproject/extra-dataplugin.zip");

		String projectName = context.getProjectName();
		assertEquals("extra-dataplugin", projectName);

		context.setZipFile("D:\\LDaten\\git\\antmvntransf\\antmvn-transformer-cli\\src\\test\\resources\\de\\crowdcode\\movmvn\\cli\\testantproject\\extra-dataplugin.zip");
		projectName = context.getProjectName();
		assertEquals("extra-dataplugin", projectName);
	}

	@Test
	public void testGetProjectSourceNameZip() {
		Context context = new ContextImpl();
		context.setProjectWorkDirectory("target/tmp");
		context.setZipFile("src/test/resources/de/crowdcode/movmvn/cli/testantproject/extra-dataplugin.zip");

		String projectSourceName = context.getProjectSourceName();
		assertEquals("target/tmp/extra-dataplugin", projectSourceName);
	}

	@Test
	public void testGetProjectTargetNameZip() {
		Context context = new ContextImpl();
		context.setProjectWorkDirectory("target/tmp");
		context.setZipFile("src/test/resources/de/crowdcode/movmvn/cli/testantproject/extra-dataplugin.zip");

		String projectTargetName = context.getProjectTargetName();
		assertEquals("target/tmp/target/extra-dataplugin", projectTargetName);
	}

	@Test
	public void testGetProjectNameDirectory() {
		Context context = new ContextImpl();
		context.setProjectWorkDirectory("target/tmp");
		context.setZipFile("src/test/resources/de/crowdcode/movmvn/cli/testantproject/extra-dataplugin");

		String projectName = context.getProjectName();
		assertEquals("extra-dataplugin", projectName);

		context.setZipFile("D:\\LDaten\\git\\antmvntransf\\antmvn-transformer-cli\\src\\test\\resources\\de\\crowdcode\\movmvn\\cli\\testantproject\\extra-dataplugin");
		projectName = context.getProjectName();
		assertEquals("extra-dataplugin", projectName);
	}

	@Test
	public void testGetProjectSourceNameDirectory() {
		Context context = new ContextImpl();
		context.setProjectWorkDirectory("target/tmp");
		context.setZipFile("src/test/resources/de/crowdcode/movmvn/cli/testantproject/extra-dataplugin");

		String projectSourceName = context.getProjectSourceName();
		assertEquals("target/tmp/extra-dataplugin", projectSourceName);
	}

	@Test
	public void testGetProjectTargetNameDirectory() {
		Context context = new ContextImpl();
		context.setProjectWorkDirectory("target/tmp");
		context.setZipFile("src/test/resources/de/crowdcode/movmvn/cli/testantproject/extra-dataplugin");

		String projectTargetName = context.getProjectTargetName();
		assertEquals("target/tmp/target/extra-dataplugin", projectTargetName);
	}
}
