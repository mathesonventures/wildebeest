// Wildebeest Migration Framework
// Copyright © 2013 - 2018, Matheson Ventures Pte Ltd
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

package co.mv.wb.plugin.composite.dom;

import co.mv.wb.LoaderFault;
import co.mv.wb.Migration;
import co.mv.wb.PluginBuildException;
import co.mv.wb.Resource;
import co.mv.wb.Wildebeest;
import co.mv.wb.fixture.FixtureCreator;
import co.mv.wb.impl.ResourceTypeServiceBuilder;
import co.mv.wb.plugin.base.dom.DomPlugins;
import co.mv.wb.plugin.base.dom.DomResourceLoader;
import co.mv.wb.plugin.composite.ExternalResourceMigration;
import org.junit.Test;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for {@link ExternalResourceDomMigrationBuilder}.
 * 
 * @author                                      Brendon Matheson
 * @since                                       4.0
 */
public class ExternalResourceDomMigrationBuilderIntegrationTests
{

	//
	// build
	//
	
	@Test public void build_forValidDocument_succeeds() throws
		LoaderFault,
		PluginBuildException
	{
		// Setup
		UUID resourceId = UUID.randomUUID();
		UUID state1Id = UUID.randomUUID();
		UUID state2Id = UUID.randomUUID();
		UUID migration1Id = UUID.randomUUID();
		
		String resourceXml = FixtureCreator.create()
			.resource(Wildebeest.PostgreSqlDatabase.getUri(), resourceId, "Test")
			.state(state1Id, "state1")
			.state(state2Id, "state2")
			.migration("External", migration1Id, state1Id, state2Id)
				.innerXml("<filename>foo.wbr</filename><target>bar</target>")
			.render();
		
		System.out.println("resourceXml: " + resourceXml);

		DomResourceLoader resourceLoader = DomPlugins.resourceLoader(
			ResourceTypeServiceBuilder
				.create()
				.withFactoryResourceTypes()
				.build(),
			resourceXml);

		// Execute
		Resource resource = resourceLoader.load(new File("."));
		
		// Verify
		Migration migration = resource.getMigrations().get(0);
		assertNotNull("migration", migration);
		
		ExternalResourceMigration migrationT = (ExternalResourceMigration)migration;
		assertEquals("migration.filename", "foo.wbr", migrationT.getFileName());
		assertEquals("migration.target", Optional.of("bar"), migrationT.getTarget());
	}

}
