// Wildebeest Migration Framework
// Copyright © 2013 - 2015, Zen Digital Co Inc
//
// This file is part of Wildebeest
//
// Wildebeest is free software: you can redistribute it and/or modify it under
// the terms of the GNU General Public License v2 as published by the Free
// Software Foundation.
//
// Wildebeest is distributed in the hope that it will be useful, but WITHOUT ANY
// WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
// A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along with
// Wildebeest.  If not, see http://www.gnu.org/licenses/gpl-2.0.html

package co.zd.wb.service.dom.composite;

import co.zd.wb.FakeLogger;
import co.zd.wb.Migration;
import co.zd.wb.Resource;
import co.zd.wb.fixturecreator.FixtureCreator;
import co.zd.wb.plugin.composite.ExternalResourceMigration;
import co.zd.wb.service.MessagesException;
import co.zd.wb.service.dom.DomPlugins;
import co.zd.wb.service.dom.DomResourceLoader;
import java.util.UUID;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit tests for {@link ExternalResourceDomMigrationBuilder}.
 * 
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class ExternalResourceDomMigrationBuilderIntegrationTests
{
	private static Logger LOG = LoggerFactory.getLogger(ExternalResourceDomMigrationBuilderIntegrationTests.class);
	
	//
	// build
	//
	
	@Test public void build_forValidDocument_succeeds() throws MessagesException
	{
		// Setup
		UUID resourceId = UUID.randomUUID();
		UUID state1Id = UUID.randomUUID();
		UUID state2Id = UUID.randomUUID();
		UUID migration1Id = UUID.randomUUID();
		
		String resourceXml = FixtureCreator.create()
			.resource("PostgreSqlDatabase", resourceId, "Test")
			.state(state1Id, "state1")
			.state(state2Id, "state2")
			.migration("External", migration1Id, state1Id, state2Id)
				.innerXml("<filename>foo.wbr</filename><target>bar</target>")
			.render();
		
		System.out.println("resourceXml: " + resourceXml);

		DomResourceLoader resourceLoader = DomPlugins.resourceLoader(new FakeLogger(), resourceXml);

		// Execute
		Resource resource = resourceLoader.load();
		
		// Verify
		Migration migration = resource.getMigrations().get(0);
		assertNotNull("migration", migration);
		
		ExternalResourceMigration migrationT = (ExternalResourceMigration)migration;
		assertEquals("migration.filename", "foo.wbr", migrationT.getFileName());
		assertEquals("migration.target", "bar", migrationT.getTarget());
	}

}
