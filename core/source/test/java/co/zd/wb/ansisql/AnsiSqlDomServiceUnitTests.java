package co.zd.wb.ansisql;

import co.zd.wb.ModelExtensions;
import co.zd.wb.Resource;
import co.zd.wb.fixturecreator.FixtureCreator;
import co.zd.wb.plugin.ansisql.AnsiSqlCreateDatabaseMigration;
import co.zd.wb.service.AssertionBuilder;
import co.zd.wb.service.MessagesException;
import co.zd.wb.service.MigrationBuilder;
import co.zd.wb.service.ResourceBuilder;
import co.zd.wb.service.dom.DomResourceLoader;
import co.zd.wb.service.dom.ansisql.AnsiSqlCreateDatabaseDomMigrationBuilder;
import co.zd.wb.service.dom.postgresql.PostgreSqlDatabaseDomResourceBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

public class AnsiSqlDomServiceUnitTests
{
	@Test public void ansiSqlCreateMigrationLoadFromValidDocumentSucceeds() throws MessagesException
	{
		
		//
		// Setup
		//
		
		UUID migrationId = UUID.randomUUID();
		UUID fromStateId = null;
		UUID toStateId = UUID.randomUUID();
		
		String xml = FixtureCreator.create()
			.resource("PostgreSqlDatabase", UUID.randomUUID(), "Foo")
				.migration("AnsiSqlCreateDatabase", migrationId, fromStateId, toStateId)
			.render();

		Map<String, ResourceBuilder> resourceBuilders = new HashMap<String, ResourceBuilder>();
		resourceBuilders.put("PostgreSqlDatabase", new PostgreSqlDatabaseDomResourceBuilder());
		
		Map<String, MigrationBuilder> migrationBuilders = new HashMap<String, MigrationBuilder>();
		migrationBuilders.put("AnsiSqlCreateDatabase", new AnsiSqlCreateDatabaseDomMigrationBuilder());
		
		// TODO: This actually should fail because the migration references non-existant states.  Validation should be
		// added, and when it is this test will need to be fixed.
		DomResourceLoader loader = new DomResourceLoader(
			resourceBuilders,
			new HashMap<String, AssertionBuilder>(),
			migrationBuilders,
			xml);

		//
		// Execute
		//
		
		Resource resource = loader.load();
		
		//
		// Verify
		//
		
		Assert.assertNotNull("resource", resource);
		Assert.assertEquals("resource.migrations.size", 1, resource.getMigrations().size());
		AnsiSqlCreateDatabaseMigration mT = ModelExtensions.As(
			resource.getMigrations().get(0),
			AnsiSqlCreateDatabaseMigration.class);
		Assert.assertNotNull("resource.migrations[0] expected to be of type AnsiSqlCreateDatabaseMigration", mT);
		Assert.assertEquals(
			"resource.migrations[0].id",
			migrationId,
			mT.getMigrationId());
		Assert.assertFalse(
			"resource.migrations[0].hasFromStateId",
			mT.hasFromStateId());
		Assert.assertEquals(
			"resource.migrations[0].toStateId",
			toStateId,
			mT.getToStateId());
		
	}
}
